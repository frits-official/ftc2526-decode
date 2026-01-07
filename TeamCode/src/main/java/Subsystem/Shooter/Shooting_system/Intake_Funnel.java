package Subsystem.Shooter.Shooting_system;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake_Funnel {
    public DcMotorEx intake = null;

    public void init (HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        intake.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void update (Gamepad gamepad1) {

        if (gamepad1.b) {
            intake.setPower(0.45);
        } else if (gamepad1.x) {
            intake.setPower(0);
        } else if (gamepad1.y) {
            intake.setPower(0.7);
        }
    }
}
