package org.firstinspires.ftc.teamcode.opmodes.auto.RedAuto;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.BezierLine;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.skeletonarmy.marrow.TimerEx;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.GlobalPose;

@Autonomous
public class RedFarHuman extends OpMode {
    Robot robot = new Robot();
    private int pathState;
    TimerEx timer = new TimerEx(2);
    int reTakeTurn;
    int loopTime = 5;
    TelemetryManager telemetryM;

    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            //Start
            case 0:
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.RED.BASIC_POSE_FAR.startPose,
                                GlobalPose.RED.BASIC_POSE_FAR.scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                        .build());
                Robot.setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    robot.shoot();
                    Robot.setPathState(2);
                }
                break;

            //Score
            case 2:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.PICKUP_POSE.pickupHuman))
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
                    robot.shoot();
                    reTakeTurn += 1;

                    if (reTakeTurn < loopTime) {
                        Robot.setPathState(2);
                    } else {
                        Robot.setPathState(5);
                    }
                }
                break;

            //End
            case 5:
                if (!robot.isShooting) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(),
                                    GlobalPose.RED.BASIC_POSE_FAR.endPose))
                            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                            .build(), true);
                    Robot.setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    Robot.setPathState(-1);
                }
        }
    }


    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.RED);
        robot.setPose(GlobalPose.RED.BASIC_POSE_FAR.startPose);
        robot.aimShoot(false, false);

        Robot.setPathState(0);
        reTakeTurn = 0;

        robot.turret.resetEncoder();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    @Override
    public void init_loop() {
        robot.init_loop();
        telemetryM.debug("This program will run automatically at RED FAR ZONE " +
                "and only pick up artifact at HUMAN ZONE");
        telemetryM.update(telemetry);
    }

    @Override
    public void start() {
        timer.start();

        while (!timer.isDone()) {
            robot.update();
            robot.aimShoot(false, false);
        }
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
