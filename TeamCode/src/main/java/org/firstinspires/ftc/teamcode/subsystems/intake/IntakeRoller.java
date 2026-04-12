package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class IntakeRoller {
    public DcMotorEx intake;
    public enum INTAKE_STATE { INTAKE, GOAL_SHOOTING, FAR_SHOOTING, STOP }
    public INTAKE_STATE currentState;

    public void init(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setDirection(DcMotorEx.Direction.FORWARD);
        setState(INTAKE_STATE.STOP);
    }

    public void setPower(double power) {
        intake.setPower(power * Robot.getVolFeedforward());
    }

    public double getVelocity() {
        return intake.getVelocity();
    }

    public double getPower() {
        return intake.getPower();
    }

    public void setState(INTAKE_STATE state) {
        currentState = state;
    }

    public INTAKE_STATE getCurrentState() { return currentState; }

    public void update() {
        switch (currentState) {
            case INTAKE:
                setPower(Constants.INTAKE.normal);
                break;
            case GOAL_SHOOTING:
                setPower(Constants.INTAKE.goalShooting);
                break;
            case FAR_SHOOTING:
                setPower(Constants.INTAKE.farShooting);
                break;
            case STOP:
                setPower(0);
                break;
        }
    }
}
