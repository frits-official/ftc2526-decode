package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.PoseStorage;

@TeleOp(group = "test")
public class TestSOTM extends OpMode {
    Robot robot = new Robot();
    boolean isRobotCentric = false;

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(PoseStorage.getPose());
        robot.aimShoot(false, true);
    }

    @Override
    public void init_loop() {
        robot.init_loop();
        telemetry.addLine("sotm test");

        robot.updateTelemetry(false, false, false, false, false);
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

        robot.aimShootMoving(true, true);
        robot.teleOpControl();

        robot.update();

        //Telemetry
        robot.updateTelemetry(true, true, false, true, false);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
