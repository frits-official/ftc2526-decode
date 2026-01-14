package org.firstinspires.ftc.teamcode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.command.ShooterAim;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;
import org.firstinspires.ftc.teamcode.subsystems.intake.OuttakeDoor;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Hood;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

public class Robot {
    public LinearOpMode opMode;
    public Follower follower;
    public IntakeRoller intakeRoller = new IntakeRoller();
    public OuttakeDoor outtakeDoor = new OuttakeDoor();
    public Shooter shooter = new Shooter();
    public Hood hood = new Hood();
    public Turret turret = new Turret();
    public TelemetryManager telemetryM;
    private DcMotor rf = null;
    private DcMotor rr = null;
    private DcMotor lf = null;
    private DcMotor lr = null;
    private boolean isFieldCentric;
    public Camera camera = new Camera();
    private Constants.ALLIANCE alliance;

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

        opMode.telemetry.setMsTransmissionInterval(11);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    public void setPose(Pose pose) {
        follower.setPose(pose);
        follower.update();
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
            telemetryM.debug("turret tick: " + turret.getCurrentPosition());
            telemetryM.debug("turret target: " + turret.getTarget());
            telemetryM.debug("turret power: " + turret.getPower());
            telemetryM.addLine("");
        }

        if (getIntake) {
            //intake
            telemetryM.debug("intake velocity:" + intakeRoller.getVelocity());
            telemetryM.debug("intake power:" + intakeRoller.getPower());
            telemetryM.debug("is intake:" + intakeRoller.isIntake());

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
            telemetryM.addLine("");
        }

        //camera
        if (getCamera) {
            LLStatus status = camera.getStatus();
            telemetryM.debug("pipeline number: " + status.getPipelineIndex());
            telemetryM.debug("temp: " + status.getTemp() + "; fps: " + (int)status.getFps());
            LLResult result = camera.getLastestResult();
            if (result != null) {
                telemetryM.debug("tx:" + camera.getLastestResult().getTx());
                telemetryM.debug("ty:" + camera.getLastestResult().getTy());
                telemetryM.debug("distance from target (cm): " + camera.getDistanceFromGoalTagCM());

                telemetryM.addData("Botpose", camera.getRobotPose().toString());
            } else telemetryM.addLine("detect nothing from camera");
            telemetryM.addLine("");
        }

        telemetryM.update(opMode.telemetry);
    }

    public void setShooterTarget(double velocity, double hoodAngle, double turretHeading) {
        shooter.setTarget(velocity);
        hood.setTargetAngle(hoodAngle);
        turret.setTarget(turretHeading);
    }

    public void aimShoot() {
        ShooterAim.ShooterState shooterState = ShooterAim.calcShoot(camera.getDistanceFromGoalTagCM(), follower.getPose(), alliance);
        double heading = ShooterAim.calcTurretHeadingFromOdometry(follower.getPose(), alliance);
        setShooterTarget(shooterState.getVelocity(), shooterState.getAngle(), heading);
    }

    public void update() {
        follower.update();;
        shooter.update();
        hood.update();
        turret.update();
    }

    public void intakeFunnelTeleOpControl() {
        intakeRoller.teleOpControl(opMode.gamepad1);
        outtakeDoor.teleOpControl(opMode.gamepad1);
    }

    public void driveTeleOpControl(double straight, double strafe, double rotate, boolean isFieldCentric) {
        this.isFieldCentric = isFieldCentric;

        double rotX = strafe, rotY = straight;

        if (isFieldCentric) {
            double botHeading = follower.getPose().getHeading();

            rotX = strafe * Math.cos(-botHeading) - straight * Math.sin(-botHeading);
            rotY = strafe * Math.sin(-botHeading) + straight * Math.cos(-botHeading);
        }

        rotX = rotX * 1.1;

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotate), 1);
        double frontLeftPower = (rotY + rotX + rotate) / denominator;
        double backLeftPower = (rotY - rotX + rotate) / denominator;
        double frontRightPower = (rotY - rotX - rotate) / denominator;
        double backRightPower = (rotY + rotX - rotate) / denominator;


        lf.setPower(frontLeftPower);
        lr.setPower(backLeftPower);
        rf.setPower(frontRightPower);
        rr.setPower(backRightPower);
    }

    public void stop() {
        camera.stop();
    }
}

