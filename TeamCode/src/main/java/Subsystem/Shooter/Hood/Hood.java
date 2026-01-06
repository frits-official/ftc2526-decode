package Subsystem.Shooter.Hood;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Hood extends LinearOpMode {
    public CRServo hood = null;
    public AnalogInput potentiometer = null;
    private TelemetryManager telemetryM = null;

    public void runOpMode() {
        hood = hardwareMap.get(CRServo.class, "hood");
        potentiometer = hardwareMap.get(AnalogInput.class, "potentiometer");

        hood.setDirection(CRServo.Direction.REVERSE);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        waitForStart();

        while (opModeIsActive()) {
            double power = 0.5;
            double x = potentiometer.getVoltage();
            double y = x * 81.8 + 25;

            if (gamepad1.left_trigger > 0.1) {
                hood.setDirection(CRServo.Direction.REVERSE);
                hood.setPower(power);
            } else if (gamepad1.right_trigger > 0.1) {
                hood.setDirection(DcMotorSimple.Direction.FORWARD);
                hood.setPower(power);
            } else {
                hood.setPower(0);
            }

            telemetryM.debug("vol:" + x);
            telemetryM.debug("góc:" + y);
            telemetryM.update(telemetry);
        }
    }
}