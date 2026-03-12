package org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto.FarZone;

import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@Autonomous
public class RedFarZoneLeverPath3_2_1Path2Ball12NoIndulge extends OpMode {
    Robot robot = new Robot();
    private ElapsedTime timer = new ElapsedTime();
    private double startDelay;

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.RED);
        robot.setPose(GlobalPose.RED.RedFarZonePose.startPose);
        robot.aimShoot(false, false);
        robot.turret.resetEncoder();

        Robot.pathState = -1;
    }

    @Override
    public void init_loop() {
        robot.init_loop();
    }

    @Override
    public void start() {
        timer.reset();
        startDelay = timer.seconds();
        Robot.pathState = 0;
    }

    @Override
    public void loop() {
        robot.update();

        if (timer.seconds() - startDelay < 2) {
            robot.aimShoot(true, false);
        } else {
            robot.aimShoot(true, true);
            autonomousPathUpdate();
        }

        robot.updateTelemetry(true, true, true, true, true);
    }

    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            // Start
            case 0:
                robot.intakeAuto(false);
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.RED.RedFarZonePose.startPose, GlobalPose.RED.RedFarZonePose.scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(69))
                        .build(), true);
                Robot.setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    Robot.setPathState(2);
                }
                break;

            // Path 1
            case 2:
                if (!robot.running) {
                    robot.intakeAuto(true);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.PICKUP_POSE_RED.pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(69), Math.toRadians(0))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.PICKUP_POSE_RED.pickup3_2))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.RedFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(69))
                            .build(), true);
                    Robot.setPathState(4);
                }
                break;
            case 4:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    Robot.setPathState(5);
                }
                break;

            // Path 2
            case 5:
                if (!robot.running) {
                    robot.intakeAuto(true);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.PICKUP_POSE_RED.pickup2_1))
                            .setLinearHeadingInterpolation(Math.toRadians(69), Math.toRadians(0))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.PICKUP_POSE_RED.pickup2_2))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.RedFarZonePose.pushLever))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(270))
                            .build(), true);
                    Robot.setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.RedFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(69))
                            .build(), true);
                    Robot.setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    Robot.setPathState(8);
                }
                break;

            // Path 3
            case 8:
                if (!robot.running) {
                    robot.intakeAuto(true);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.PICKUP_POSE_RED.pickup1_1))
                            .setLinearHeadingInterpolation(Math.toRadians(69), Math.toRadians(0))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.PICKUP_POSE_RED.pickup1_2))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.RED.scorePoseEnd))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45))
                            .build(), true);
                    Robot.setPathState(10);
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    Robot.setPathState(11);
                }
                break;

            case 11:
                if (!robot.follower.isBusy()) {
                    Robot.setPathState(-1);
                }
                break;
        }
    }
}