package Subsystem.Shooter.Shooting_system;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;

public class ShootingPID {
    private static double p = 0.01;
    private static double i = 0;
    private static double d = 0;
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(p, i, d);
    public void init() {

        controlSystem = ControlSystem.builder()
                .velPid(coefficients)
                .velSquID(coefficients)
                .angular(AngleType.DEGREES, fb -> fb.velPid(coefficients))
                .build();
    }

    public double target = 0;

    public void update(Shooting shoot, int i) {
        controlSystem.setGoal(new KineticState(0, target));

        double vel = controlSystem.calculate(new KineticState(0, shoot.shoot1.getVelocity()));

        shoot.shoot1.setPower(vel);
        shoot.shoot2.setPower(vel);
    }
}