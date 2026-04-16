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
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;

@Autonomous
public class BlueGoalPath2_1_3 extends OpMode {
    Robot robot = new Robot();
    IntakeRoller intakeRoller = new IntakeRoller();
    int reTakeTurn;
    int loopTime = 1;
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
                        .setConstantHeadingInterpolation(Math.toRadians(323))
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
            case 5:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.setMaxPower(.9);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.pushLever))
                            .setConstantHeadingInterpolation(Math.toRadians(142))
                            .build(), true);
                    Robot.setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.reTake1))
                            .setLinearHeadingInterpolation(Math.toRadians(142), Math.toRadians(109))
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.reTake2))
                            .setLinearHeadingInterpolation(Math.toRadians(109), Math.toRadians(107))
                            .build(), true);
                    Robot.setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.reTake3))
                            .setLinearHeadingInterpolation(Math.toRadians(107), Math.toRadians(99))
                            .build(), true);
                    time.reset();
                    Robot.setPathState(8);
                }
                break;
            case 8:
                if (!robot.follower.isBusy() || time.seconds() > .5) {
                    robot.follower.setMaxPower(1);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.scorePoseRetake))
                            .setConstantHeadingInterpolation(Math.toRadians(45))
                            .build(), true);
                    Robot.setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(10);
                }
                break;

            //Stage 3 (Path 3)
            case 10:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(57.76, 27.45),
                                    new Pose(55.54, 36.38),
                                    GlobalPose.BLUE.PICKUP_POSE.pickup3))
                            .setConstantHeadingInterpolation(Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(11);
                }
                break;
            case 11:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.scorePosePath))
                            .setConstantHeadingInterpolation(Math.toRadians(225))
                            .build(), true);
                    Robot.setPathState(12);
                }
                break;
            case 12:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(13);
                }
                break;
            case 13:
                if (!(time.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(36.19, 86.31),
                                    GlobalPose.BLUE.PICKUP_POSE.pickup1))
                            .setConstantHeadingInterpolation(Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(14);
                }
                break;


            //Stage 4 (Path 1)
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
                if (!robot.follower.isBusy()) {
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
        robot.setPose(GlobalPose.BLUE.BASIC_POSE_NEAR.startPose);
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
