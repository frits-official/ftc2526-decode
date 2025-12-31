package org.firstinspires.ftc.teamcode.Scoring;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class Navigation_system extends LinearOpMode {
    private CRServo navigation = null;
    private AnalogInput potentiometer = null;
    private TelemetryManager telemetryM = null;

    public void runOpMode() {
        navigation = hardwareMap.get(CRServo.class, "navigation");
        potentiometer = hardwareMap.get(AnalogInput.class, "potentiometer");

        navigation.setDirection(CRServo.Direction.REVERSE);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        waitForStart();

        while (opModeIsActive()) {
            double power = 0.5;
            double x = potentiometer.getVoltage();
            double y = x * 81.8 + 25;

            if (gamepad1.left_trigger > 0.1) {
                navigation.setDirection(CRServo.Direction.REVERSE);
                navigation.setPower(power);
            } else if (gamepad1.right_trigger > 0.1) {
                navigation.setDirection(DcMotorSimple.Direction.FORWARD);
                navigation.setPower(power);
            } else {
                navigation.setPower(0);
            }

            telemetryM.debug("vol:" + x);
            telemetryM.debug("góc:" + y);
            telemetryM.update(telemetry);
        }
    }
}