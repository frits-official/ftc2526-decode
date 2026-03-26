package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(group = "A-teleop")
public class AimBlueTeleOpControl extends OpMode {
    Robot robot = new Robot();
    boolean isFieldCentric = false;

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
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
        // if (gamepad1.back || gamepad1.share) isFieldCentric = !isFieldCentric;
        robot.driveTeleOpControl(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, isFieldCentric);

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
