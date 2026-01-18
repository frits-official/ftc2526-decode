package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@Disabled
@TeleOp(group = "A-teleop")
public class RedTeleOpControl extends LinearOpMode {
    Robot robot = new Robot();
    boolean isFieldCentric = true;

    @Override
    public void runOpMode() {
        //Telemetry
        robot.init(this, Constants.ALLIANCE.RED);
        robot.setPose(new Pose(72, 72, 0));
        robot.setShooterTarget(0, 25, 0);

        telemetry.addLine("This program will open test TELEOP program of RED ALLIANCE, which will only track AprilTag 24 (BLUE GOAL AprilTag).");
        telemetry.update();

        waitForStart();

        //Drivetrain
        if (opModeIsActive()) {
            while(opModeIsActive()) {
                if (gamepad1.start || gamepad1.options) {
                    robot.resetPose(false, false, true);
                }

                if (gamepad1.back || gamepad1.share) isFieldCentric = !isFieldCentric;
                robot.driveTeleOpControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, isFieldCentric);

                robot.intakeFunnelTeleOpControl();

                //robot.aimShoot(true, false);

                robot.update();

                //Telemetry
                robot.updateTelemetry(true, true, true, true);
            }
        }
    }
}
