package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto.FarZone;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@Autonomous
public class BlueFarZoneLeverPath3_2_1Path2Ball12NoIndulge extends LinearOpMode {
    Robot robot = new Robot();
    private int pathState;
    private ElapsedTime time = new ElapsedTime();
    public void autonomousPathUpdate() {
        switch (pathState) {
            //Start
            case 0:
                robot.intakeAuto(false);
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.BLUE.BlueFarZonePose.startPose, GlobalPose.BLUE.BlueFarZonePose.scorePose))
                        .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
                        .build(), true);
                setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(2);
                }
                break;

            //Path1
            case 2:
                if (!robot.running) {
                    robot.intakeAuto(true);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(GlobalPose.BLUE.BlueFarZonePose.startPose, GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup3_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(111))
                            .build(), true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(5);
                }
                break;

            //Path2
            case 5:
                if (!robot.running) {
                    robot.intakeAuto(true);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup2_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup2_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.pushLever))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))
                            .build(), true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.BlueFarZonePose.scorePose))
                            .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(111))
                            .build(), true);
                    setPathState(7);
                }
                break;
            case 7:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(8);
                }
                break;

            //Path3
            case 8:
                if (!robot.running) {
                    robot.intakeAuto(true);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup1_1))
                            .setLinearHeadingInterpolation(Math.toRadians(111), Math.toRadians(180))
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.PICKUP_POSE_BLUE.pickup1_2))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                            .build(), true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!robot.follower.isBusy()) {
                    robot.intakeAuto(false);
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(robot.follower.getPose(), GlobalPose.BLUE.scorePoseEnd))
                            .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
                            .build(), true);
                    setPathState(10);
                }
                break;
            case 10:
                if (!robot.follower.isBusy()) {
                    robot.unBlockAndShoot();
                    setPathState(11);
                }
                break;

            //End
            case 11:
                if (!robot.follower.isBusy()) {
                    setPathState(-1);
                }
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
    }

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BLUE.BlueFarZonePose.startPose);
        robot.aimShoot(false, false);

        setPathState(0);

        robot.turret.resetEncoder();

        while (!isStarted()) {
            robot.init_loop();
        }

        if (opModeIsActive()) {
            double seconds = time.seconds();

            while ((time.seconds() - seconds) < 2) {
                robot.update();
                robot.aimShoot(true, false);
            }
            while (opModeIsActive()) {
                robot.update();
                robot.aimShoot(true, true);
                autonomousPathUpdate();

                robot.updateTelemetry(true, true, true, true);
            }
        }
    }
}
