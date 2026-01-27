package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@Autonomous
public class BlueFarZoneLeverAuto extends LinearOpMode {
    Robot robot = new Robot();
    private int pathState;
    private Timer pathTimer, opmodeTimer;
    private ElapsedTime time = new ElapsedTime();
    public void autonomousPathUpdate() {
        switch (pathState) {
            //Start
            case 0:
                robot.unBlockAndShoot();
                setPathState(1);
                break;

            //Path1
            case 1:
                if (!robot.running) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.PICKUP_POSE_BLUE.pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.PICKUP_POSE_BLUE.pickup3_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
                            .build(), true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(4);
                }
                break;

            //Path2
            case 4:
                if (!robot.running) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.PICKUP_POSE_BLUE.pickup2_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.PICKUP_POSE_BLUE.pickup2_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.pushLeverBlue))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))
                            .build(), true);
                    setPathState(5);
                }
                break;
            case 5:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(111))
                            .build(), true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(7);
                }
                break;

            //Path3
            case 7:
                if (!robot.running) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.PICKUP_POSE_BLUE.pickup1_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.PICKUP_POSE_BLUE.pickup1_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
                            .build(), true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(10);
                }
                break;

            //End
            case 10:
                if (!robot.running) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BlueFarZonePose.endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(111))
                            .build(), true);
                    setPathState(11);
                }
                break;
            case 11:
                if (!robot.follower.isBusy()) {
                    setPathState(-1);
                }
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BlueFarZonePose.startPose);
        robot.aimShoot(false, false);

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        setPathState(0);

        waitForStart();

        if (opModeIsActive()) {
            double seconds = time.seconds();

            if (seconds < 2) {
                robot.update();
                robot.aimShoot(true, true);
            } else {
                while (opModeIsActive()) {
                    robot.update();
                    robot.aimShoot(true, true);
                    robot.intakeAuto(true);
                    autonomousPathUpdate();

                    robot.updateTelemetry(true, true, true, true);
                }
            }
        }
    }
}
