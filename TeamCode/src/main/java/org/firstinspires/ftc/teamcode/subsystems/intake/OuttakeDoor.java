package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;

public class OuttakeDoor {
    private ElapsedTime time = new ElapsedTime();
    private boolean running = false;
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

    public void teleOpControl(Gamepad gamepad1) {
        if (gamepad1.dpad_up && !running) {
            running = true;
            time.reset();
        }

        if (running) {
            double seconds = time.seconds();

            if (seconds < Math.abs(Constants.DOOR.delayTime - Constants.DOOR.openTime)) {
                block(true);
            } else if (seconds < Constants.DOOR.delayTime) {
                block(false);
            } else {
                block(true);
                running = false;
            }
        }
    }
}