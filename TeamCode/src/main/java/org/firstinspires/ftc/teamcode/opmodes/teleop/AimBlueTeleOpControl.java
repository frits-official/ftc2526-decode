package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.GlobalPose;

@TeleOp(group = "A-teleop")
public class AimBlueTeleOpControl extends OpMode {
    Robot robot = new Robot();
    boolean isRobotCentric = true;

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose((GlobalPose.lastPose != null) ? GlobalPose.lastPose : new Pose(72, 72, Math.toRadians(0)));
        robot.aimShoot(false, true);
    }

    @Override
    public void init_loop() {
        robot.init_loop();
        telemetry.addLine("This program will open test TELEOP program of BLUE ALLIANCE, which will only track AprilTag 20 (BLUE GOAL AprilTag).");

        robot.updateTelemetry(true, false, false, false, false);
    }

    @Override
    public void start() {
        robot.start();
    }

    @Override
    public void loop() {
        if (gamepad1.back || gamepad1.share) isRobotCentric = !isRobotCentric;
        robot.driveTeleOpControl(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, isRobotCentric);

        robot.aimShoot(true, true);
        robot.teleOpControl();

        robot.update();

        //Telemetry
        robot.updateTelemetry(true, true, true, true, false);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
