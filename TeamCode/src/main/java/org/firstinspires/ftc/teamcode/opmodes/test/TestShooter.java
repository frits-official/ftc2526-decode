package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(group = "test")
@Configurable
public class TestShooter extends OpMode {
    Robot robot = new Robot();
    public static double vel, angle, heading;

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(new Pose(72, 72, 0));
    }

    @Override
    public void loop() {
        robot.setShooterTarget(vel, angle, heading);

        robot.intakeTeleOpControl();
        if (gamepad1.left_bumper) {
            robot.outtakeDoor.block(false);
        } else robot.outtakeDoor.block(true);

        if (gamepad1.cross) {
            robot.turret.setCoefficients();
            robot.shooter.setCoefficients();
        }

        robot.update();

        robot.updateTelemetry(true, true, false, false, false);
    }
}
