package Subsystem.Shooter.Shooting_system;

import Subsystem.ConstantFTC;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class ShootingPID {
    public double target = 0;
    private ControlSystem controlSystem;
    public void init() {
        PIDCoefficients coefficients = new PIDCoefficients(ConstantFTC.SHOOTER.p, ConstantFTC.SHOOTER.i, ConstantFTC.SHOOTER.d);
        controlSystem = ControlSystem.builder()
                .velPid(coefficients)
                .build();
    }

    public void setTarget(double velocity) {
        this.target = velocity;
        controlSystem.setGoal(new KineticState(0, target));
    }

    public void update(Shooting shoot) {
        if (target <= 0) {
            shoot.shoot1.setPower(0);
            shoot.shoot2.setPower(0);
            return;
        }

        double vel = controlSystem.calculate(new KineticState(0, shoot.shoot1.getVelocity()));

        vel = Math.max(-1.0, Math.min(1.0, vel));

        shoot.shoot1.setPower(vel);
        shoot.shoot2.setPower(vel);
    }
}