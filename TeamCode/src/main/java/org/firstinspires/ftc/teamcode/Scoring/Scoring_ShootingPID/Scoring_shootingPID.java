package org.firstinspires.ftc.teamcode.Scoring.Scoring_ShootingPID;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.control.feedforward.GravityFeedforwardParameters;

@TeleOp
public class Scoring_shootingPID extends LinearOpMode {
    private static double p = 0.01;
    private static double i = 0;
    private static double d = 0;
    private static double  v = 0.005;
    private static double a = 0.001;
    private static double s = 0.1;
    private static double g = 0;
    private ControlSystem controlSystem = null;
    private PIDCoefficients coefficients = new PIDCoefficients(p, i, d);
    private BasicFeedforwardParameters basicffp = new BasicFeedforwardParameters(v, a, s);
    private GravityFeedforwardParameters gravityffp = new GravityFeedforwardParameters(g, v, a, s);
    private DcMotorEx motor = null;

    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "motor");

        controlSystem = ControlSystem.builder()
                .velPid(coefficients)
                .feedback(new FullPowerFeedbackElement())
                .velSquID(coefficients)
                .angular(AngleType.DEGREES, fb -> fb.velPid(coefficients))
                .feedforward(new FullPowerFeedforwardElement())
                .basicFF(basicffp)
                .elevatorFF(gravityffp)
                .armFF(gravityffp)
                .build();


        waitForStart();

        while (opModeIsActive()) {
            motor.setPower(controlSystem.calculate(new KineticState(0, motor.getVelocity())));
        }
    }
}