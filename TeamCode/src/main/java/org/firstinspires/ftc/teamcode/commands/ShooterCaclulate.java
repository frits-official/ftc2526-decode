package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.math.MathFunctions;

import org.firstinspires.ftc.teamcode.Constants;

import java.util.Vector;

public class ShooterCaclulate {
    private static double slope, static_friction  = 0;
    public static double getTickFromVel (double velocity){
        return MathFunctions.clamp(slope * velocity - static_friction + Constants.SHOOTER.flywheelOffset, Constants.SHOOTER.flywheelMinSpeed, Constants.SHOOTER.flywheelMaxSpeed);
    }
    public static double caclShotVectorAndTurret (double heading) {
        double g = 9.8 * 39.37;
        return g;
    }
}
