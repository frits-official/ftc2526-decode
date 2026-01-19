package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

@Configurable
@TeleOp(group = "test")
public class TestTurret extends LinearOpMode {
    Robot robot = new Robot();
    public static double target = 135;

    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                robot.turret.setCoefficients(Constants.TURRET.p, Constants.TURRET.i, Constants.TURRET.d,
                        Constants.TURRET.v, Constants.TURRET.a, Constants.TURRET.s);
                robot.turret.setTarget(target);
            }
            if (gamepad1.a) {
                robot.turret.setTarget(0);
            } else if (gamepad1.y) {
                robot.turret.setTarget(target);
            }

            robot.update();
            robot.updateTelemetry(false, true, false, false);
        }
    }
}
