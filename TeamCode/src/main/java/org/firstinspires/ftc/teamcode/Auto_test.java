package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.createFollower;

import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class Auto_test extends OpMode {
    private Follower follower = null;
    private TelemetryManager telemetryM = null;

    @Override
    public void init() {
        follower = createFollower(hardwareMap);
        follower.setPose(new Pose(0,0,0));
    }

    public void loop() {
        

        follower.update();
        telemetryM.debug("X:" + follower.getPose().getX());
        telemetryM.debug("Y:" + follower.getPose().getY());
        telemetryM.debug("Heading:" + follower.getPose().getHeading());
        telemetryM.debug("Total Heading:" + follower.getTotalHeading());
    }
}