package org.firstinspires.ftc.teamcode.command;

import static org.firstinspires.ftc.teamcode.Constants.SHOOTER_CALCULATION.*;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.Constants;

public class ShooterAim {
    public static class ShooterState {
        private double velocity, angle;

        public ShooterState(double velocity, double angle) {
            this.velocity = velocity;
            this.angle = angle;
        }

        public double getVelocity() { return velocity; }
        public double getAngle() { return angle; }
    }

    public static double calcDistanceFromTag(double cm, Pose pose, Constants.ALLIANCE alliance) {
        double xOff = 0.0;
        if (alliance == Constants.ALLIANCE.BLUE) xOff = 304.8;
        else xOff = 3352.8;
        double distance = cm;
        if (cm == 0.0)
            distance = Math.sqrt(Math.pow(xOff - pose.getX(), 2) + Math.pow(3352.8 - pose.getY(), 2)) / 10.0;
        return distance;
    }
    public static ShooterState calcShoot(double cm, Pose pose, Constants.ALLIANCE alliance) {
        int i = lowerBound(calcDistanceFromTag(cm, pose, alliance));
        return new ShooterState(targetVelocity[i], targetAngle[i]);
    }
    /** this will return degree with -90deg -> 90deg will run in counterclockwise
     * use this for nearly accurate turning, we will use limelight for more accuracy **/
    public static double calcTurretHeadingFromOdometry(Pose pose, Constants.ALLIANCE alliance) {
        double xOff;
        if (alliance == Constants.ALLIANCE.BLUE) xOff = 304.8;
        else xOff = 3352.8;
        double a1 = -Math.atan2(xOff - pose.getX(), 3352.8 - pose.getY());
        double a2 = Math.PI / 2 - pose.getHeading();
        return (a1 + a2) * 180 / Math.PI;
    }

    static int lowerBound(double key) {
        int low = 0, high = distanceThresh.length;
        int mid;

        while (low < high) {
            mid = low + (high - low) / 2;
            if (key <= distanceThresh[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        if (low < distanceThresh.length && distanceThresh[low] < key) {
            low++;
        }

        return low;
    }
}
