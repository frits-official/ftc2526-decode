package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;


public class Shooter {
    private ControlSystem controlSystem;
    public DcMotorEx shoot1 = null;
    public DcMotorEx shoot2 = null;

    public void init(HardwareMap hardwareMap) {
        shoot1 = hardwareMap.get(DcMotorEx.class, "shoot1");
        shoot2 = hardwareMap.get(DcMotorEx.class, "shoot2");

        shoot1.setDirection(DcMotorEx.Direction.REVERSE);
        shoot2.setDirection(DcMotorEx.Direction.FORWARD);

        shoot1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shoot1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shoot2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

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
        double vel = controlSystem.calculate(new KineticState(0, shoot1.getVelocity()));
        vel = Math.max(-1.0, Math.min(1.0, vel));
        shoot1.setPower(vel);
        shoot2.setPower(vel);

        if (getTarget() <= 0) {
            shoot1.setPower(0);
            shoot2.setPower(0);
        }
    }

    public double getVelocity() { //tick
        return shoot1.getVelocity();
    }
}