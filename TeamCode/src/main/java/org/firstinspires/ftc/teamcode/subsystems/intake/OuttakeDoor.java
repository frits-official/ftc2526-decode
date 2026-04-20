package org.firstinspires.ftc.teamcode.subsystems.intake;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


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
            setPosition(Constants.DOOR.block);
        } else {
            setPosition(Constants.DOOR.unblock);
        }
    }

    double lastPos = 0;
    public void setPosition(double position) {
        if (Math.abs(position - lastPos) > 0.05) {
            door.setPosition(position);
            lastPos = position;
        }
    }


    public boolean isBlocked() {
        return door.getPosition() == Constants.DOOR.block;
    }
}