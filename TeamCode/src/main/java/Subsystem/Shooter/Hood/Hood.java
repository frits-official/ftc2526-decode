package Subsystem.Shooter.Hood;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import Subsystem.ConstantFTC;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;

public class Hood {
    public double target = 0;
    private ControlSystem controlSystem;
    private PIDCoefficients coefficients = new PIDCoefficients(ConstantFTC.HOOD.p, ConstantFTC.HOOD.i, ConstantFTC.HOOD.d);
    public CRServo hood = null;
    public AnalogInput potentiometer = null;
    private TelemetryManager telemetryM = null;
    private double x = 0;
    private double y = 0;

    public void init(HardwareMap hardwareMap) {
        controlSystem = ControlSystem.builder()
                .posPid(coefficients)
                .build();

        hood = hardwareMap.get(CRServo.class, "hood");
        potentiometer = hardwareMap.get(AnalogInput.class, "potentiometer");

        hood.setDirection(CRServo.Direction.REVERSE);

        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
    }

    public double calculate (double a) {
        double res = (a - 25) / 81.1;

        if (a < 25.1) {
            res = 0;
        }
        if (a > 46) {
            res = (46 - 25) / 81.1;
        }

        return res;
    }

    public void update(Gamepad gamepad1) {
        x = potentiometer.getVoltage();
        y = x * 81.8 + 25;

        if (gamepad1.left_trigger > 0.1) {
            hood.setDirection(CRServo.Direction.REVERSE);
            hood.setPower(0.5);
        } else if (gamepad1.right_trigger > 0.1) {
            hood.setDirection(DcMotorSimple.Direction.FORWARD);
            hood.setPower(0.5);
        } else {
            hood.setPower(0);
        }
    }

    public void update() {
        controlSystem.setGoal(new KineticState(target,0));

        hood.setPower(controlSystem.calculate(new KineticState(potentiometer.getVoltage())));
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetryM.debug("vol:" + x);
        telemetryM.debug("góc:" + y);
        telemetryM.update(telemetry);
    }
}