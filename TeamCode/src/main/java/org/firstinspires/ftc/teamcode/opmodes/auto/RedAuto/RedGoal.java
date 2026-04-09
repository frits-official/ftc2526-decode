package org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.GlobalPose;

@Autonomous
public class RedGoal extends OpMode {
    Robot robot = new Robot();
    int reTakeTurn;
    int loopTime = 3;
    TelemetryManager telemetryM;
    ElapsedTime time = new ElapsedTime();
    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            //Start
            case 0:
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.RED.BASIC_POSE_NEAR.startPose,
                                GlobalPose.RED.BASIC_POSE_NEAR.scorePose1))
                        .setLinearHeadingInterpolation(Math.toRadians(217), Math.toRadians(0))
                        .build());
                Robot.setPathState(1);
                break;
            case 1:
                time.reset();
                if (!robot.follower.isBusy()) {
                    if (time.seconds() < 1.5) {
                        robot.shoot();
                    } else {
                        robot.stopShoot();
                        Robot.setPathState(2);
                    }
                }
                break;

            //Stage 1 (Path 2)
            case 2:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(87.32, 59.1),
                                    GlobalPose.RED.PICKUP_POSE.pickup2))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.BASIC_POSE_NEAR.scorePose2))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(4);
                }
                break;
            case 4:
                time.reset();
                if (!robot.follower.isBusy()) {
                    if (time.seconds() < 1.5) {
                        robot.shoot();
                    } else {
                        robot.stopShoot();
                        Robot.setPathState(5);
                    }
                }
                break;

            //Stage 2 (Retake)
            case 5:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(107.45, 67),
                                    GlobalPose.RED.pushLever))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(41))
                            .build(), true);
                    Robot.setPathState(6);
                }
                break;
            case 6:
                time.reset();
                if (!robot.follower.isBusy()) {
                    if (time.milliseconds() < 500) {
                        robot.follower.followPath(robot.follower.pathBuilder()
                                .addPath(new BezierLine(robot.follower.getPose(),
                                        GlobalPose.RED.reTake))
                                .setLinearHeadingInterpolation(Math.toRadians(41), Math.toRadians(41))
                                .build(), true);
                    } else {
                        Robot.setPathState(7);
                    }
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(107.68, 67.57),
                                    GlobalPose.RED.BASIC_POSE_NEAR.scorePose2))
                            .setLinearHeadingInterpolation(Math.toRadians(41), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(8);
                }
                break;
            case 8:
                time.reset();
                if (!robot.follower.isBusy()) {
                    if (time.seconds() < 1.5) {
                        robot.shoot();
                    } else {
                        robot.stopShoot();
                        if (reTakeTurn < loopTime) {
                            reTakeTurn += 1;
                            Robot.setPathState(5);
                        } else {
                            Robot.setPathState(9);
                        }
                    }
                }
                break;

            //Stage 3 (Path 1)
            case 9:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.PICKUP_POSE.pickup1))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(10);
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.BASIC_POSE_NEAR.scorePose2))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(11);
                }
                break;
            case 11:
                time.reset();
                if (!robot.follower.isBusy()) {
                    if (time.seconds() < 1.5) {
                        robot.shoot();
                    } else {
                        robot.stopShoot();
                        Robot.setPathState(12);
                    }
                }
                break;

            //End
            case 12:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.BASIC_POSE_NEAR.endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(13);
                }
                break;
            case 13:
                if (!robot.follower.isBusy()) {
                    Robot.setPathState(-1);
                }
                break;
        }
    }


    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.RED);
        robot.setPose(GlobalPose.RED.BASIC_POSE_NEAR.startPose);
        robot.aimShoot(false, false);

        Robot.setPathState(0);
        reTakeTurn = 0;
        time.reset();

        robot.turret.resetEncoder();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void init_loop() {
        robot.init_loop();
        telemetryM.debug("This program will run automatically at RED NEAR ZONE, " +
                "pick up artifact at PATH 2, RETAKE, PATH 1");
        telemetryM.update(telemetry);
    }

    @Override
    public void loop() {
        robot.update();
        robot.aimShoot(false, false);
        autonomousPathUpdate();

        robot.updateTelemetry(true, true, true, true, true);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
