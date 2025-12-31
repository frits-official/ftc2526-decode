package org.firstinspires.ftc.teamcode.Scoring;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Outtake_door extends LinearOpMode {
    private Servo door = null;

    public void runOpMode() {
        door = hardwareMap.get(Servo.class, "door");

        door.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        while (opModeIsActive()) {
            double open = 0.5;
            double close = 0.26;

            if (gamepad1.right_bumper) {
                door.setPosition(open);
            } else if (gamepad1.left_bumper) {
                door.setPosition(close);
            }
        }
    }
}