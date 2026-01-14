package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.command.ShooterAim;

@TeleOp(group = "test")
public class TestAim extends LinearOpMode {
    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("turret target: ", ShooterAim.calcTurretHeadingFromOdometry(robot.follower.getPose(), Constants.ALLIANCE.BLUE));
            telemetry.addData("distance from tag: ", ShooterAim.calcDistanceFromTag(robot.camera.getDistanceFromGoalTagCM(), robot.follower.getPose(), Constants.ALLIANCE.BLUE));
            telemetry.update();
        }
    }
}
