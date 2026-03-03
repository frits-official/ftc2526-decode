package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@Autonomous
public class Auto_testBlue21Ball extends LinearOpMode {
    Robot robot = new Robot();
    private ElapsedTime time = new ElapsedTime();

    public void autonomousPathUpdate() {
        switch (Robot.pathState) {
            case 0:
                
        }
    }


    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BLUE.BlueNearZonePose.startPose);
        robot.aimShoot(false, false);

        Robot.setPathState(0);
        time.reset();

        robot.turret.resetEncoder();

        while (!isStarted()) {
            robot.init_loop();
        }

        if (opModeIsActive()) {
            double second = time.seconds();

            while ((time.seconds() - second) < 2) {
                robot.update();
                robot.aimShoot(true, false);
            }
            while (opModeIsActive()) {
                robot.update();
                robot.aimShoot(true, true);
                autonomousPathUpdate();

                robot.updateTelemetry(true, true, true, true, true);
            }
        }
    }
}
