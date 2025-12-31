package org.firstinspires.ftc.teamcode.Scoring;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Scoring.Scoring_ShootingPID.Shooting;
import org.firstinspires.ftc.teamcode.Scoring.Scoring_ShootingPID.ShootingPID;

@TeleOp
public class Scoring_System extends LinearOpMode {
    Shooting shooting = new Shooting();
    ShootingPID shootingPID = new ShootingPID();

    public void runOpMode() {
        shooting.init(hardwareMap);
        shootingPID.init();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                shootingPID.update(shooting, 2000);
            } else if (gamepad1.b) {
                shooting.intake.setPower(1.0);
            } else if (gamepad1.x) {
                shooting.shoot1.setPower(0);
                shooting.shoot2.setPower(0);
                shooting.intake.setPower(0);
            }
        }
    }
}
