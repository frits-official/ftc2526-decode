package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(group = "teleop")
public class TeleOpControl_Pedro extends LinearOpMode {
    Robot robot = new Robot();

    @Override
    public void runOpMode() {
       robot.init(this);
       robot.setPose(new Pose(0, 0, 0));

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

                robot.follower.setTeleOpDrive(-gamepad1.left_stick_y,
                                                -gamepad1.left_stick_x,
                                                -gamepad1.right_stick_x,
                                                false);

                robot.intakeFunnelTeleOpControl();
                robot.update();

                //Telemetry
                robot.updateTelemetry(true, true, true);
            }
        }
    }
}
