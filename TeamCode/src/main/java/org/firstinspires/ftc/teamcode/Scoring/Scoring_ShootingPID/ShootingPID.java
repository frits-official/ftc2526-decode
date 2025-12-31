package org.firstinspires.ftc.teamcode.Scoring.Scoring_ShootingPID;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.control.feedforward.GravityFeedforwardParameters;

public class ShootingPID {
    Shooting shoot = new Shooting();
    private static double p = 0.01;
    private static double i = 0;
    private static double d = 0;
    private static double v = 0.005;
    private static double a = 0.001;
    private static double s = 0.1;
    private static double g = 0;
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(p, i, d);
    private BasicFeedforwardParameters basicffp = new BasicFeedforwardParameters(v, a, s);
    private GravityFeedforwardParameters gravityffp = new GravityFeedforwardParameters(g, v, a, s);

    public void init() {

        controlSystem = ControlSystem.builder()
                .velPid(coefficients)
                .velSquID(coefficients)
                .angular(AngleType.DEGREES, fb -> fb.velPid(coefficients))
                .basicFF(basicffp)
                .elevatorFF(gravityffp)
                .armFF(gravityffp)
                .build();
    }

    public void update(Shooting shoot, int i) {
        shoot.shoot1.setPower(controlSystem.calculate(new KineticState(0, shoot.shoot1.getVelocity())));
        shoot.shoot2.setPower(controlSystem.calculate(new KineticState(0, shoot.shoot2.getVelocity())));
    }
}