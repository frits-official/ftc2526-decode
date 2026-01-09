package Subsystem.Shooter.Motion_system.Turret;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class Control_turret extends LinearOpMode {
    Turret turret = new Turret();

    public void runOpMode() {
        turret.init(hardwareMap);

        turret.turning.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.turning.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            double power = gamepad1.right_stick_x;

            double limitedPower = Range.clip(power, -0.5, 0.5);

            turret.turning.setPower(limitedPower);

            telemetry.addData("Position: ", turret.turning.getCurrentPosition());
            telemetry.addData("Power: ", turret.turning.getPower());
            telemetry.update();
        }
    }
}
