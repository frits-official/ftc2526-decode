package org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.skeletonarmy.marrow.TimerEx;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.GlobalPose;
import org.firstinspires.ftc.teamcode.subsystems.PoseStorage;

@Autonomous
public class RedFarPath3Human extends OpMode {
    Robot robot = new Robot();
    TimerEx timer = new TimerEx(2);
    int reTakeTurn;
    int loopTime = 5;
    TelemetryManager telemetryM;
    ElapsedTime time = new ElapsedTime();
    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            case 0:
                if (time.seconds() > 1) {
                        time.reset();
                        robot.shoot();
                        Robot.setPathState(2);
                }
                break;

            //Stage 1
            case 2:
                if (!(time.seconds() < 1)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierCurve(GlobalPose.RED.BASIC_POSE_FAR.startPose,
                                    new Pose(85.43, 46.37),
                                    new Pose(104.07, 32.81),
                                    GlobalPose.RED.PICKUP_POSE.pickup3))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.BASIC_POSE_FAR.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
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


            //Stage 2 (Human)
            case 5:
                if (!(time.seconds() < 1)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.PICKUP_POSE.pickupHuman))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(6);
                    time.reset();
                }
                break;
            case 6:
                if (time.seconds() > 1.5) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.BASIC_POSE_FAR.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    time.reset();
                    robot.shoot();
                    reTakeTurn += 1;

                    if (reTakeTurn < loopTime) {
                        Robot.setPathState(5);
                    } else {
                        Robot.setPathState(8);
                    }
                }
                break;

            //End
            case 8:
                if (!(time.seconds() < 1)) {
                    robot.stopShoot();
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.BASIC_POSE_FAR.endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
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
        robot.init(this, Constants.ALLIANCE.RED);
        PoseStorage.init();

        robot.setPose(GlobalPose.RED.BASIC_POSE_FAR.startPose);
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
        telemetryM.debug("This program will run automatically at RED FAR ZONE, " +
                "pick up artifact at PATH 3 and HUMAN ZONE");
        telemetryM.update(telemetry);
    }

    @Override
    public void start() {
        timer.start();

        while (!timer.isDone()) {
            robot.update();
            robot.aimShoot(true, true);
        }
        time.reset();
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
