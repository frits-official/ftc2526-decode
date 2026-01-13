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
    Robot robot = new Robot();
    boolean isFieldCentric = true;

    @Override
    public void runOpMode() {
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

                if (gamepad1.back) isFieldCentric = !isFieldCentric;
                robot.driveTeleOpControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, isFieldCentric);

                robot.intakeFunnelTeleOpControl();
                robot.update();

                //Telemetry
                robot.updateTelemetry(true, true, true);
            }
        }
    }
}
