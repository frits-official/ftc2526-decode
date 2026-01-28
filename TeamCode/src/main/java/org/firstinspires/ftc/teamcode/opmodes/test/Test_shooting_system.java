package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(group = "test")
@Configurable
public class Test_shooting_system extends LinearOpMode {
    Robot robot = new Robot();

    public static double vel, angle, heading;

    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(new Pose(72, 72, 0));

        waitForStart();

        while (opModeIsActive()) {
            robot.setShooterTarget(vel, angle, heading);

            robot.intakeFunnelTeleOpControl();
            if (gamepad1.left_bumper && !robot.running) {
                //brakeDrive(true);
                robot.unBlockAndShoot();
            }

            if (gamepad1.cross) {
                robot.turret.setCoefficients(Constants.TURRET.p, Constants.TURRET.i, Constants.TURRET.d);
                robot.hood.setCoefficients(Constants.HOOD.p, Constants.HOOD.i, Constants.HOOD.d);
                robot.shooter.setCoefficients(Constants.SHOOTER.p, Constants.SHOOTER.i, Constants.SHOOTER.d);
            }

            robot.update();

            robot.updateTelemetry(true, true, false, false);
        }
    }
}
