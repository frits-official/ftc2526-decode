package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.hardware.limelightvision.LLResult;
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
    public DcMotorEx turret, turretEncoder;
    LLResult result;

    public void init(HardwareMap hardwareMap) {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();

        turret = hardwareMap.get(DcMotorEx.class, "turning");
        turretEncoder = hardwareMap.get(DcMotorEx.class, "lf");

        turret.setDirection(DcMotorEx.Direction.FORWARD);

        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void resetEncoder() {
        turretEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setCoefficients(double p, double i, double d) {
        controlSystem = ControlSystem.builder()
                .posPid(new PIDCoefficients(p, i, d))
                .build();
    }

    public double getDegree(double pos) {
        return pos * (360.0 / 768.0);
    }

    public void setTarget(double target1) {
        double finalTarget = target1;
        if (target1 > Constants.TURRET.maxAngle) {
            finalTarget = target1 - 360.0;
        }
        if (target1 < Constants.TURRET.minAngle) {
            finalTarget = target1 + 360.0;
        }

        controlSystem.setGoal(new KineticState(0));
    }

    public void setCamResult(LLResult result) {
        this.result = result;
    }

    public void update() {
        if (getDegree(getCurrentPosition()) > Constants.TURRET.maxAngle) {
            turret.setPower(-.3);
        } else  if (getDegree(getCurrentPosition()) < Constants.TURRET.minAngle) {
            turret.setPower(.3);
        } else {
            double power = controlSystem.calculate(new KineticState(getDegree(getCurrentPosition())));
            if (result != null && result.isValid()) {
                power = -result.getTy() * (Math.abs(result.getTy()) > Constants.TURRET.threshold ? Constants.TURRET.pCU : Constants.TURRET.pCD);
                turret.setPower(power + (Constants.TURRET.fC * Math.signum(power)));
            } else if (!controlSystem.isWithinTolerance(new KineticState(Constants.TURRET.tolerance))) {
                turret.setPower(power + Constants.TURRET.f * Math.signum(power));
            } else {
                turret.setPower(0);
            }
        }
    }

    public double getCurrentPosition() {
        return turretEncoder.getCurrentPosition();
    }

    public double getTarget() {
        return controlSystem.getGoal().getPosition();
    }

    public double getPower() {
        return turret.getPower();
    }
}
