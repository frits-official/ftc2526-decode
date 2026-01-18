package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.ShooterAim;

@TeleOp(group = "test")
public class TestAim extends LinearOpMode {
    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(new Pose(72, 72,0));

        waitForStart();

        while (opModeIsActive()) {
            robot.aimShoot(false, true);

            robot.update(gamepad1);

            robot.updateTelemetry(true, true,false,true);
        }
    }
}
