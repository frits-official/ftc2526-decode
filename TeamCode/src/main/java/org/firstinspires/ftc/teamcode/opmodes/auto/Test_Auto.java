package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(group = "auto")
public class Test_Auto extends LinearOpMode {
    Robot robot = new Robot();
    private int pathState;
    private Timer pathTimer, opmodeTimer;
    private PathChain Start;
    private PathChain Pickup1_1, Pickup1_2, Score1, Pickup2_1, Pickup2_2, Score2, Pickup3_1, Pickup3_2, Score3;
    private final Pose startPose = new Pose(50.89, 137.35, Math.toRadians(180));
    private final Pose startScore = new Pose(48.12, 95.52, Math.toRadians(180));
    private final Pose pickup1_1 = new Pose(38.82, 85.04, Math.toRadians(180));

    public void buildPath() {
        //Start
        Start = robot.follower.pathBuilder()
                .addPath(new BezierLine(startPose, startScore))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        //Pickup1
        Pickup1_1 = robot.follower.pathBuilder()
                .addPath(new BezierLine(startScore, pickup1_1))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                robot.follower.followPath(Start);
                setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(Pickup1_1, true);
                    setPathState(2);
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(startPose);

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        if (opModeIsActive()) {
            setPathState(0);
            opmodeTimer.resetTimer();

            while (opModeIsActive()) {
                robot.follower.update();
                autonomousPathUpdate();

                robot.updateTelemetry(true, true, true, false);
            }
        }
    }
}
