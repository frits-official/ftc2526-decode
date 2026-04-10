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

@Autonomous
public class BlueGoal extends OpMode {
    Robot robot = new Robot();
    int reTakeTurn;
    int loopTime = 2;
    TelemetryManager telemetryM;
    ElapsedTime time = new ElapsedTime();
    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            //Start
            case 0:
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.BLUE.BASIC_POSE_NEAR.startPose,
                                GlobalPose.BLUE.BASIC_POSE_NEAR.scorePose1))
                        .setLinearHeadingInterpolation(Math.toRadians(322), Math.toRadians(180))
                        .build());
                Robot.setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    if (time.seconds() > .85){
                        time.reset();
                        robot.shoot();
                        Robot.setPathState(2);
                    }
                } else {
                    time.reset();
                }
                break;

            //Stage 1 (Path 2)
            case 2:
                if (!(time.seconds() < 1.5)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(51.55, 49.58),
                                    new Pose(49.84, 61.14),
                                    GlobalPose.BLUE.PICKUP_POSE.pickup2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.scorePose2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(4);
                }
                break;

            //Stage 2 (Retake)
            case 4:
                if (!(time.seconds() < 1.5)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(36.55, 67),
                                    GlobalPose.BLUE.pushLever))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.reTake))
                                    .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(130))
                            .addPath(new BezierCurve(robot.follower.getPose(),
                                    new Pose(36.32, 67.57),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.scorePose2))
                            .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(5);
                }
                break;
            case 5:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    if (reTakeTurn < loopTime) {
                        reTakeTurn += 1;
                        Robot.setPathState(4);
                    } else {
                        Robot.setPathState(6);
                    }
                }
                break;

            //Stage 3 (Path 1)
            case 6:
                if (!(time.seconds() < 1.5)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.PICKUP_POSE.pickup1))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.scorePose2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    Robot.setPathState(8);
                }
                break;

            //End
            case 8:
                if (!(time.seconds() < 1.5)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.BLUE.BASIC_POSE_NEAR.endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    Robot.setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
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
