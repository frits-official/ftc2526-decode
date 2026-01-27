package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

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
        shootEncoder = hardwareMap.get(DcMotorEx.class, "rf");

        shoot1.setDirection(DcMotorEx.Direction.REVERSE);
        shoot2.setDirection(DcMotorEx.Direction.FORWARD);
        shootEncoder.setDirection(DcMotorEx.Direction.REVERSE);

        shootEncoder.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shootEncoder.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        PIDCoefficients coefficients = new PIDCoefficients(Constants.SHOOTER.p, Constants.SHOOTER.i, Constants.SHOOTER.d);
        controlSystem = ControlSystem.builder()
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
        shoot1.setPower(power);
        shoot2.setPower(power);

        if (getTarget() <= 0) {
            shoot1.setPower(0);
            shoot2.setPower(0);
        }
    }

    public double getVelocity() { //tick
        return shootEncoder.getVelocity();
    }
}