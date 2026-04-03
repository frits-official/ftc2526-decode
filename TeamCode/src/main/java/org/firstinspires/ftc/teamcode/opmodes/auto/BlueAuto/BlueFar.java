package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto;

import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.skeletonarmy.marrow.TimerEx;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.GlobalPose;

@Autonomous
public class BlueFar extends OpMode {
    Robot robot = new Robot();
    private int pathState;
    TimerEx timer = new TimerEx(2);
    public void autonomousPathUpdate() {
        switch (pathState) {
            //Start
            case 0:
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.BLUE.BlueFarZonePose.startPose, GlobalPose.BLUE.BlueFarZonePose.scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
                        .build(), true);
                setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    robot.shoot();
                    setPathState(2);
                }
                break;

            //Path1
            case 2:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(GlobalPose.BLUE.BlueFarZonePose.startPose, GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
                            .build(), true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!robot.follower.isBusy()) {
                    robot.shoot();
                    setPathState(5);
                }
                break;

            //Path2
            case 5:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup2_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup2_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.pushLever))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))
                            .build(), true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(111))
                            .build(), true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    robot.shoot();
                    setPathState(8);
                }
                break;

            //Path3
            case 8:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup1_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup1_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.scorePoseEnd))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                            .build(), true);
                    setPathState(10);
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    robot.shoot();
                    setPathState(11);
                }
                break;

            //End
            case 11:
                if (!robot.follower.isBusy()) {
                    setPathState(-1);
                }
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
    }

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BLUE.BlueFarZonePose.startPose);
        robot.aimShoot(false, false);

        setPathState(0);

        robot.turret.resetEncoder();
    }

    @Override
    public void init_loop() {
        robot.init_loop();
    }

    @Override
    public void start() {
        timer.start();

        while (!timer.isDone()) {
            robot.update();
            robot.aimShoot(true, false);
        }
    }

    @Override
    public void loop() {
        robot.update();
        robot.aimShoot(true, true);
        autonomousPathUpdate();

        robot.updateTelemetry(true, true, true, true, true);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
