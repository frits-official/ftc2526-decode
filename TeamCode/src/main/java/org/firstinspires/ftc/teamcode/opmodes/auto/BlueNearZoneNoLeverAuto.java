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
public class BlueNearZoneNoLeverAuto extends LinearOpMode {
    Robot robot = new Robot();
    private int pathState;
    private Timer pathTimer, opmodeTimer;
    //Pose Start
    private final Pose startPose = new Pose(50.3, 136.91, Math.toRadians(180));
    private final Pose scorePose = new Pose(49.97, 95.84, Math.toRadians(180));
    //Pose Path1
    private final Pose pickup1_1 = new Pose(45.38, 84.87, Math.toRadians(180));
    private final Pose pickup1_2 = new Pose(41.59, 83.88, Math.toRadians(180));
    //Pose Path2
    private final Pose pickup2_1 = new Pose(43.52, 60.22, Math.toRadians(180));
    private final Pose pickup2_2 = new Pose(26.44, 60.06, Math.toRadians(180));
    //Pose Path3
    private final Pose pickup3_1 = new Pose(44.34, 36.26, Math.toRadians(180));
    private final Pose pickup3_2 = new Pose(25.69, 35.96, Math.toRadians(180));
    //Pose End
    private final Pose endPose = new Pose(42.21, 71.35, Math.toRadians(180));

    public void autonomousPathUpdate() {
        switch (pathState) {
            //Start
            case 0:
                PathChain Start = robot.follower.pathBuilder()
                        .addPath(new BezierLine(startPose, scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                        .build();
                robot.follower.followPath(Start);
                setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(2);
                }
                break;

            //Path1
            case 2:
                if (!robot.running) {
                    PathChain Pickup1_1 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup1_1))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build();
                    robot.follower.followPath(Pickup1_1, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    PathChain Pickup1_2 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup1_2))
                            .setTangentHeadingInterpolation()
                            .build();
                    robot.follower.followPath(Pickup1_2, true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!robot.follower.isBusy()) {
                    PathChain Score1 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build();
                    robot.follower.followPath(Score1, true);
                    setPathState(5);
                }
                break;
            case 5:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(6);
                }
                break;

            //Path2
            case 6:
                if (!robot.running) {
                    PathChain Pickup2_1 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup2_1))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build();
                    robot.follower.followPath(Pickup2_1, true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    PathChain Pickup2_2 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup2_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build();
                    robot.follower.followPath(Pickup2_2, true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!robot.follower.isBusy()) {
                    PathChain Score2 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build();
                    robot.follower.followPath(Score2, true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(10);
                }
                break;

            //Path3
            case 10:
                if (!robot.running) {
                    PathChain Pickup3_1 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build();
                    robot.follower.followPath(Pickup3_1, true);
                    setPathState(11);
                }
                break;
            case 11:
                if (!robot.follower.isBusy()) {
                    PathChain Pickup3_2 = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup3_2))
                            .setTangentHeadingInterpolation()
                            .build();
                    robot.follower.followPath(Pickup3_2, true);
                    setPathState(12);
                }
            case 12:
                if (!robot.follower.isBusy()) {
                    PathChain End = robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build();
                    robot.follower.followPath(End, true);
                    setPathState(13);
                }
                break;

            //End
            case 13:
                if (!robot.follower.isBusy()) {
                    setPathState(-1);
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
        robot.aimShoot(false, false);

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        waitForStart();

        if (opModeIsActive()) {
            setPathState(0);
            opmodeTimer.resetTimer();

            while (opModeIsActive()) {
                robot.update();
                autonomousPathUpdate();
                robot.aimShoot(true, false);
                robot.intakeAuto(true);

                robot.updateTelemetry(true, true, true, false);
            }
        }
    }
}
