package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.util.InterpLUT;
import com.skeletonarmy.marrow.TimerEx;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.commands.InterpLUTShooterCalculator;
import org.firstinspires.ftc.teamcode.commands.ShootingMath;
import org.firstinspires.ftc.teamcode.misc.GlobalPose;
import org.firstinspires.ftc.teamcode.misc.ShooterState;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.LEDIndicator;
import org.firstinspires.ftc.teamcode.subsystems.PoseStorage;
import org.firstinspires.ftc.teamcode.subsystems.Drawing;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;
import org.firstinspires.ftc.teamcode.subsystems.intake.OuttakeDoor;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Hood;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

import java.util.List;
import java.util.Objects;

import dev.nextftc.control.KineticState;

public class Robot {
    public OpMode opMode;
    public Follower follower;
    public IntakeRoller intakeRoller = new IntakeRoller();
    public OuttakeDoor outtakeDoor = new OuttakeDoor();
    public Shooter shooter = new Shooter();
    public Hood hood = new Hood();
    public Turret turret = new Turret();
    public LEDIndicator ledIndicator;
    public TelemetryManager telemetryM;
    private boolean isFieldCentric;
    public Camera camera = new Camera();
    public static Constants.ALLIANCE alliance;
    TimerEx relocalizeTimer = new TimerEx(60);
    public boolean isShooting = false;
    public double vel = 0, angle = 0, heading = 0;
    List<LynxModule> allHubs;
    ElapsedTime updateTime = new ElapsedTime();
    private boolean firstRelocalization = true;
    Gamepad currentGamepad1 = new Gamepad(), currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad(), previousGamepad2 = new Gamepad();
    public VoltageSensor batteryVoltage;
    static double currentVoltage = 12.5;
    public static int pathState;
    double teleOpFieldFaceAngle;
    PolygonZone robotZone = new PolygonZone(13, 13);

    public void init(OpMode _opmode, Constants.ALLIANCE alliance) {
        opMode = _opmode;
        Robot.alliance = alliance;

        follower = DriveConstants.createFollower(opMode.hardwareMap);

        batteryVoltage = opMode.hardwareMap.voltageSensor.iterator().next();

        shooter.init(opMode.hardwareMap);
        hood.init(opMode.hardwareMap);
        turret.init(opMode.hardwareMap);

        outtakeDoor.init(opMode.hardwareMap);
        intakeRoller.init(opMode.hardwareMap);

        camera.init(opMode.hardwareMap, alliance);

        allHubs = opMode.hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        setShooterTarget(0, 0, 0);

        if (alliance == Constants.ALLIANCE.BLUE) {
            teleOpFieldFaceAngle = Math.PI;
        } else {
            teleOpFieldFaceAngle = 0;
        }

        InterpLUTShooterCalculator.init();

        intakeRoller.setState(IntakeRoller.INTAKE_STATE.STOP);

        Drawing.init();

        ledIndicator = new LEDIndicator(this.opMode);
        ledIndicator.set(false);
    }

    public void init_loop() {
        intakeRoller.setState(IntakeRoller.INTAKE_STATE.STOP);
        aimShoot(false, false);
        update();
    }

    public void start() {
        relocalizeTimer.start();
        intakeRoller.setState(IntakeRoller.INTAKE_STATE.INTAKE);
    }

    public void update() {
        for (LynxModule hub : allHubs) {
            hub.clearBulkCache();
        }

        currentVoltage = batteryVoltage.getVoltage();

        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);

        currentGamepad1 = opMode.gamepad1;
        currentGamepad2 = opMode.gamepad2;

        follower.update();
        PoseStorage.setPose(follower.getPose());

        // marrow spatial
        robotZone.setPosition(follower.getPose().getX(), follower.getPose().getY());
        robotZone.setRotation(follower.getPose().getHeading());

        /*
        if (relocalizeTimer.isDone() || firstRelocalization) {
            boolean success = relocalize();
            if (success) {
                firstRelocalization = false;
                relocalizeTimer.restart();
            }
        }
         */

        intakeRoller.update();

        if (isShooting) {
            outtakeDoor.block(false);
            if (robotZone.isInside(GlobalPose.ZONES.closeLaunchZone)) {
                intakeRoller.setState(IntakeRoller.INTAKE_STATE.GOAL_SHOOTING);
            } else intakeRoller.setState(IntakeRoller.INTAKE_STATE.FAR_SHOOTING);
        } else {
            outtakeDoor.block(true);
        }

        if (!isShooting) setShooterTarget(vel, angle, heading);
        else setShooterTarget(vel + Constants.SHOOTER.shootingIncrement, angle, heading);

        turret.update();
        shooter.update();
        hood.update();
    }

    public void stop() {
        camera.stop();
        ledIndicator.set(false);
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
        follower.update();
        PoseStorage.setPose(follower.getPose());
        robotZone.setPosition(follower.getPose().getX(), follower.getPose().getY());
        robotZone.setRotation(follower.getPose().getHeading());
    }

    public void updateTelemetry(boolean getDrive, boolean getShooter, boolean getIntake, boolean getCamera, boolean getPathstate) {
        telemetryM.debug("update time: " + updateTime.milliseconds() + "ms\n");
        updateTime.reset();

        if (getShooter) {
            //shooter
            telemetryM.debug("is shooting: " + isShooting);
            telemetryM.debug("shoot velocity:" + shooter.getVelocity());
            telemetryM.debug("shoot target:" + shooter.getTarget());
            telemetryM.debug("shoot power:" + shooter.power);
            telemetryM.debug("shoot amp: " + shooter.shoot1.getCurrent(CurrentUnit.AMPS));

            //hood
            telemetryM.debug("hood angle:" + hood.getCurrentAngle());
            telemetryM.debug("hood target angle:" + hood.getTargetAngle());

            //turret
            telemetryM.debug("turret angle: " + turret.getDegreeFromTick(turret.getCurrentPosition()));
            telemetryM.debug("turret target: " + turret.getTarget());
            telemetryM.debug("turret power: " + turret.getPower());
            telemetryM.debug("turret amp: " + turret.turret.getCurrent(CurrentUnit.AMPS));
            telemetryM.debug("turret is in tolerance: " + turret.controlSystem.isWithinTolerance(new KineticState(Constants.TURRET.tolerance)));
            telemetryM.debug("turret tick: " + turret.getCurrentPosition());
            telemetryM.addLine("");
        }

        if (getIntake) {
            //intake
            telemetryM.debug("intake velocity:" + intakeRoller.getVelocity());
            telemetryM.debug("intake power:" + intakeRoller.getPower());
            telemetryM.debug("intake amp:" + intakeRoller.intake.getCurrent(CurrentUnit.AMPS));

            //door
            telemetryM.debug("door is block:" + outtakeDoor.isBlocked());
            telemetryM.addLine("");
        }

        //drivetrain
        if (getPathstate) {
            telemetryM.debug("Path State: " + pathState);
        }

        if (getDrive) {
            telemetryM.debug("drive X:" + follower.getPose().getX());
            telemetryM.debug("drive Y:" + follower.getPose().getY());
            telemetryM.debug("drive Heading:" + follower.getPose().getHeading());
            telemetryM.debug("drive is field centric:" + isFieldCentric);

            telemetryM.debug(" pure turret target: ", ShootingMath.getTurretHeadingFromOdometry(follower.getPose(), ShootingMath.getStaticGoalVector(follower.getPose())));
            telemetryM.debug("distance from tag odo: ", ShootingMath.getStaticGoalVector(follower.getPose()).getMagnitude());
            telemetryM.addLine("");

            Drawing.drawDebug(follower);
        }

        if (getCamera) {
            LLStatus status = camera.getStatus();
            Pose aprilTagPose = camera.getAprilTagPose(Math.toDegrees(follower.getPose().getHeading()));
            ledIndicator.set(camera.getDetect());
            telemetryM.debug("pipeline number: " + status.getPipelineIndex());
            telemetryM.debug("temp: " + status.getTemp() + "; fps: " + (int)status.getFps());
            telemetryM.debug("pose: " + aprilTagPose.toString());
            telemetryM.addLine("");
        }

        telemetryM.update(opMode.telemetry);
    }

    public void setShooterTarget(double velocity, double hoodAngle, double turretHeading) {
        shooter.setTarget(velocity);
        hood.setTargetAngle(hoodAngle);
        turret.setTarget(turretHeading);
    }

    boolean unJamTurret = false;

    public void aimShoot(boolean aimVelAndAngle, boolean aimHeading) {
        ShooterState shooterState = InterpLUTShooterCalculator.calcShoot(follower.getPose());
        if (aimVelAndAngle) {
            vel = shooterState.getVelocity();
            if (!unJamTurret) angle = shooterState.getAngle();
            else angle = 60;
        }
        if (aimHeading) {
            heading = shooterState.getHeading();
        }
    }

    public void aimShootMoving(boolean aimVelAndAngle, boolean aimHeading) {
        ShooterState shooterState = InterpLUTShooterCalculator.calcShootOnMoving(follower);
        if (aimVelAndAngle) {
            vel = shooterState.getVelocity();
            if (!unJamTurret) angle = shooterState.getAngle();
            else angle = 60;
        }
        if (aimHeading) {
            heading = shooterState.getHeading();
        }
    }

    boolean isAutonomous = false;

    public void teleOpControl() {
        if (currentGamepad2.xWasPressed()) {
            unJamTurret = true;
        } else unJamTurret = false;

        if (currentGamepad1.right_trigger > 0.1)
            intakeRoller.setState(IntakeRoller.INTAKE_STATE.STOP);
        else if (!isShooting) intakeRoller.setState(IntakeRoller.INTAKE_STATE.INTAKE);

        if (currentGamepad1.left_bumper) {
            isShooting = true;
        } else isShooting = false;

        if (currentGamepad1.optionsWasPressed()) {
            boolean success = relocalize();
            if (success) ledIndicator.set(true);
        } else ledIndicator.set(false);

        if (currentGamepad1.leftTriggerWasPressed()) {
            follower.holdPoint(follower.getPose());
            isAutonomous = true;
        }

        if (currentGamepad1.rightBumperWasPressed()) {
            if (alliance == Constants.ALLIANCE.BLUE) {
                follower.followPath(follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(),
                                GlobalPose.BLUE.RETAKE_POSE.pushLever))
                        .setLinearHeadingInterpolation(follower.getHeading(), Math.toRadians(160))
                        .build());
            } else if (alliance == Constants.ALLIANCE.RED) {
                follower.followPath(follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(),
                                GlobalPose.RED.pushLever))
                        .setLinearHeadingInterpolation(follower.getHeading(), Math.toRadians(20.5))
                        .build());
            }
            isAutonomous = true;
        }

        if (isAutonomous && (Utils.joyStickIsMoving(currentGamepad1, .3))) {
            follower.startTeleopDrive(true);
            isAutonomous = false;
        }
    }

    /** use in autonomous**/
    public void shoot() {
        isShooting = true;
    }
    public void stopShoot() {
        isShooting = false;
        intakeRoller.setState(IntakeRoller.INTAKE_STATE.INTAKE);
    }

    public boolean relocalize() {
        double velocity = follower.getVelocity().getMagnitude();
        double angularVelocity = follower.getAngularVelocity();
        if (Math.abs(velocity) > .2 || Math.abs(angularVelocity) > .2) return false;

        Pose tagPose = camera.getAprilTagPose(Math.toDegrees(follower.getPose().getHeading()));
        if (tagPose.roughlyEquals(new Pose(), 0.001)) return false;

        setPose(tagPose);

        return true;
    }

    public void driveTeleOpControl(double straight, double strafe, double rotate, boolean isRobotCentric) {
        // just a dumbass, pls don't care
        isFieldCentric = !isRobotCentric;

        if (!isAutonomous) {
            if (isRobotCentric)
                follower.setTeleOpDrive(straight, strafe, rotate * Constants.DRIVE.turnSpeedMultiplier, true, 0);
            else
                follower.setTeleOpDrive(straight, strafe, rotate * Constants.DRIVE.turnSpeedMultiplier, false, teleOpFieldFaceAngle);
        }
    }

    public static void setPathState(int pState) {
        pathState = pState;
    }

    public static double getVolFeedforward() {
        return Constants.nominalVoltage / currentVoltage;
    }
}

