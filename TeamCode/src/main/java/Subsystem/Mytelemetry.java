package Subsystem;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Subsystem.Shooter.Shooting_system.Intake_Funnel;
import Subsystem.Shooter.Shooting_system.Shooting;
public class Mytelemetry {
    private TelemetryManager telemetryM;

    public void init () {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    public void updateTelemetry(Shooting shooting, Intake_Funnel intakeFunnel, Follower follower, Telemetry telemetry) {
        //shooting
        telemetryM.debug("shoot velocity:" + shooting.getVelocity());

        //intake
        telemetryM.debug("intake velocity:" + intakeFunnel.intake.getVelocity());

        //drivetrain
        telemetryM.debug("X:" + follower.getPose().getX());
        telemetryM.debug("Y:" + follower.getPose().getY());
        telemetryM.debug("Heading:" + follower.getPose().getHeading());

        //setup
        telemetryM.update(telemetry);
    }
}
