package OpMode;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import Subsystem.Drivetrain.pedroPathing.Constants;
import Subsystem.Mytelemetry;
import Subsystem.Shooter.Hood.Hood;
import Subsystem.Shooter.Motion_system.Outtake_door;
import Subsystem.Shooter.Motion_system.Turret.Turret;
import Subsystem.Shooter.Shooting_system.Intake_Funnel;
import Subsystem.Shooter.Shooting_system.Shooting;

@TeleOp
public class TeleOpControl extends LinearOpMode {
    //Telemetry
    Mytelemetry mytelemetry = new Mytelemetry();

    //Drivetrain
    public Follower follower;

    //Shooting System
    Intake_Funnel intakeFunnel = new Intake_Funnel();
    Shooting shooting = new Shooting();
    Outtake_door outtakeDoor = new Outtake_door();
    Hood hood = new Hood();
    Turret turret = new Turret();

    @Override
    public void runOpMode() {
        //Telemetry
        mytelemetry.init();

        //Drivetrain
        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(0,0,0));
        follower.update();

        //Shooting System
        shooting.init(hardwareMap);
        outtakeDoor.init(hardwareMap);
        intakeFunnel.init(hardwareMap);
        hood.init(hardwareMap);
        turret.init(hardwareMap);

        waitForStart();

        //Drivetrain
        if (opModeIsActive()) {
            hood.setTarget(45);
            turret.setTarget(0);

            while(opModeIsActive()) {
                follower.update();
                if (gamepad1.a) {
                    shooting.setTarget(1300);
                } else if (gamepad1.x) {
                    shooting.setTarget(0);
                }

                if (gamepad1.start) {
                    Pose p = follower.getPose();
                    follower.setPose(new Pose(p.getX(),p.getY(),0));
                }

                //Drivetrain
                follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);

                //Shooting System
                shooting.update();
                outtakeDoor.update(gamepad1);
                intakeFunnel.update(gamepad1);
                hood.update();
                turret.update();

                //Telemetry
                mytelemetry.updateTelemetry(shooting, intakeFunnel, follower, telemetry);
            }
        }
    }
}
