package Subsystem.Shooter.Motion_system;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import Subsystem.Shooter.Shooting_system.Intake_Funnel;

public class Outtake_door {
    private ElapsedTime time = new ElapsedTime();
    private boolean running = false;
    Intake_Funnel intakeFunnel = new Intake_Funnel();
    public Servo door = null;

    public void init(HardwareMap hardwareMap) {
        door = hardwareMap.get(Servo.class, "door");

        door.setDirection(Servo.Direction.FORWARD);

        door.setPosition(0.3);

        intakeFunnel.init(hardwareMap);
    }

    public void update(Gamepad gamepad1) {
        if (gamepad1.dpad_up && !running) {
            running = true;
            time.reset();
        }

        if (running) {
            double sencond = time.seconds();

            if (sencond < 0.3) {
                intakeFunnel.intake.setPower(0.7);
                door.setPosition(0.3);
            } else if (sencond < 2.5) {
                intakeFunnel.intake.setPower(0.7);
                door.setPosition(0);
            } else {
                door.setPosition(0.3);
                intakeFunnel.intake.setPower(0.3);
                running = false;
            }
        }
    }
}