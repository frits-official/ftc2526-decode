package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

@TeleOp(group = "test")
public class TestTurret extends LinearOpMode {
    Robot robot = new Robot();

    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                robot.turret.setTarget(0);
            } else if (gamepad1.y) {
                robot.turret.setTarget(180);
            }

            robot.update();
            robot.updateTelemetry(false, true, false, false);
        }
    }
}
