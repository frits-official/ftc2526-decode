package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;

public class IntakeRoller {
    private DcMotorEx intake;
    boolean running = false;
    private ElapsedTime time = new ElapsedTime();

    public void init (HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void setPower(double power) {
        intake.setPower(power);
    }

    public double getVelocity() {
        return intake.getVelocity();
    }

    public double getPower() {
        return intake.getPower();
    }

    public void setIntakeState(boolean isIntake) {
        if (isIntake) setPower(.7);
        else setPower(0);
    }

    public boolean isIntake() {
        return getPower() == .7;
    }

    public void teleOpControl(Gamepad gamepad1) {
        if (gamepad1.b) {
            setIntakeState(true);
        } else if (gamepad1.x) {
            setIntakeState(false);
        }

        if (gamepad1.dpad_up && !running) {
            running = true;
            time.reset();
        }

        if (running) {
            double seconds = time.seconds();

            if (seconds < Constants.DOOR.delayTime) {
                setPower(1);
            } else {
                setPower(.7);
                running = false;
            }
        }
    }
}
