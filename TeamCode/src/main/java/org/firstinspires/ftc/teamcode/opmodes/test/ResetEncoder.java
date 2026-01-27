package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(group = "test")
public class ResetEncoder extends LinearOpMode {
    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(new Pose(72, 72,0));

        waitForStart();

        robot.turret.resetEncoder();

        while (opModeIsActive()) {
            robot.updateTelemetry(true, true,false,true);
        }
    }
}
