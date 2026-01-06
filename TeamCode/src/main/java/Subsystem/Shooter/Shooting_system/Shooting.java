package Subsystem.Shooter.Shooting_system;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooting {
    public DcMotorEx shoot1 = null;
    public DcMotorEx shoot2 = null;
    private TelemetryManager telemetryM = null;

    public void init(HardwareMap hardwareMap) {
        shoot1 = hardwareMap.get(DcMotorEx.class, "shoot1");
        shoot2 = hardwareMap.get(DcMotorEx.class, "shoot2");

        shoot1.setDirection(DcMotorEx.Direction.REVERSE);
        shoot2.setDirection(DcMotorEx.Direction.FORWARD);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }


        public void update(Gamepad gamepad1, Telemetry telemetry){
            double start_shoot = 0.8;
            double stop = 0;

            if (gamepad1.a) {
                shoot1.setPower(start_shoot);
                shoot2.setPower(start_shoot);
            } else if (gamepad1.x) {
                shoot1.setPower(stop);
                shoot2.setPower(stop);
            }

            telemetryM.debug("velocity shoot:" + shoot1.getVelocity());
            telemetryM.update(telemetry);
        }
}