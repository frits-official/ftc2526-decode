package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.Vector;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;


public class Shooter {
    private ControlSystem controlSystem;
    public DcMotorEx shoot1, shoot2, shootEncoder;
    public double power = 0.0;

    public void init(HardwareMap hardwareMap) {
        shoot1 = hardwareMap.get(DcMotorEx.class, "shoot1");
        shoot2 = hardwareMap.get(DcMotorEx.class, "shoot2");
        shootEncoder = hardwareMap.get(DcMotorEx.class, "turning");

        shoot1.setDirection(DcMotorEx.Direction.FORWARD);
        shoot2.setDirection(DcMotorEx.Direction.REVERSE);
        shootEncoder.setDirection(DcMotorEx.Direction.REVERSE);

        shootEncoder.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shootEncoder.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        PIDCoefficients coefficients = new PIDCoefficients(Constants.SHOOTER.p, Constants.SHOOTER.i, Constants.SHOOTER.d);
        controlSystem = ControlSystem.builder()
                .basicFF(Constants.SHOOTER.v, 0, Constants.SHOOTER.s)
                .velPid(coefficients)
                .build();
    }

    public void setTarget(double velocity) {
        controlSystem.setGoal(new KineticState(0, velocity));
    }

    public double getTarget() {
        return controlSystem.getGoal().getVelocity();
    }

    public void update() {
        power = controlSystem.calculate(new KineticState(0, getVelocity()));
        power = Math.max(-.2, Math.min(1.0, power));
        setPower(power);

        if (getTarget() <= 0) {
            setPower(0);
        }
    }

    public void setCoefficients() {
        PIDCoefficients coefficients = new PIDCoefficients(Constants.SHOOTER.p, Constants.SHOOTER.i, Constants.SHOOTER.d);
        controlSystem = ControlSystem.builder()
                .basicFF(Constants.SHOOTER.v, 0, Constants.SHOOTER.s)
                .velPid(coefficients)
                .build();
    }

    double lastPower = 0;
    public void setPower(double power) {
        if (Math.abs(power - lastPower) > 0.06) {
            shoot1.setPower(power * Robot.getVolFeedforward());
            shoot2.setPower(power * Robot.getVolFeedforward());
            lastPower = power;
        }
    }

    public double getVelocity() { //tick
        return -shootEncoder.getVelocity();
    }
}