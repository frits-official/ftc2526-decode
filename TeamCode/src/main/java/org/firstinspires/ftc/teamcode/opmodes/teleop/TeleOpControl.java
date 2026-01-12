package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Hood;
import org.firstinspires.ftc.teamcode.subsystems.intake.OuttakeDoor;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

@TeleOp(group = "teleop")
public class TeleOpControl extends LinearOpMode {
    private DcMotor rf = null;
    private DcMotor rr = null;
    private DcMotor lf = null;
    private DcMotor lr = null;

    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        lr = hardwareMap.get(DcMotor.class, "lr");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rr = hardwareMap.get(DcMotor.class, "rr");

        lf.setDirection(DcMotorSimple.Direction.FORWARD);
        lr.setDirection(DcMotorSimple.Direction.FORWARD);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rr.setDirection(DcMotorSimple.Direction.REVERSE);
        //Telemetry
       robot.init(this);
       robot.setPose(new Pose(0, 0, 0));
       robot.setShooterTargetManual(0, 25, 0);

        waitForStart();

        //Drivetrain
        if (opModeIsActive()) {

            while(opModeIsActive()) {
                if (gamepad1.a) {
                    robot.setShooterTargetManual(1800, 45, 0);
                } else if (gamepad1.x) {
                    robot.setShooterTargetManual(0, 45, 0);

                } else if (gamepad1.y) {
                    robot.setShooterTargetManual(1500, 45, 0);

                }

                if (gamepad1.start) {
                    robot.resetPose(false, false, true);
                }

                //Drivetrain
                double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
                double x = gamepad1.left_stick_x;
                double rx = gamepad1.right_stick_x;

                double botHeading = robot.follower.getPose().getHeading();

                // Rotate the movement direction counter to the bot's rotation
                double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
                double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

                rotX = rotX * 1.1;  // Counteract imperfect strafing

                // Denominator is the largest motor power (absolute value) or 1
                // This ensures all the powers maintain the same ratio,
                // but only if at least one is out of the range [-1, 1]
                double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
                double frontLeftPower = (rotY + rotX + rx) / denominator;
                double backLeftPower = (rotY - rotX + rx) / denominator;
                double frontRightPower = (rotY - rotX - rx) / denominator;
                double backRightPower = (rotY + rotX - rx) / denominator;


                lf.setPower(frontLeftPower);
                lr.setPower(backLeftPower);
                rf.setPower(frontRightPower);
                rr.setPower(backRightPower);

                robot.intakeFunnelTeleOpControl();
                robot.update();

                //Telemetry
                robot.updateTelemetry(true, true, true);
            }
        }
    }
}
