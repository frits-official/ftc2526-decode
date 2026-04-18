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

@Autonomous
public class BlueGoalPath2_1 extends OpMode {
    Robot robot = new Robot();
    TelemetryManager telemetryM;
    int reTakeTurn;
    int loopTime = 3;
    ElapsedTime timeShoot = new ElapsedTime();
    ElapsedTime timeCase = new ElapsedTime();
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
                    timeShoot.reset();
                    robot.shoot();
                    Robot.setPathState(2);
                }
                break;

            //Stage 1 (Path 2)
            case 2:
                if (!(timeShoot.seconds() < .7)) {
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
                    timeShoot.reset();
                    robot.shoot();
                    Robot.setPathState(5);
                }
                break;

            //Stage 2 (Retake)
            case 5:
                if (!(timeShoot.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.setMaxPower(1);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.RETAKE_POSE.pushLever))
                                    .setConstantHeadingInterpolation(Math.toRadians(148))
                            .build(), true);
                    Robot.setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    robot.follower.setMaxPower(1);
                    if (timeCase.seconds() > .25) {
                        robot.follower.followPath(robot.follower.pathBuilder()
                                .addPath(new BezierLine(robot.follower.getPose(),
                                        GlobalPose.BLUE.BASIC_POSE_NEAR.scorePosePath))
                                .setConstantHeadingInterpolation(Math.toRadians(225))
                                .build(), true);
                        Robot.setPathState(8);
                    }
                } else {
                    timeCase.reset();
                }
                break;
            case 8:
                if (!robot.follower.isBusy()) {
                    timeShoot.reset();
                    robot.shoot();
                    reTakeTurn += 1;

                    if (reTakeTurn < loopTime) {
                        Robot.setPathState(5);
                    } else {
                        Robot.setPathState(9);
                    }
                }
                break;

            //Stage 3 (Path 1)
            case 9:
                if (!(timeShoot.seconds() < .6)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(36.67, 87.77),
                                    GlobalPose.BLUE.PICKUP_POSE.pickup1))
                                    .setConstantHeadingInterpolation(Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(10);
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.endPose))
                                    .setConstantHeadingInterpolation(Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(11);
                }
                break;
            case 11:
                if (!robot.follower.isBusy()) {
                    timeShoot.reset();
                    robot.shoot();
                    Robot.setPathState(12);
                }
                break;

            //End
            case 12:
                if (!(timeShoot.seconds() < .6)) {
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
        timeShoot.reset();

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
