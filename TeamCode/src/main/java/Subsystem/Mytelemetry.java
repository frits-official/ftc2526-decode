package Subsystem;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Subsystem.Shooter.Shooting_system.Intake_Funnel;
import Subsystem.Shooter.Shooting_system.Shooting;

public class Mytelemetry {
    private TelemetryManager telemetryM = null;

    public void init () {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    public void updateTelemetry(Shooting shooting, Intake_Funnel intakeFunnel, Telemetry telemetry) {
        telemetryM.debug("shoot velocity:" + shooting.shoot1.getVelocity());
        telemetryM.debug("intake velocity:" + intakeFunnel.intake.getVelocity());
        telemetryM.update(telemetry);
    }
}
