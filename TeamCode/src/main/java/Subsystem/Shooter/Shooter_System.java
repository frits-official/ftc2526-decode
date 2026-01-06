package Subsystem.Shooter;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Subsystem.Shooter.Motion_system.Outtake_door;
import Subsystem.Shooter.Shooting_system.Shooting;
import Subsystem.Shooter.Shooting_system.ShootingPID;

@TeleOp
public class Shooter_System extends LinearOpMode {
    Shooting shooting = new Shooting();
    ShootingPID shootingPID = new ShootingPID();
    Outtake_door outtakeDoor = new Outtake_door();

    public void runOpMode() {
        shooting.init(hardwareMap);
        shootingPID.init();
        outtakeDoor.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            //Shooting
            if (gamepad1.a) {
                shootingPID.update(shooting, 2000);
            } else if (gamepad1.x) {
                shooting.shoot1.setPower(0);
                shooting.shoot2.setPower(0);
            }

            //Outtake_door
            if (gamepad1.dpad_up) {
                outtakeDoor.door.setPosition(0.4);
            } else if (gamepad1.dpad_down) {
                outtakeDoor.door.setPosition(0);
            }


        }
    }
}
