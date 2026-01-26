package org.firstinspires.ftc.teamcode.opmodes.test;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

@Autonomous(group = "test")
public class Test_Auto extends LinearOpMode {
    Turret turret = new Turret();
    Robot robot = new Robot();
    private int pathState;
    private Timer pathTimer, opmodeTimer;
    //Pose Start
    private final Pose startPose = new Pose(49.64, 137.14, Math.toRadians(180));
    private final Pose scorePose = new Pose(48.43, 96.54, Math.toRadians(135));
    //Pose Path1
    private final Pose pickup1_1 = new Pose(45.88, 84.19, Math.toRadians(180));
    private final Pose pickup1_2 = new Pose(27.54, 84.52, Math.toRadians(180));
    //Pose Path2
    private final Pose pickup2_1 = new Pose(43.84, 60.11, Math.toRadians(180));
    private final Pose pickup2_2 = new Pose(27.31, 60.12, Math.toRadians(180));
    //Pose Path3
    private final Pose pickup3_1 = new Pose(44.25, 37.91, Math.toRadians(180));
    private final Pose pickup3_2 = new Pose(25.3, 38.54, Math.toRadians(180));
    //Pose End
    private final Pose endPose = new Pose(43.61, 72.41, Math.toRadians(180));

    public void autonomousPathUpdate() {
        switch (pathState) {
            //Start
            case 0:
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(startPose, scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                        .build());
                robot.aimShoot(true, false);
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
                    robot.aimShoot(false, false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup1_1))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), pickup1_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                            .build(),true);
                    robot.aimShoot(true, false);
                    setPathState(4);
                }
                break;
            case 4:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(5);
                }
                break;

            //Path2
            case 5:
                if (!robot.running) {
                    robot.aimShoot(false, false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup2_1))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), pickup2_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                            .build(), true);
                    robot.aimShoot(true, false);
                    setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(8);
                }
                break;

            //Path3
            case 8:
                if (!robot.running) {
                    robot.aimShoot(false, false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), pickup3_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                            .build(), true);
                    robot.aimShoot(true, false);
                    setPathState(10);
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(11);
                }
                break;

            //End
            case 11:
                if (!robot.running) {
                    robot.aimShoot(false, false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .build(), true);
                    setPathState(12);
                }
                break;
            case 12:
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
        setPathState(0);

        waitForStart();

        while (opModeIsActive()) {
            robot.update();
            robot.intakeAuto(true);
            autonomousPathUpdate();

            robot.updateTelemetry(true, true, true, false);
        }
    }
}
