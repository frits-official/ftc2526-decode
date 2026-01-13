package org.firstinspires.ftc.teamcode.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Hood;

@TeleOp(group = "test")
public class Test_Hood extends LinearOpMode {
    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        robot.init(this);

        robot.hood.setTargetAngle(Constants.HOOD.minAngle);

        waitForStart();

        while (opModeIsActive()) {
            robot.update();
            robot.updateTelemetry(false, true, false);
        }
    }
}
