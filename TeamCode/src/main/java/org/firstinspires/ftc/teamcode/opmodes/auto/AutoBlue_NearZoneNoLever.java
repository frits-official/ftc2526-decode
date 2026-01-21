package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(group = "auto")
public class AutoBlue_NearZoneNoLever extends LinearOpMode {
    Robot robot = new Robot();
    private final Pose startPose = new Pose(48.95, 136.11, -1.56);
    private final Pose Pose1 = new Pose(48.85, 95.94, 3.14);
    private final Pose Pose2 = new Pose(32.62, 83.13, 3.14);

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(new Pose(72,72, 0));

        while (!isStarted()) {
            robot.follower.update();
            robot.updateTelemetry(true, false, false, false);
        }

        robot.follower.setPose(startPose);

        PathChain pathChain = robot.follower.pathBuilder()
                .addPath(new BezierLine(startPose, Pose1))
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(180))
                .addPath(new BezierLine(Pose1, Pose2))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        waitForStart();

        if (opModeIsActive()) {
            robot.follower.followPath(pathChain);

            while (opModeIsActive() && robot.follower.isBusy()) {
                robot.follower.update();

                robot.updateTelemetry(true, true, true, false);
            }
        }
    }
}
