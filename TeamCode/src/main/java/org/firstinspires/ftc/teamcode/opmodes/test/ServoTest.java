package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "test")
public class ServoTest extends LinearOpMode {
    Servo servo;

    @Override
    public void runOpMode() {
        servo = hardwareMap.get(Servo.class, "hood");
        servo.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            servo.setPosition(0);
        }
    }
}
