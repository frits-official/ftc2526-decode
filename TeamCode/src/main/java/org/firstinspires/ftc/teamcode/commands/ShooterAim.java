package org.firstinspires.ftc.teamcode.commands;

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
        double distance = cm;
        if (cm == 0.0)
            distance = calcDistanceFromTagOdometryCM(pose, alliance);
        return distance;
    }

    public static double calcDistanceFromTagOdometryCM(Pose pose, Constants.ALLIANCE alliance) {
        double xOff = 0.0;
        if (alliance == Constants.ALLIANCE.BLUE) xOff = 12;
        else xOff = 132;
        return Math.sqrt(Math.pow(xOff - pose.getX(), 2) + Math.pow(132 - pose.getY(), 2)) * 2.54;
    }

    public static ShooterState calcShoot(Pose pose, Constants.ALLIANCE alliance) {
        int i = lowerBound(calcDistanceFromTagOdometryCM(pose, alliance));
        if (i == -1) i = 0;
        return new ShooterState(targetVelocity[i], targetAngle[i]);
    }
    /** this will return degree with -90deg -> 90deg will run in counterclockwise
     * use this for nearly accurate turning, we will use limelight for more accuracy **/
    public static double calcTurretHeadingFromOdometry(Pose pose, Constants.ALLIANCE alliance) {
        double xOff;
        if (alliance == Constants.ALLIANCE.BLUE) xOff = 12;
        else xOff = 132;
        double a1 = -Math.atan2(xOff - pose.getX(), 132 - pose.getY());
        double a2 = Math.PI / 2 - pose.getHeading();
        return Math.toDegrees(a1 + a2);
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

        return low - 1;
    }
}
