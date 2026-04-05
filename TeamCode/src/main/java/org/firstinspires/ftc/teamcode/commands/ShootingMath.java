package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class ShootingMath {
    public static double getTickFromVel(double velocity){
        // this will use an interpLUT to get the tick value instead of this crap
        return MathFunctions.clamp(TheoreticalCalculatorConstant.slope * velocity - TheoreticalCalculatorConstant.static_friction + TheoreticalCalculatorConstant.flywheelOffset,
                TheoreticalCalculatorConstant.flywheelMinSpeed, TheoreticalCalculatorConstant.flywheelMaxSpeed);
    }

    public static double getVelFromTick(double tick) {
        // will use inverse interpLUT to get the horizontal velocity
        return tick;
    }

    public static Vector getStaticGoalVector(Pose pose) {
        double xOff;
        if (Robot.alliance == Constants.ALLIANCE.BLUE) xOff = 6;
        else xOff = 138;
        return new Vector(Math.sqrt(Math.pow(xOff - pose.getX(), 2) + Math.pow(138 - pose.getY(), 2)) * 2.54,
                            Math.atan2(138 - pose.getY(), xOff - pose.getX()));
    }
}
