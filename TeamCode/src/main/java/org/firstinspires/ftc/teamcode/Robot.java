package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.commands.GlobalPose;
import org.firstinspires.ftc.teamcode.commands.ShooterAim;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;
import org.firstinspires.ftc.teamcode.subsystems.intake.OuttakeDoor;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Hood;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

import java.util.List;

import dev.nextftc.control.KineticState;

public class Robot {
    public LinearOpMode opMode;
    public Follower follower;
    public IntakeRoller intakeRoller = new IntakeRoller();
    public OuttakeDoor outtakeDoor = new OuttakeDoor();
    public Shooter shooter = new Shooter();
    public Hood hood = new Hood();
    public Turret turret = new Turret();
    public TelemetryManager telemetryM;
    private DcMotor rf, rr, lf, lr;
    private boolean isFieldCentric;
    public Camera camera = new Camera();
    private Constants.ALLIANCE alliance;
    private ElapsedTime time = new ElapsedTime();
    public boolean running = false;
    double turretHeadingFromCam = 0.0;
    boolean brake = false;
    double vel = 0, angle = 0, heading = 0;
    List<LynxModule> allHubs;
    ElapsedTime updateTime = new ElapsedTime();
    double turretOffset = 0;
    LLResult result;
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();

    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();


    public void init(LinearOpMode _opmode, Constants.ALLIANCE alliance) {
        opMode = _opmode;
        this.alliance = alliance;

        follower = DriveConstants.createFollower(opMode.hardwareMap);

        lf = opMode.hardwareMap.get(DcMotor.class, "lf");
        lr = opMode.hardwareMap.get(DcMotor.class, "lr");
        rf = opMode.hardwareMap.get(DcMotor.class, "rf");
        rr = opMode.hardwareMap.get(DcMotor.class, "rr");

        lf.setDirection(DcMotorSimple.Direction.FORWARD);
        lr.setDirection(DcMotorSimple.Direction.FORWARD);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rr.setDirection(DcMotorSimple.Direction.REVERSE);

        shooter.init(opMode.hardwareMap);
        hood.init(opMode.hardwareMap);
        turret.init(opMode.hardwareMap);

        outtakeDoor.init(opMode.hardwareMap);
        intakeRoller.init(opMode.hardwareMap);

        camera.init(opMode.hardwareMap, alliance);

        allHubs = opMode.hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        setShooterTarget(0, 0, 0);
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
        follower.update();
        GlobalPose.lastPose = follower.getPose();
    }

    public void resetPose(boolean resetX, boolean resetY, boolean resetHeading) {
        Pose p = follower.getPose();
        Pose newP = new Pose(0, 0, 0);
        if (resetX) newP = new Pose(0, p.getY(), p.getHeading());
        if (resetY) newP = new Pose(newP.getX(), 0, p.getHeading());
        if (resetHeading) newP = new Pose(newP.getX(), newP.getY(), 0);
        setPose(newP);
    }

    public void updateTelemetry(boolean getDrive, boolean getShooter, boolean getIntake, boolean getCamera) {
        telemetryM.debug("update time: " + updateTime.milliseconds() + "ms\n");
        updateTime.reset();

        if (getShooter) {
            //shooter
            telemetryM.debug("shoot velocity:" + shooter.getVelocity());
            telemetryM.debug("shoot target:" + shooter.getTarget());
            telemetryM.debug("shoot power:" + shooter.power);

            //hood
            telemetryM.debug("hood angle:" + hood.getAngle());
            telemetryM.debug("hood target angle:" + hood.getTargetAngle());
            telemetryM.debug("hood power:" + hood.getPower());

            //turret
            telemetryM.debug("turret angle: " + turret.getDegree(turret.getCurrentPosition()));
            telemetryM.debug("turret target: " + turret.getTarget());
            telemetryM.debug("turret power: " + turret.getPower());
            telemetryM.debug("turret í in tolerance: " + turret.controlSystem.isWithinTolerance(new KineticState(Constants.TURRET.tolerance)));
            telemetryM.addLine("");
        }

        if (getIntake) {
            //intake
            telemetryM.debug("intake velocity:" + intakeRoller.getVelocity());
            telemetryM.debug("intake power:" + intakeRoller.getPower());

            //door
            telemetryM.debug("door is block:" + outtakeDoor.isBlocked());
            telemetryM.addLine("");
        }

        //drivetrain
        if (getDrive) {
            telemetryM.debug("drive X:" + follower.getPose().getX());
            telemetryM.debug("drive Y:" + follower.getPose().getY());
            telemetryM.debug("drive Heading:" + follower.getPose().getHeading());
            telemetryM.debug("drive is field centric:" + isFieldCentric);

            telemetryM.debug(" pure turret target: ", ShooterAim.calcTurretHeadingFromOdometry(follower.getPose(), alliance));
            telemetryM.debug("distance from tag odo: ", ShooterAim.calcDistanceFromTagOdometryCM(follower.getPose(), alliance));
            telemetryM.addLine("");
        }

        if (getCamera) {
            LLStatus status = camera.getStatus();
            telemetryM.debug("pipeline number: " + status.getPipelineIndex());
            telemetryM.debug("temp: " + status.getTemp() + "; fps: " + (int)status.getFps());
            LLResult result = camera.getLastestResult();
            if (result != null) {
                telemetryM.debug("tx:" + result.getTx());
                telemetryM.debug("ty:" + result.getTy());
            } else telemetryM.addLine("detect nothing from camera");
            telemetryM.addLine("");
        }

        telemetryM.update(opMode.telemetry);

        currentGamepad1 = opMode.gamepad1;
        currentGamepad2 = opMode.gamepad2;
    }

    public void setShooterTarget(double velocity, double hoodAngle, double turretHeading) {
        shooter.setTarget(velocity);
        hood.setTargetAngle(hoodAngle);
        turret.setTarget(turretHeading);
        turret.setCamResult(camera.getLastestResult());
    }

    public void aimShoot(boolean aimVelAndAngle, boolean aimHeading) {
        if (aimVelAndAngle) {
            ShooterAim.ShooterState shooterState = ShooterAim.calcShoot(follower.getPose(), alliance);
            vel = shooterState.getVelocity();
            angle = shooterState.getAngle();
        }
        if (aimHeading) {
            heading = ShooterAim.calcTurretHeadingFromOdometry(follower.getPose(), alliance);
        }
        setShooterTarget(vel, angle, heading + turretOffset);
    }

    public void init_loop() {
        aimShoot(false, true);
        update();
    }

    public void update() {
        follower.update();
        GlobalPose.lastPose = follower.getPose();
        // LLResult result = camera.getLastestResult();
        // if (result != null) {
        //    turretHeadingFromCam =  camera.getLastestResult().getTy();
        // }
        result = camera.getLastestResult();
        turret.update();
        shooter.update();
        hood.update();

        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);

        currentGamepad1 = opMode.gamepad1;
        currentGamepad2 = opMode.gamepad2;

        updateUnblockAndShoot();
    }

    public void intakeFunnelTeleOpControl() {
        intakeRoller.teleOpControl(opMode.gamepad1);
    }

    public void intakeAuto(boolean active) {
        if (active) {
            intakeRoller.setPower(.7);
        } else {
            intakeRoller.setPower(0);
        }
    }

    public void setTurretOffset() {
        if (result != null && result.isValid()) turretOffset = -result.getTy();
    }
    public void outtakeTeleOpControl() {
        aimShoot(true, true);

        //if (opMode.gamepad2.left_bumper) turretOffset += 1;
        //if (opMode.gamepad2.right_bumper) turretOffset -= 1;

        if (currentGamepad1.a && !previousGamepad1.a) {
            setTurretOffset();
        }

        if (result != null && result.isValid() && Math.abs(result.getTy()) < 2) opMode.gamepad1.rumble(100);

        if (opMode.gamepad1.left_bumper && !running) {
            //brakeDrive(true);
            unBlockAndShoot();
        }
    }

    public void unBlockAndShoot() {
        running = true;
        time.reset();
    }

    public void driveTeleOpControl(double straight, double strafe, double rotate, boolean isFieldCentric) {
        if (!brake) {
            this.isFieldCentric = isFieldCentric;

            double rotX = strafe, rotY = straight;

            if (isFieldCentric) {
                double botHeading = follower.getPose().getHeading();

                rotX = strafe * Math.cos(-botHeading) - straight * Math.sin(-botHeading);
                rotY = strafe * Math.sin(-botHeading) + straight * Math.cos(-botHeading);
            }

            rotX = rotX * 1.1;

            double frontLeftPower = (rotY + rotX + rotate);
            double backLeftPower = (rotY - rotX + rotate);
            double frontRightPower = (rotY - rotX - rotate);
            double backRightPower = (rotY + rotX - rotate);

            lf.setPower(frontLeftPower);
            lr.setPower(backLeftPower);
            rf.setPower(frontRightPower);
            rr.setPower(backRightPower);
        } else {
            lf.setPower(0);
            lr.setPower(0);
            rf.setPower(0);
            rr.setPower(0);
        }
    }

    //public void stop() {
        //camera.stop();
    //}

    public void updateUnblockAndShoot() {
        if (running) {
            double seconds = time.seconds();

            if (seconds < Math.abs(Constants.DOOR.delayTime - Constants.DOOR.openTime)) {
                intakeRoller.setPower(1);
                outtakeDoor.block(true);
            } else if (seconds < Constants.DOOR.delayTime) {
                intakeRoller.setPower(1);
                outtakeDoor.block(false);
            } else {
                intakeRoller.setPower(.7);
                outtakeDoor.block(true);
                brakeDrive(false);
                running = false;
            }
        }
    }

    public void brakeDrive(boolean brake) {
        this.brake = brake;
        if (brake) {
            lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else {
            lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
}

