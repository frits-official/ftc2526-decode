package Subsystem.Shooter.Hood;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Test_Hood extends LinearOpMode {
    Hood hood = new Hood();

    @Override
    public void runOpMode() {
        hood.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            hood.update(gamepad1);
            hood.updateTelemetry(telemetry);
        }
    }
}
