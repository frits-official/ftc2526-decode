package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;

public class OuttakeDoor {
    public Servo door;

    public void init(HardwareMap hardwareMap) {
        door = hardwareMap.get(Servo.class, "door");

        door.setDirection(Servo.Direction.FORWARD);

        block(true);
    }

    public void block(boolean state) {
        if (state) {
            door.setPosition(Constants.DOOR.block);
        } else {
            door.setPosition(Constants.DOOR.unblock);
        }
    }

    public boolean isBlocked() {
        return door.getPosition() == Constants.DOOR.block;
    }
}