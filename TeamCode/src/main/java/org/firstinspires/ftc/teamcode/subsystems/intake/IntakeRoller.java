package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class IntakeRoller {
    private DcMotorEx intake;
    boolean reset = false;

    public void init (HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void setPower(double power) {
        intake.setPower(power * Robot.getVolFeedfoward());
    }

    public double getVelocity() {
        return intake.getVelocity();
    }

    public double getPower() {
        return intake.getPower();
    }

    public boolean stopIntake = false;

    public void teleOpControl(Gamepad gamepad1) {
        if (gamepad1.right_bumper) {
            setPower(-.4);
        } else if (gamepad1.b || gamepad1.circle) {
            setPower(0);
        } else if (gamepad1.left_bumper) {
            setPower(Constants.INTAKE.shooting);
        } else {
            setPower(Constants.INTAKE.normal);
        }
    }
}
