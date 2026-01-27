package org.firstinspires.ftc.teamcode.opmodes.auto.BlueAuto;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.commands.GlobalPose;

@Autonomous(group = "test")
public class BlueFarZonePath3_4NoLeverNoIndulge extends LinearOpMode {
    Robot robot = new Robot();
    private int pathState;
    private Timer pathTimer, opmodeTimer;
    private ElapsedTime time = new ElapsedTime();

    public void autonomousPathUpdate() {
        switch (pathState) {

        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void runOpMode() {
        robot.init(this, Constants.ALLIANCE.BLUE);
        robot.setPose(GlobalPose.BlueNearZonePose.startPose);
        robot.aimShoot(false, false);

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        setPathState(0);

        waitForStart();

        if (opModeIsActive()) {
            double seconds = time.seconds();

            if (seconds < 2) {
                robot.update();
                robot.aimShoot(true, true);
            } else {
                while (opModeIsActive()) {
                    robot.update();
                    robot.aimShoot(true, true);
                    robot.intakeAuto(true);
                    autonomousPathUpdate();

                    robot.updateTelemetry(true, true, true, true);
                }
            }
        }
    }
}
