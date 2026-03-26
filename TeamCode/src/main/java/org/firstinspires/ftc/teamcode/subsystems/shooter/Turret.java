package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.util.MathUtils;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class Turret {
    public ControlSystem controlSystem;
    public DcMotorEx turret, turretEncoder;

    public void init(HardwareMap hardwareMap) {
        PIDCoefficients coefficients = new PIDCoefficients(Constants.TURRET.p, Constants.TURRET.i, Constants.TURRET.d);
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .basicFF(0, 0, Constants.TURRET.f)
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

    public void setCoefficients() {
        PIDCoefficients coefficients = new PIDCoefficients(Constants.TURRET.p, Constants.TURRET.i, Constants.TURRET.d);
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .basicFF(0, 0, Constants.TURRET.f)
                .build();
    }

    public double getDegree(double pos) {
        return pos * (360.0 / 768.0);
    }

    public void setTarget(double target) {
        controlSystem.setGoal(new KineticState(MathUtils.clamp(target, Constants.TURRET.minAngle, Constants.TURRET.maxAngle)));
    }

    public void update() {
        if (getDegree(getCurrentPosition()) > Constants.TURRET.maxAngle) {
            setPower(-.3);
        } else  if (getDegree(getCurrentPosition()) < Constants.TURRET.minAngle) {
            setPower(.3);
        } else {
            double power = controlSystem.calculate(new KineticState(getDegree(getCurrentPosition())));
            if (!controlSystem.isWithinTolerance(new KineticState(Constants.TURRET.tolerance))) {
                setPower(power);
            } else {
                setPower(0);
            }
        }
    }

    public void setPower(double power) {
        turret.setPower(power * Robot.getVolFeedforward());
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
