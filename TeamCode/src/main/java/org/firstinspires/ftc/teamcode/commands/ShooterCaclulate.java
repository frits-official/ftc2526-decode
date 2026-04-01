package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;


public class ShooterCaclulate {
    public static double getTickFromVel (double velocity){
        return MathFunctions.clamp(Constants_ShooterCacl.slope * velocity - Constants_ShooterCacl.static_friction + Constants_ShooterCacl.flywheelOffset,
                Constants_ShooterCacl.flywheelMinSpeed, Constants_ShooterCacl.flywheelMaxSpeed);
    }
    public static ShooterAim.ShooterState caclShotVectorAndTurret (Robot robot, double heading) {
        Pose currentPose = robot.follower.getPose();
        Vector robotToGoalVector = ShooterAim.getGoalVec(currentPose);
        double g = 9.8 * 100;
        double x = robotToGoalVector.getMagnitude() - Constants_ShooterCacl.passThroughPoint;
        double y = Constants_ShooterCacl.scoreHeight;
        double a = Constants_ShooterCacl.scoreAngle;

        double hoodAngle = MathFunctions.clamp(Math.atan(2 * y / x - Math.tan(a)), Constants.HOOD.minAngle, Constants.HOOD.maxAngle);

        double flywheelSpeed = Math.sqrt(g * Math.pow(x, 2) / (2 * Math.pow(Math.cos(hoodAngle), 2) * (x *Math.tan(hoodAngle) - y)));

        Vector robotVel = robot.follower.poseTracker.getVelocity();

        double coordinateTheta = robotVel.getTheta() - robotToGoalVector.getTheta();

        double paralleComponent = -Math.cos(coordinateTheta) * robotVel.getMagnitude();
        double perpendicularComponent = Math.sin(coordinateTheta) * robotVel.getMagnitude();

        if (flywheelSpeed < 0.1) flywheelSpeed = 0.1;
        if (x < 0.1) x = 0.1;

        double vz = flywheelSpeed * Math.sin(hoodAngle);
        double time = x / (flywheelSpeed * Math.sin(hoodAngle));
        double ivr = x / time + paralleComponent;
        double nvr = Math.sqrt(Math.pow(ivr, 2) + Math.pow(perpendicularComponent, 2));
        double ndr = nvr * time;

        hoodAngle = MathFunctions.clamp(Math.atan(vz / nvr),
                Constants.HOOD.minAngle, Constants.HOOD.maxAngle);

        flywheelSpeed = Math.sqrt(g * Math.pow(ndr, 2) / (2 * Math.pow(Math.cos(hoodAngle), 2) * (ndr * Math.tan(hoodAngle) - y)));

        double turretVelComOffset = Math.atan(perpendicularComponent / ivr);
        double turretAngle = Math.toDegrees(heading - robotToGoalVector.getTheta() + turretVelComOffset);

        return new ShooterAim.ShooterState(flywheelSpeed, hoodAngle, turretAngle);
    }
}
