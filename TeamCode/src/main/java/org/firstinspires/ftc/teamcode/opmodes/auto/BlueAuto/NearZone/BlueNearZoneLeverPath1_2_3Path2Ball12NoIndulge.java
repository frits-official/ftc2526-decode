package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.NearZone;

import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@Autonomous(group = "test")
public class BlueNearZoneLeverPath1_2_3Path2Ball12NoIndulge extends OpMode {
    Robot robot = new Robot();

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BLUE.BlueNearZonePose.startPose);
        robot.aimShoot(false, false);
        robot.turret.resetEncoder();

        Robot.pathState = 0;
    }

    @Override
    public void init_loop() {
        robot.init_loop();
    }

    @Override
    public void start() {
    }
    @Override
    public void loop() {
        robot.update();
        robot.aimShoot(true, true);

        autonomousPathUpdate();

        robot.updateTelemetry(true, true, true, true, true);
    }

    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            // Start
            case 0:
                robot.intakeAuto(false);
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.BLUE.BlueNearZonePose.startPose, GlobalPose.BLUE.BlueNearZonePose.scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                        .build());
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
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup1_1))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup1_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueNearZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
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
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup2_1))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup2_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueNearZonePose.pushLeverPath2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))
                            .build(), true);
                    Robot.setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueNearZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(135))
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
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueNearZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
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

            // End
            case 11:
                if (!robot.running) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueNearZonePose.endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(12);
                }
                break;
            case 12:
                if (!robot.follower.isBusy()) {
                    Robot.setPathState(-1);
                }
                break;
        }
    }
}