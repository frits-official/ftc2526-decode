package Subsystem.Shooter.Motion_system.Turret;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Subsystem.ConstantFTC;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class Turret {
    public ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(ConstantFTC.TURRET.p, ConstantFTC.TURRET.i, ConstantFTC.TURRET.d);
    public DcMotorEx turning = null;

    public void init(HardwareMap hardwareMap) {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();

        turning = hardwareMap.get(DcMotorEx.class, "turning");

        turning.setDirection(DcMotorEx.Direction.FORWARD);
        turning.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turning.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public double getDegree(double pos) {
        return pos * (360.0 / 425.0);
    }

    public void setTarget(double target1) {
        double finalTarget = target1;
        if (target1 > ConstantFTC.TURRET.TURRETMAX) {
            finalTarget = ConstantFTC.TURRET.TURRETMAX;
        }
        if (target1 < ConstantFTC.TURRET.TURRETMIN) {
            finalTarget = ConstantFTC.TURRET.TURRETMIN;
        }

        controlSystem.setGoal(new KineticState(getDegree(finalTarget)));
    }

    public void update() {
        double power = controlSystem.calculate(new KineticState(getDegree(turning.getCurrentPosition())));

        turning.setPower(power);
    }
}
