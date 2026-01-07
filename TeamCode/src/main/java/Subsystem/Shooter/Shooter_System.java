package Subsystem.Shooter;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Subsystem.Shooter.Motion_system.Outtake_door;
import Subsystem.Shooter.Shooting_system.Intake_Funnel;
import Subsystem.Shooter.Shooting_system.Shooting;
import Subsystem.Shooter.Shooting_system.ShootingPID;
import Subsystem.Mytelemetry;

@TeleOp
public class Shooter_System extends LinearOpMode {
    Mytelemetry mytelemetry = new Mytelemetry();
    Intake_Funnel intakeFunnel = new Intake_Funnel();
    Shooting shooting = new Shooting();
    ShootingPID shootingPID = new ShootingPID();
    Outtake_door outtakeDoor = new Outtake_door();

    @Override
    public void runOpMode() {
        mytelemetry.init();
        shooting.init(hardwareMap);
        shootingPID.init();
        outtakeDoor.init(hardwareMap);
        intakeFunnel.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            //Shooting
            if (gamepad1.a) {
                shootingPID.setTarget(1545);
            } else if (gamepad1.x) {
                shootingPID.setTarget(0);
            }
            shootingPID.update(shooting);

            //Outtake_door
            outtakeDoor.update(gamepad1);

            //intake
            intakeFunnel.update(gamepad1);

            //Telemetry
            mytelemetry.updateTelemetry(shooting, intakeFunnel, telemetry);
        }
    }
}
