package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.FarZone;

import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@Autonomous
public class BlueFarZonePath3_4Ball9NoLeverIndulge extends OpMode {
    Robot robot = new Robot();
    private ElapsedTime time = new ElapsedTime();
    private double shootTimerValue;

    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BLUE.BlueFarZonePose.startPose);
        robot.aimShoot(false, false);
        robot.turret.resetEncoder();

        Robot.setPathState(0);
        telemetry.addData("Status", "Robot Initialized");
    }

    @Override
    public void init_loop() {
        robot.init_loop();
    }

    @Override
    public void start() {
        time.reset();
        shootTimerValue = time.seconds();
    }

    @Override
    public void loop() {
        robot.update();

        if ((time.seconds() - shootTimerValue) < 2) {
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
                        .addPath(new BezierLine(GlobalPose.BLUE.BlueFarZonePose.startPose, GlobalPose.BLUE.BlueFarZonePose.scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
                        .build(), true);
                Robot.setPathState(2);
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
                            .addPath(new BezierLine(GlobalPose.BLUE.BlueFarZonePose.startPose, GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
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
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup4_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(270))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup4_2))
                            .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(270))
                            .build(), true);
                    Robot.setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(111))
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

            // Indulge
            case 8:
                if (!robot.running) {
                    robot.intakeAuto(true);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.indulge1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(135))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.indulge2))
                            .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(90))
                            .build(), true);
                    Robot.setPathState(9);
                }
                break;

            // End
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(111))
                            .build(), true);
                    Robot.setPathState(10);
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    Robot.setPathState(-1);
                }
                break;
        }
    }
}