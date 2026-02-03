package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;

public class IntakeRoller {
    private DcMotorEx intake;
    ElapsedTime timer = new ElapsedTime();
    boolean reset = false;

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

    public boolean stopIntake = false;

    public void teleOpControl(Gamepad gamepad1) {
        if (gamepad1.right_trigger > 0) {
            setPower(.95);
        } else if (gamepad1.right_bumper) {
            reset = true;
            timer.reset();
        } else if (gamepad1.b || gamepad1.circle) {
            stopIntake = true;
        } else {
            stopIntake = false;
        }

        if (stopIntake) {
            setPower(0);
        } else {
            setPower(.7);
        }

        if (reset && timer.milliseconds() < 50) {
            setPower(-.4);
        } else {
            stopIntake = false;
            reset = false;
        }
    }
}
