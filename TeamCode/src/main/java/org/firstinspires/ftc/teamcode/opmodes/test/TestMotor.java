package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TestMotor extends LinearOpMode {
    private DcMotorEx motor;

    @Override
    public void runOpMode() {
        motor = hardwareMap.get(DcMotorEx.class, "rr");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            motor.setPower(1);
            telemetry.addData("Velocity(tick/s): ", motor.getVelocity());
            telemetry.update();
        }
    }
}
