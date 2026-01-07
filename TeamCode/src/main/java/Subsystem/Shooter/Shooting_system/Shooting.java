package Subsystem.Shooter.Shooting_system;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Shooting {
    public DcMotorEx shoot1 = null;
    public DcMotorEx shoot2 = null;

    public void init(HardwareMap hardwareMap) {
        shoot1 = hardwareMap.get(DcMotorEx.class, "shoot1");
        shoot2 = hardwareMap.get(DcMotorEx.class, "shoot2");

        shoot1.setDirection(DcMotorEx.Direction.REVERSE);
        shoot2.setDirection(DcMotorEx.Direction.FORWARD);
    }
}