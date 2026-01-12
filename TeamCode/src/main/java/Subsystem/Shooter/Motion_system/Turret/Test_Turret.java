package Subsystem.Shooter.Motion_system.Turret;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test_Turret extends LinearOpMode {
    private Turret turret = new Turret();

    public void runOpMode() {
        turret.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                turret.setTarget(0);
            } else if (gamepad1.y) {
                turret.setTarget(180);
            }

            turret.update();
            turret.updateTelemetry(telemetry);
        }
    }
}
