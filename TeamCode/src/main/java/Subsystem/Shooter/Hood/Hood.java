package Subsystem.Shooter.Hood;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Subsystem.ConstantFTC;
import Subsystem.Shooter.Motion_system.Turret.Turret;
import Subsystem.Shooter.Shooting_system.Shooting;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class Hood {
    public double power = 0;
    private double target = 0;
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(ConstantFTC.HOOD.p, ConstantFTC.HOOD.i, ConstantFTC.HOOD.d);
    public CRServo hood = null;
    public AnalogInput potentiometer = null;
    private TelemetryManager telemetryM = null;
    public double x = 0;
    public double y = 0;

    public void init(HardwareMap hardwareMap) {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();

        hood = hardwareMap.get(CRServo.class, "hood");
        potentiometer = hardwareMap.get(AnalogInput.class, "potentiometer");

        hood.setDirection(CRServo.Direction.FORWARD);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    public double limitAngle(double a) {
        if (a != -1) return Math.max(25.1, Math.min(45.5, a));
        else return -1;
    }

    public double calcAngle(double l, double vel) {
        if (vel != 0 && l != 0) {
            double b = (-9.8 * Math.pow(l, 2)) / (2 * Math.pow(vel, 2));
            double d = Math.pow(l, 2) - 4  * (b * (b - 0.7));
            double a = -1;
            if (d >= 0) {
                if (l <= ConstantFTC.HOOD.threshold) a = Math.atan((-l - Math.sqrt(d)) / (2 * b));
                else a = Math.atan((-l + Math.sqrt(d)) / (2 * b));
            }
            a *= 180 / Math.PI;
            return a;
        } else return -1;
    }

   public void setTarget(double a) {
       if (a != -1) this.target = limitAngle(a);
       controlSystem.setGoal(new KineticState(target, 0));
   }

    public void update() {
        x = potentiometer.getVoltage();
        y = x * 81.8 + 25;

        power = controlSystem.calculate(new KineticState(y));

        hood.setPower(power);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetryM.debug("vol:" + x);
        telemetryM.debug("angel:" + y);
        telemetryM.debug("power:" + power);
        telemetryM.update(telemetry);
    }
}