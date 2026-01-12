package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class Turret {
    public ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(Constants.TURRET.p, Constants.TURRET.i, Constants.TURRET.d);
    public DcMotorEx turret;

    public void init(HardwareMap hardwareMap) {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();

        turret = hardwareMap.get(DcMotorEx.class, "turning");

        turret.setDirection(DcMotorEx.Direction.FORWARD);

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double getDegree(double pos) {
        return pos * (360.0 / 425.0);
    }

    public void setTarget(double target1) {
        double finalTarget = target1;
        if (target1 > Constants.TURRET.maxAngle) {
            finalTarget = Constants.TURRET.maxAngle;
        }
        if (target1 < Constants.TURRET.minAngle) {
            finalTarget = Constants.TURRET.minAngle;
        }

        controlSystem.setGoal(new KineticState(finalTarget));
    }

    public void update() {
        double power = controlSystem.calculate(new KineticState(getDegree(getCurrentPosition())));

        turret.setPower(power);
    }

    public double getCurrentPosition() {
        return turret.getCurrentPosition();
    }

    public double getTarget() {
        return controlSystem.getGoal().getPosition();
    }

    public double getPower() {
        return turret.getPower();
    }
}
