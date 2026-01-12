package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

@TeleOp(group = "test")
public class TurretManualControl extends LinearOpMode {
    Robot robot = new Robot();

    public void runOpMode() {
        robot.init(this);

        waitForStart();

        while (opModeIsActive()) {
            double power = gamepad1.right_stick_x;

            double limitedPower = Range.clip(power, -0.5, 0.5);

            robot.turret.turret.setPower(limitedPower);

            robot.updateTelemetry(false, true,false);
        }
    }
}
