package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;
import org.opencv.core.Mat;

public class Hood {
    private double target = 25.0;
    public Servo hood = null;

    public void init(HardwareMap hardwareMap) {

        hood = hardwareMap.get(Servo.class, "hood");
        hood.setPosition(calcAngle(Constants.HOOD.minAngle));

        hood.setDirection(Servo.Direction.FORWARD);

        setTargetAngle(25.0);
        update();
    }

    public double calcAngle(double targetHood) {
        double hoodAngle = targetHood - Constants.HOOD.hoodOffset;

        double servoAngle = hoodAngle * Constants.HOOD.gearRatio;

        double position = servoAngle / Constants.HOOD.servoRange;

        return Math.max(0.0, Math.min(1.0, position));
    }

    public void update() {
        double targetPos = calcAngle(target);
        hood.setPosition(targetPos);
    }

    public void setTargetAngle(double angle) {
        this.target = Math.max(Constants.HOOD.minAngle, Math.min(Constants.HOOD.maxAngle, angle));
    }

    public double getTargetAngle() {
        return target;
    }

    public double getCurrentAngle() {
        return (hood.getPosition() * Constants.HOOD.servoRange / Constants.HOOD.gearRatio) + Constants.HOOD.hoodOffset;
    }
}