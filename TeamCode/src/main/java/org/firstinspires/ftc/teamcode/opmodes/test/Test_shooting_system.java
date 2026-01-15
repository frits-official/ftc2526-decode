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

    public static double vel, angle;

    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(new Pose(72, 72, 0));

        waitForStart();

        while (opModeIsActive()) {
            robot.setShooterTarget(vel, angle, 0);

            robot.intakeFunnelTeleOpControl();

            robot.update();

            robot.updateTelemetry(false, true, false, true);
        }
    }
}
