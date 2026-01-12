package OpMode;

import static Subsystem.Drivetrain.pedroPathing.Constants.createFollower;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class Auto extends OpMode {
    private Follower follower = null;
    private TelemetryManager telemetryM = null;
    private final Pose startPose = new Pose(0, 0, 0);
    private final Pose scorePose = new Pose(75.81, 80.96, Math.toRadians(45));
    private final Pose pickup1Pose = new Pose(75.81, -72.72, 0);

    @Override
    public void init() {
        follower = createFollower(hardwareMap);
        follower.setPose(startPose);


        PathChain pathChain = follower.pathBuilder()
                .addPath(new BezierLine(startPose, pickup1Pose))
                .setLinearHeadingInterpolation(startPose.getHeading(), pickup1Pose.getHeading())
                .addPath(new BezierLine(pickup1Pose, scorePose))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        follower.followPath(pathChain);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    public void loop() {

        follower.update();
        telemetryM.debug("X:" + follower.getPose().getX());
        telemetryM.debug("Y:" + follower.getPose().getY());
        telemetryM.debug("Heading:" + follower.getPose().getHeading());
        telemetryM.debug("Total Heading:" + follower.getTotalHeading());
    }
}