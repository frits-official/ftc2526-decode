package org.firstinspires.ftc.teamcode.opmodes.test;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeRoller;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Hood;
import org.firstinspires.ftc.teamcode.subsystems.intake.OuttakeDoor;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Turret;

@TeleOp(group = "test")
@Configurable
public class Test_shooting_system extends LinearOpMode {
    Robot robot = new Robot();

    public static double vel, angle;

    public void runOpMode() {
        robot.init(this);

        waitForStart();

        while (opModeIsActive()) {
            robot.setShooterTargetManual(vel, angle, 0);

            robot.intakeFunnelTeleOpControl();

            robot.update();

            robot.updateTelemetry(false, true, false);
        }
    }
}
