package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.ShooterState;
import org.firstinspires.ftc.teamcode.commands.InterpLUTShooterCalculator;
import org.firstinspires.ftc.teamcode.commands.TheoreticalShooterCalculator;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveConstants;

import java.util.Collections;

@TeleOp(group = "test")
public class TestAimWithoutRobot extends OpMode {
    TelemetryManager telemetryM;
    enum MODE { INTERPLUT, THEORETICAL };
    MODE mode = MODE.INTERPLUT;
    Follower follower;

    @Override
    public void init() {
        follower = DriveConstants.createFollower(this.hardwareMap);

        Collections.sort(Constants.SHOOTER_CALCULATION.distanceThresh);
        Collections.sort(Constants.SHOOTER_CALCULATION.targetVelocity);
        Collections.sort(Constants.SHOOTER_CALCULATION.targetAngle);

        Robot.velocityLUT.createLUT();
        Robot.angleLUT.createLUT();

        Robot.alliance = Constants.ALLIANCE.BLUE;

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        telemetryM.debug("test aim by manually input the pose. left bumper for interplut. right bumper for theoretical calculation.");
        telemetryM.update();
    }

    @Override
    public void loop() {
        follower.update();

        telemetryM.debug("test aim by manually input the pose. left bumper for interplut. right bumper for theoretical calculation.");
        telemetryM.debug("current mode: " + mode.toString());
        telemetryM.addLine("");

        ShooterState shooterState;
        if (mode == MODE.INTERPLUT) shooterState = InterpLUTShooterCalculator.calcShoot(follower.getPose());
        else if (mode == MODE.THEORETICAL) shooterState = TheoreticalShooterCalculator.calcShoot(follower);
        else shooterState = new ShooterState(0, 0, 0);

        telemetryM.debug("current pose: " + follower.getPose().toString());
        telemetryM.debug("current velocity: " + follower.getVelocity().toString());
        telemetryM.addLine("");
        telemetryM.debug("distance: " + InterpLUTShooterCalculator.getGoalVec(follower.getPose()));
        telemetryM.addLine("");
        telemetryM.debug("velo: " + shooterState.getVelocity());
        telemetryM.debug("angle: " + shooterState.getAngle());
        telemetryM.debug("heading: " + shooterState.getHeading());

        telemetryM.update();
    }
}
