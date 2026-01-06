package Subsystem.Shooter.Hood;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.control.feedforward.GravityFeedforwardParameters;

public class HoodPID {
    private static double p = 0.01;
    private static double i = 0;
    private static double d = 0;
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(p, i, d);

    public void init() {

        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .posSquID(coefficients)
                .angular(AngleType.DEGREES, fb -> fb.posPid(coefficients))
                .build();
    }

    public double calculate (double a) {
        double res = (a - 25) / 81.1;

        if (a < 25.1) {
            res = 0;
        }
        if (a > 46) {
            res = (46 - 25) / 81.1;
        }

        return res;
    }

    public double target = 0;

    public void update(Hood hood) {
        controlSystem.setGoal(new KineticState(target,0));

        hood.hood.setPower(controlSystem.calculate(new KineticState(hood.potentiometer.getVoltage())));
    }
}