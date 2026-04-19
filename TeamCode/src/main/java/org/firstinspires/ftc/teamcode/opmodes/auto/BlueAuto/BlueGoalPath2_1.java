package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto;

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
import org.firstinspires.ftc.teamcode.subsystems.PoseStorage;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;

public class BlueGoalPath2_1 extends OpMode {
    Robot robot = new Robot();
    TelemetryManager telemetryM;
    ElapsedTime time = new ElapsedTime();
    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            //Start
            case 0:
                robot.follower.setMaxPower(1);
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.BLUE.BASIC_POSE_NEAR.startPose,
                                GlobalPose.BLUE.BASIC_POSE_NEAR.scorePoseStart))
                        .setConstantHeadingInterpolation(Math.toRadians(-43))
                        .build());
                Robot.setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(2);
                }
                break;

            //Stage 1 (Path 2)
            case 2:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(49.85, 54.46),
                                    GlobalPose.BLUE.PICKUP_POSE.pickup2))
                            .setConstantHeadingInterpolation(Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                GlobalPose.BLUE.BASIC_POSE_NEAR.scorePosePath))
                                    .setConstantHeadingInterpolation(Math.toRadians(225))
                            .build(), true);
                    Robot.setPathState(4);
                }
                break;
            case 4:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(5);
                }
                break;

            //Stage 2 (Retake)
            //fisrt time
            case 5:
                if (!(time.seconds() < .8)) {
                    robot.stopShoot();
                    robot.follower.setMaxPower(1);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.pushLever))
                                    .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(160))
                            .build(), true);
                    Robot.setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.follower.setMaxPower(1);
                    if (time.seconds() > .4) {
                        robot.follower.followPath(robot.follower.pathBuilder()
                                .addPath(new BezierLine(robot.follower.getPose(),
                                        GlobalPose.BLUE.BASIC_POSE_NEAR.scorePosePath))
                                .setConstantHeadingInterpolation(Math.toRadians(225))
                                .build(), true);
                        Robot.setPathState(7);
                    }
                } else {
                    time.reset();
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(8);
                }
                break;
            //second time
            case 8:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.setMaxPower(1);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.pushLever))
                            .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(160))
                            .build(), true);
                    Robot.setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.follower.setMaxPower(1);
                    if (time.seconds() > 1.2) {
                        robot.follower.followPath(robot.follower.pathBuilder()
                                .addPath(new BezierLine(robot.follower.getPose(),
                                        GlobalPose.BLUE.BASIC_POSE_NEAR.scorePosePath))
                                .setConstantHeadingInterpolation(Math.toRadians(225))
                                .build(), true);
                        Robot.setPathState(10);
                    }
                } else {
                    time.reset();
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(11);
                }
                break;
            //third time
            case 11:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.setMaxPower(1);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.pushLever))
                            .setLinearHeadingInterpolation(Math.toRadians(225), Math.toRadians(160))
                            .build(), true);
                    Robot.setPathState(12);
                }
                break;
            case 12:
                if (!robot.follower.isBusy()) {
                    robot.follower.setMaxPower(1);
                    if (time.seconds() > 1) {
                        robot.follower.followPath(robot.follower.pathBuilder()
                                .addPath(new BezierLine(robot.follower.getPose(),
                                        GlobalPose.BLUE.BASIC_POSE_NEAR.scorePosePath))
                                .setConstantHeadingInterpolation(Math.toRadians(225))
                                .build(), true);
                        Robot.setPathState(13);
                    }
                } else {
                    time.reset();
                }
                break;
            case 13:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(14);
                }
                break;

            //Stage 3 (Path 1)
            case 14:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(36.67, 87.77),
                                    GlobalPose.BLUE.PICKUP_POSE.pickup1))
                                    .setConstantHeadingInterpolation(Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(15);
                }
                break;
            case 15:
                time.reset();
                if (!robot.follower.isBusy() || time.seconds() > .2) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.endPose))
                                    .setConstantHeadingInterpolation(Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(16);
                }
                break;
            case 16:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(17);
                }
                break;

            //End
            case 17:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    Robot.setPathState(-1);
                }
                break;
        }
    }




    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        PoseStorage.init();

        robot.setPose(GlobalPose.BLUE.BASIC_POSE_NEAR.startPose);
        robot.aimShoot(false, false);

        Robot.setPathState(0);
        time.reset();

        robot.turret.resetEncoder();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void init_loop() {
        robot.init_loop();
        telemetryM.debug("This program will run automatically at BLUE NEAR ZONE, " +
                "pick up artifact at PATH 2, RETAKE, PATH 1");
        telemetryM.update(telemetry);
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
