package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(group = "a-teleop")
public class BlueTeleOpControl extends LinearOpMode {
    Robot robot = new Robot();
    boolean isFieldCentric = true;

    @Override
    public void runOpMode() {
        //Telemetry
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(new Pose(72, 72, 0));
        robot.setShooterTarget(0, 25, 0);

        telemetry.addLine("This program will open test TELEOP program of BLUE ALLIANCE, which will only track AprilTag 20 (BLUE GOAL AprilTag).");
        telemetry.update();

        waitForStart();

        //Drivetrain
        if (opModeIsActive()) {

            while(opModeIsActive()) {
                if (gamepad1.a) {
                    robot.setShooterTarget(1800, 45, 0);
                } else if (gamepad1.x) {
                    robot.setShooterTarget(0, 45, 0);

                } else if (gamepad1.y) {
                    robot.setShooterTarget(1500, 45, 0);
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
                robot.updateTelemetry(true, true, true, true);
            }
        }
    }
}
