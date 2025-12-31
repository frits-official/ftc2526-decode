package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Constants.createFollower;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class Drivetrain extends LinearOpMode {
    private DcMotor rf = null;
    private DcMotor rr = null;
    private DcMotor lf = null;
    private DcMotor lr = null;
    private Follower follower = null;
    private TelemetryManager telemetryM = null;

    public void runOpMode() {
        lf = hardwareMap.get(DcMotor.class, "lf");
        lr = hardwareMap.get(DcMotor.class, "lr");
        rf = hardwareMap.get(DcMotor.class, "rf");
        rr = hardwareMap.get(DcMotor.class, "rr");

        lf.setDirection(DcMotorSimple.Direction.FORWARD);
        lr.setDirection(DcMotorSimple.Direction.FORWARD);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rr.setDirection(DcMotorSimple.Direction.REVERSE);

        follower = createFollower(hardwareMap);
        follower.setPose(new Pose(0,0,0));

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        waitForStart();

        while(opModeIsActive()) {
            double move = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;

            double leftfront = move + turn + strafe;
            double leftback = move + turn - strafe;
            double rightfront = move - turn - strafe;
            double rightback = move - turn + strafe;

            double max = Math.max(1, Math.max(Math.max(Math.abs(leftfront), Math.abs(leftback)), Math.max(Math.abs(rightfront), Math.abs(rightback))));

            lf.setPower(leftfront / max);
            lr.setPower(leftback / max);
            rf.setPower(rightfront / max);
            rr.setPower(rightback / max);

            follower.update();

            telemetryM.debug("X: " + follower.getPose().getX());
            telemetryM.debug("Y: " + follower.getPose().getY());
            telemetryM.debug("Heading: " + follower.getPose().getHeading());
            telemetryM.update(telemetry);
        }
    }
}
