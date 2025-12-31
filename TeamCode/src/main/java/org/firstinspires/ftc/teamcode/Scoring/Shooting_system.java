package org.firstinspires.ftc.teamcode.Scoring;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class Shooting_system extends LinearOpMode {
    private DcMotorEx shoot1 = null;
    private DcMotorEx shoot2 = null;
    private DcMotorEx intake = null;
    private TelemetryManager telemetryM = null;

    public void runOpMode() {
        shoot1 = hardwareMap.get(DcMotorEx.class, "shoot1");
        shoot2 = hardwareMap.get(DcMotorEx.class, "shoot2");
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        shoot1.setDirection(DcMotorEx.Direction.REVERSE);
        shoot2.setDirection(DcMotorEx.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        waitForStart();

        while(opModeIsActive()) {
            double start_intake = 1;
            double start_shoot = 0.8;
            double stop = 0;

            if (gamepad1.a) {
                shoot1.setPower(start_shoot);
                shoot2.setPower(start_shoot);
            } else if (gamepad1.b) {
                intake.setPower(start_intake);
            } else if (gamepad1.x) {
                shoot1.setPower(stop);
                shoot2.setPower(stop);
                intake.setPower(stop);
            }

            telemetryM.debug("velocity shoot:" + shoot1.getVelocity());
            telemetryM.debug("Velocity intake:" + intake.getVelocity());
            telemetryM.update(telemetry);
        }
    }
}