package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@TeleOp(group = "A-teleop")
public class AimBlueTeleOpControl extends LinearOpMode {
    Robot robot = new Robot();
    boolean isFieldCentric = false;

    @Override
    public void runOpMode() {
        //Telemetry
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose((GlobalPose.lastPose != null) ? GlobalPose.lastPose : new Pose(72, 72, Math.toRadians(180)));
        robot.aimShoot(false, true);

        while (!isStarted()) {
            telemetry.addLine("This program will open test TELEOP program of BLUE ALLIANCE, which will only track AprilTag 20 (BLUE GOAL AprilTag).");

            robot.init_loop();
            robot.update();
            robot.updateTelemetry(true, false, false, false);
        }

        //Drivetrain
        if (opModeIsActive()) {

            while(opModeIsActive()) {
                // if (gamepad1.start || gamepad1.options) {
                //    robot.resetPose(false, false, true);
                // }

                // if (gamepad1.back || gamepad1.share) isFieldCentric = !isFieldCentric;
                robot.driveTeleOpControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, isFieldCentric);

                robot.intakeFunnelTeleOpControl();

                robot.outtakeTeleOpControl();

                robot.update();

                //Telemetry
                robot.updateTelemetry(true, true, true, true);
            }
        }
    }
}
