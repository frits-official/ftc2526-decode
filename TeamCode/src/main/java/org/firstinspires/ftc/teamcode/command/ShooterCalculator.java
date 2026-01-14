package org.firstinspires.ftc.teamcode.command;

import static org.firstinspires.ftc.teamcode.Constants.SHOOTER_CALCULATION.*;

import com.pedropathing.geometry.Pose;

public class ShooterCalculator {
    public static class ShooterState {
        private double velocity, angle;

        public ShooterState(double velocity, double angle) {
            this.velocity = velocity;
            this.angle = angle;
        }

        public double getVelocity() { return velocity; }
        public double getAngle() { return angle; }
    }
    public static ShooterState calcShoot(double cm, Pose pose) {
        double distance = cm;
        if (cm == 0.0)
            distance = ((pose.getX() / 10.0) - 30.48) / Math.sin(pose.getHeading());
        int i = lowerBound(distance);
        return new ShooterState(targetVelocity[i], targetAngle[i]);
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
