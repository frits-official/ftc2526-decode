package Subsystem.Shooter.Motion_system.Turret;

import Subsystem.ConstantFTC;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class TurretPID {
    private Turret turret = new Turret();
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(ConstantFTC.TURRET.p, ConstantFTC.TURRET.i, ConstantFTC.TURRET.d);

    public void init() {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();
    }

    public boolean calculate(boolean a) {
        return a;
    }

    public double x, y, target = 0;
    public boolean change = false;

    public void update(double x, double y) {
        this.x = x;
        this.y = y;

        if (change) {
            if (calculate(target > ConstantFTC.TURRET.TURRETMAX)) {
                controlSystem.setGoal(new KineticState(ConstantFTC.TURRET.TURRETMAX));
            } else if (calculate(target < ConstantFTC.TURRET.TURRETMIN)) {
                controlSystem.setGoal(new KineticState(ConstantFTC.TURRET.TURRETMIN));
            } else {
                controlSystem.setGoal(new KineticState(target));
            }
        }

        turret.turning.setPower(controlSystem.calculate(new KineticState(turret.turning.getCurrentPosition())));
    }
}
