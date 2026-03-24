package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;


import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

public class ShooterAim {
    public static class ShooterState {
        private double velocity, angle, heading;

        public ShooterState(double velocity, double angle, double heading) {
            this.velocity = velocity;
            this.angle = angle;
            this.heading = heading;
        }

        public double getVelocity() { return velocity; }
        public double getAngle() { return angle; }
        public double getHeading() { return heading; }
    }

    public static Vector getGoalVec(Pose pose) {
        double xOff;
        if (Robot.alliance == Constants.ALLIANCE.BLUE) xOff = 6;
        else xOff = 138;
        return new Vector(Math.sqrt(Math.pow(xOff - pose.getX(), 2) + Math.pow(138 - pose.getY(), 2)) * 2.54,
                            Math.atan2(pose.getY(), pose.getX()));
    }

    public static ShooterState calcShoot(Pose pose) {
        double distance = getGoalVec(pose).getMagnitude();
        return new ShooterState(Robot.velocityLUT.get(distance),
                                Robot.angleLUT.get(distance),
                                getTurretHeadingFromOdometry(pose));
    }
    /** this will return degree with -90deg -> 90deg will run in counterclockwise
     * use this for nearly accurate turning, we will use limelight for more accuracy **/
    public static double getTurretHeadingFromOdometry(Pose pose) {
        Vector bot = new Vector(1, pose.getHeading());
        Vector perpBot = new Vector(1, pose.getHeading() + Math.PI / 2);
        Vector goal = getGoalVec(pose);
        return Math.toDegrees(Math.acos(goal.dot(bot) / (bot.getMagnitude() * goal.getMagnitude()))
                                * Math.signum(goal.dot(perpBot)));
    }
}
