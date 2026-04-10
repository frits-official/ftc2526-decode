package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.TheoreticalShooterCalculator;
import org.firstinspires.ftc.teamcode.misc.ShooterState;

@TeleOp(group = "test")
public class TestShootTheoretical extends OpMode {
    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.turret.resetEncoder();
        robot.setPose(new Pose(72, 72, 0));
    }

    @Override
    public void init_loop() {
        robot.init_loop();
    }

    @Override
    public void start() {
        robot.start();
    }

    @Override
    public void loop() {
        robot.driveTeleOpControl(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        ShooterState shooterState = TheoreticalShooterCalculator.calcShoot(robot.follower);
        robot.vel = shooterState.getVelocity();
        robot.angle = shooterState.getAngle();
        robot.heading = shooterState.getHeading();
        robot.update();
        robot.updateTelemetry(true, true,false,true, true);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
