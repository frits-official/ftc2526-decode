package org.firstinspires.ftc.teamcode.subsystems.shooter;

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
    double power;

    public void init(HardwareMap hardwareMap) {
        PIDCoefficients coefficients = new PIDCoefficients(Constants.TURRET.p, Constants.TURRET.i, Constants.TURRET.d);
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();

        turret = hardwareMap.get(DcMotorEx.class, "turning");
        turretEncoder = hardwareMap.get(DcMotorEx.class, "lr");

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
                .posSquID(coefficients)
                .build();
    }

    public double getDegreeFromTick(int tick) {
        return tick * (360.0 / 575.0);
    }
    public long getTickFromDegree(double degree) {
        return Math.round(degree * (575.0 / 360.0));
    }

    public void setTarget(double target) {
        // convert heading from calculator (-180 -> 180 range, counterclockwise)
        // to turret (0 -> -360 range, counterclockwise)
        if (target >= 0) target = -target;
        else target = -360 - target;

        // convert to tick it's more accurate
        controlSystem.setGoal(new KineticState(MathUtils.clamp(getTickFromDegree(target),
                                                                getTickFromDegree(Constants.TURRET.minAngle),
                                                                getTickFromDegree(Constants.TURRET.maxAngle))));
    }

    public void update() {
        if (getCurrentPosition() > getTickFromDegree(Constants.TURRET.maxAngle)) {
            setPower(-.7);
        } else if (getCurrentPosition() < getTickFromDegree(Constants.TURRET.minAngle)) {
            setPower(.7);
        } else {
            power = controlSystem.calculate(new KineticState(getDegreeFromTick(getCurrentPosition()))) + Math.signum(getTarget() - getCurrentPosition()) * Constants.TURRET.f;
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

    public int getCurrentPosition() {
        return turretEncoder.getCurrentPosition();
    }

    public double getTarget() {
        return controlSystem.getGoal().getPosition();
    }

    public double getPower() {
        return turret.getPower();
    }
}
