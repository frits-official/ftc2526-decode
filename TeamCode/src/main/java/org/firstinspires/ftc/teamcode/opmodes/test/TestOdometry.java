package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.GlobalPose;


@TeleOp
public class TestOdometry extends OpMode {
    Robot robot = new Robot();
    TelemetryManager telemetryM;
    int Turn;
    int loopTime = 3;
    public void pathUpdate() {
        switch (Robot.pathState) {
            case 0:
                robot.follower.followPath(robot.follower.pathBuilder()
                        .addPath(new BezierLine(GlobalPose.TEST_POSE.startPose,
                                GlobalPose.TEST_POSE.endPose))
                        .setConstantHeadingInterpolation(Math.toRadians(180))
                        .build());
                Robot.setPathState(1);
                break;
            case 1:
                if (!robot.follower.isBusy()) {
                    robot.follower.followPath(robot.follower.pathBuilder()
                            .addPath(new BezierLine(GlobalPose.TEST_POSE.endPose,
                                    GlobalPose.TEST_POSE.startPose))
                            .setConstantHeadingInterpolation(180)
                            .build(), true);
                    Robot.setPathState(0);
                }
        }
    }




    @Override
    public void init() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BLUE.BASIC_POSE_NEAR.startPose);
        robot.aimShoot(false, false);

        Robot.setPathState(0);

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
        pathUpdate();

        robot.updateTelemetry(true, true, true, true, true);
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
