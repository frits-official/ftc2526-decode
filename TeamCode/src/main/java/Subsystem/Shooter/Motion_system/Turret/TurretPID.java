package Subsystem.Shooter.Motion_system.Turret;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class TurretPID {
    private Turret turret = new Turret();
    private static double p = 0.01;
    private static double i = 0;
    private static double d = 0;
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(p, i, d);

    public void init() {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();
    }

    public double calculate(double a) {
        return a;
    }

    public double x, y, target = 0;
    public boolean change = false;

    public void update(double x, double y) {
        this.x = x;
        this.y = y;

        if (change) {
            //if (calculate(target > TURRET.MAX)) {
            //controlSystem.setGoal(new KineticState(TURRET.MAX));
            // } else if (calculate(target < TURRET.MIN)) {
            //controlSystem.setGoal(new KineticState(TURRET.MIN));
        } else {
            controlSystem.setGoal(new KineticState(target));
        }
    }

    //turret.turning.setPower(controlSystem.calculate(new KineticState(turret.turning.getCurrentPosition())));
}
