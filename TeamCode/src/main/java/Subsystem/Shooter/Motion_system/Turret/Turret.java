package Subsystem.Shooter.Motion_system.Turret;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Turret {
    public DcMotorEx turning = null;

    public void init (HardwareMap hardwareMap) {
        turning = hardwareMap.get(DcMotorEx.class, "turning");

        turning.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void update (Gamepad gamepad1) {
        if (gamepad1.right_bumper) {
            turning.setDirection(DcMotorEx.Direction.FORWARD);
            turning.setPower(0.5);
        } else if (gamepad1.left_bumper) {
            turning.setPower(0);
        }
    }
}
