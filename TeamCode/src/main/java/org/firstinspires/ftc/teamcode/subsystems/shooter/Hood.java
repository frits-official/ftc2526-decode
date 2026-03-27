package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.teamcode.Constants;
import org.opencv.core.Mat;

public class Hood {
    private double position = 0;
    private double targetPos = 0;
    private double target = Constants.HOOD.minAngle;
    public Servo hood;

    public void init(HardwareMap hardwareMap) {
        hood = hardwareMap.get(Servo.class, "hood");
        hood.setDirection(Servo.Direction.FORWARD);
        hood.setPosition(calcAngle(Constants.HOOD.minAngle));

    }

    public double calcAngle(double targetHood) {
        position = (targetHood - Constants.HOOD.hoodOffset) * Constants.HOOD.gearRatio / Constants.HOOD.servoRange;
        return MathUtils.clamp(position, 0, 1);
    }

    public void update() {
        targetPos = calcAngle(target);
        hood.setPosition(targetPos);
    }

    public void setTargetAngle(double angle) {
        this.target = MathUtils.clamp(angle, Constants.HOOD.minAngle, Constants.HOOD.maxAngle);
    }

    public double getTargetAngle() {
        return target;
    }

    public double getCurrentAngle() {
        return (hood.getPosition() * Constants.HOOD.servoRange / Constants.HOOD.gearRatio) + Constants.HOOD.hoodOffset;
    }
}