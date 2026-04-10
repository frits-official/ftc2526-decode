package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class ShootingMath {
    /**
     * @param velocity cm/s
     * @return tick/s
     */
    public static double getTickFromVel(double velocity){
        // this will use an interpLUT to get the tick value instead of this crap
        return MathFunctions.clamp(TheoreticalCalculatorConstant.slope * velocity + TheoreticalCalculatorConstant.flywheelOffset,
                TheoreticalCalculatorConstant.flywheelMinSpeed, TheoreticalCalculatorConstant.flywheelMaxSpeed);
    }

    /**
     * @param tick tick/s
     * @return cm/s
     */
    public static double getVelFromTick(double tick) {
        // will use inverse interpLUT to get the horizontal velocity
        return tick;
    }

    /**
     * @param pose
     * @return Vector
     */
    public static Vector getStaticGoalVector(Pose pose) {
        double xOff;
        if (Robot.alliance == Constants.ALLIANCE.BLUE) xOff = 6;
        else xOff = 138;
        return new Vector(Math.sqrt(Math.pow(xOff - pose.getX(), 2) + Math.pow(138 - pose.getY(), 2)) * 2.54,
                            Math.atan2(138 - pose.getY(), xOff - pose.getX()));
    }

    /** this will return degree with -180deg -> 180deg will run in counterclockwise
     * use this for nearly accurate turning, we will use limelight for more accuracy
     * @param pose
     * @param goal
     * @return degree
     **/
    public static double getTurretHeadingFromOdometry(Pose pose, Vector goal) {
        double angle = goal.getTheta() - pose.getHeading();
        return Math.toDegrees(Math.IEEEremainder(angle, 2 * Math.PI));
    }
}
