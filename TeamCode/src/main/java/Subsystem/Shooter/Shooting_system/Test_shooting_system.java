package Subsystem.Shooter.Shooting_system;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import Subsystem.Shooter.Hood.Hood;
import Subsystem.Shooter.Motion_system.Outtake_door;
import Subsystem.Shooter.Motion_system.Turret.Turret;

@TeleOp
@Configurable
public class Test_shooting_system extends LinearOpMode {
    Outtake_door outtakeDoor = new Outtake_door();
    Intake_Funnel intakeFunnel = new Intake_Funnel();
    Turret turret = new Turret();
    Hood hood = new Hood();
    Shooting shooting = new Shooting();

    public static double l, v;

    public void runOpMode() {
        outtakeDoor.init(hardwareMap);

        intakeFunnel.init(hardwareMap);

        turret.init(hardwareMap);

        hood.init(hardwareMap);

        shooting.init(hardwareMap);

        turret.setTarget(0);

        waitForStart();

        while (opModeIsActive()) {
            turret.setTarget(0);
            shooting.setTarget(v);
            hood.setTarget(hood.calcAngle(l, shooting.getVelocity()));

            outtakeDoor.update(gamepad1);
            intakeFunnel.update(gamepad1);
            shooting.update();
            hood.update();
            turret.update();

            telemetry.addData("shooting velocity:", shooting.shoot1.getVelocity());
            telemetry.addData("Position turret:", turret.getDegree(turret.turning.getCurrentPosition()));
            telemetry.addData("Power turret: ", turret.turning.getPower());
            telemetry.addData("turret target", turret.controlSystem.getGoal().getPosition());
            telemetry.addData("l: ", l);
            telemetry.addData("vol hood:", hood.x);
            telemetry.addData("angel hood:", hood.y);
            telemetry.addData("power hood:", hood.power);
            telemetry.update();
        }
    }
}
