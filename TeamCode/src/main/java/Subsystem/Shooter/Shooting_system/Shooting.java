package Subsystem.Shooter.Shooting_system;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Subsystem.ConstantFTC;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;


public class Shooting {
    public double target = 0;
    private ControlSystem controlSystem;
    public DcMotorEx shoot1 = null;
    public DcMotorEx shoot2 = null;

    public void init(HardwareMap hardwareMap) {
        shoot1 = hardwareMap.get(DcMotorEx.class, "shoot1");
        shoot2 = hardwareMap.get(DcMotorEx.class, "shoot2");

        shoot1.setDirection(DcMotorEx.Direction.REVERSE);
        shoot2.setDirection(DcMotorEx.Direction.FORWARD);

        shoot1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shoot1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shoot2.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        PIDCoefficients coefficients = new PIDCoefficients(ConstantFTC.SHOOTER.p, ConstantFTC.SHOOTER.i, ConstantFTC.SHOOTER.d);
        controlSystem = ControlSystem.builder()
                .velPid(coefficients)
                .build();
    }

    public void setTarget(double velocity) {
        this.target = velocity;
        controlSystem.setGoal(new KineticState(0, target));
    }

    public void update() {
        double vel = controlSystem.calculate(new KineticState(0, shoot1.getVelocity()));
        vel = Math.max(-1.0, Math.min(1.0, vel));
        shoot1.setPower(vel);
        shoot2.setPower(vel);

        if (target <= 0) {
            shoot1.setPower(0);
            shoot2.setPower(0);
            return;
        }
    }

    public double getVelocity() { //m/s
        double tick = shoot1.getVelocity();
        double rpm = tick / 28;
        return rpm * 0.09 * Math.PI;
    }
}