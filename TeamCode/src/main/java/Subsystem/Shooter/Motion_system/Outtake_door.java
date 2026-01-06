package Subsystem.Shooter.Motion_system;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Outtake_door {
    public Servo door = null;

    public void init(HardwareMap hardwareMap) {
        door = hardwareMap.get(Servo.class, "door");

        door.setDirection(Servo.Direction.FORWARD);
    }

    public void update(Gamepad gamepad1) {
        if (gamepad1.dpad_up) {
            door.setPosition(0.4);
        } else if (gamepad1.dpad_down) {
            door.setPosition(0);
        }
    }
}