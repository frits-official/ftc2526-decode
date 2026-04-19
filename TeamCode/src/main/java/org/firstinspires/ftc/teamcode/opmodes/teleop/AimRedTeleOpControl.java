package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.PoseStorage;

@TeleOp(group = "A-teleop")
public class AimRedTeleOpControl extends OpMode {
    Robot robot = new Robot();
    boolean isRobotCentric = false;

    @Override
    public void init() {

        robot.init(this, Constants.ALLIANCE.RED);
        robot.setPose(PoseStorage.getPose());
        robot.aimShoot(false, true);
    }

    @Override
    public void init_loop() {
        robot.init_loop();

        telemetry.addLine("This program will open test TELEOP program of RED ALLIANCE, which will only track AprilTag 24 (BLUE GOAL AprilTag).");
        robot.updateTelemetry(true, false, false, false, false);
    }

    @Override
    public void start() {
        robot.start();
        robot.follower.startTeleopDrive(true);
    }

    @Override
    public void loop() {
        if (gamepad1.shareWasPressed()) isRobotCentric = !isRobotCentric;
        robot.driveTeleOpControl(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, isRobotCentric);

        robot.aimShoot(true, true);
        robot.teleOpControl();

        robot.update();

        //Telemetry
        robot.updateTelemetry(false, false, false, false, false);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
