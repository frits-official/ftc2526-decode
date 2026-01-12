package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class Hood {
    public double power = 0;
    private double target = 0;
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(Constants.HOOD.p, Constants.HOOD.i, Constants.HOOD.d);
    public CRServo hood = null;
    public AnalogInput potentiometer = null;

    public void init(HardwareMap hardwareMap) {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();

        hood = hardwareMap.get(CRServo.class, "hood");
        potentiometer = hardwareMap.get(AnalogInput.class, "potentiometer");

        hood.setDirection(CRServo.Direction.FORWARD);
    }

    public double limitAngle(double a) {
        if (a != -1) return Math.max(Constants.HOOD.minAngle, Math.min(Constants.HOOD.maxAngle, a));
        else return -1;
    }

    public double calcAngle(double l, double vel) {
        if (vel != 0 && l != 0) {
            double b = (-9.8 * Math.pow(l, 2)) / (2 * Math.pow(vel, 2));
            double d = Math.pow(l, 2) - 4  * (b * (b - 0.7));
            double a = -1;
            if (d >= 0) {
                if (l <= Constants.HOOD.changeFormulaDistanceThreshold) a = Math.atan((-l - Math.sqrt(d)) / (2 * b));
                else a = Math.atan((-l + Math.sqrt(d)) / (2 * b));
            }
            a *= 180 / Math.PI;
            return a;
        } else return -1;
    }

    public void setTargetAngle(double a) {
       if (a != -1) this.target = limitAngle(a);
       controlSystem.setGoal(new KineticState(target, 0));
   }

    public double getAngle() {
        return potentiometer.getVoltage() * 81.8 + 25;
    }

    public void update() {
        power = controlSystem.calculate(new KineticState(getAngle()));
        hood.setPower(power);
    }

    public double getPower() {
        return hood.getPower();
    }

    public double getTargetAngle() {
        return controlSystem.getGoal().getPosition();
    }
}