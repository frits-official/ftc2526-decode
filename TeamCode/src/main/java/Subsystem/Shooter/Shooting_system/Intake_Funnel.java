package Subsystem.Shooter.Shooting_system;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake_Funnel {
    private DcMotorEx intake = null;
    private TelemetryManager telemetryM = null;

    public void init (HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setDirection(DcMotorEx.Direction.FORWARD);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    public void update (Gamepad gamepad1, Telemetry telemetry) {
        int turn = 0;

        if (gamepad1.b) {
            if (turn == 0) {
                intake.setDirection(DcMotorEx.Direction.FORWARD);
                intake.setPower(0.5);
                turn = 1;
            } else if (turn == 1) {
                intake.setDirection(DcMotorEx.Direction.REVERSE);
                intake.setPower(0.5);
                turn = 0;
            }
        } else if (gamepad1.x) {
            intake.setPower(0);
            turn = 0;
        }

        telemetryM.debug("intake velocity:" + intake.getVelocity());
        telemetryM.update(telemetry);
    }
}
