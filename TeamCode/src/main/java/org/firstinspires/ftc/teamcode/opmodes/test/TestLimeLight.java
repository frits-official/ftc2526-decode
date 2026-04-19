package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.TheoreticalShooterCalculator;
import org.firstinspires.ftc.teamcode.misc.ShooterState;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;

@TeleOp(group = "test")
public class TestLimeLight extends OpMode {
    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.RED);
        robot.setPose(new Pose(72, 72, 0));
    }

    @Override
    public void init_loop() {
        robot.init_loop();
    }

    @Override
    public void start() {
        robot.start();
        robot.intakeRoller.setState(IntakeRoller.INTAKE_STATE.STOP);
    }

    @Override
    public void loop() {
        robot.update();
        robot.updateTelemetry(true, false, false,true, false);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
