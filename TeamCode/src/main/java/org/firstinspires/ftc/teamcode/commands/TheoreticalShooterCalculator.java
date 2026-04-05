package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.misc.ShooterState;


public class TheoreticalShooterCalculator {
    public static double getTickFromVel (double velocity){
        return MathFunctions.clamp(TheoreticalCalculatorConstant.slope * velocity - TheoreticalCalculatorConstant.static_friction + TheoreticalCalculatorConstant.flywheelOffset,
                TheoreticalCalculatorConstant.flywheelMinSpeed, TheoreticalCalculatorConstant.flywheelMaxSpeed);
    }
    public static ShooterState calcShoot(Follower follower) {
        Pose currentPose = follower.getPose();
        Vector robotToGoalVector = InterpLUTShooterCalculator.getGoalVec(currentPose);

        double g = 9.8 * 100;
        double x = robotToGoalVector.getMagnitude() - TheoreticalCalculatorConstant.passThroughPoint;
        double y = TheoreticalCalculatorConstant.scoreHeight;
        double a = TheoreticalCalculatorConstant.scoreAngle;

        double hoodAngle = MathFunctions.clamp(Math.atan(2 * y / x - Math.tan(a)), Math.toRadians(Constants.HOOD.minAngle), Math.toRadians(Constants.HOOD.maxAngle));

        double flywheelSpeed = Math.sqrt(g * Math.pow(x, 2) / (2 * Math.pow(Math.cos(hoodAngle), 2) * (x * Math.tan(hoodAngle) - y)));

        Vector robotVel = follower.getVelocity();

        double coordinateTheta = robotVel.getTheta() - robotToGoalVector.getTheta();

        double parallelComponent = -Math.cos(coordinateTheta) * robotVel.getMagnitude();
        double perpendicularComponent = Math.sin(coordinateTheta) * robotVel.getMagnitude();

        if (flywheelSpeed < 0.1) flywheelSpeed = 0.1;
        if (x < 0.1) x = 0.1;

        double vz = flywheelSpeed * Math.sin(hoodAngle);
        double time = x / (flywheelSpeed * Math.sin(hoodAngle));
        double ivr = x / time + parallelComponent;
        double nvr = Math.sqrt(Math.pow(ivr, 2) + Math.pow(perpendicularComponent, 2));
        double ndr = nvr * time;

        hoodAngle = MathFunctions.clamp(Math.atan(vz / nvr),
                Math.toRadians(Constants.HOOD.minAngle), Math.toRadians(Constants.HOOD.maxAngle));

        flywheelSpeed = Math.sqrt(g * Math.pow(ndr, 2) / (2 * Math.pow(Math.cos(hoodAngle), 2) * (ndr * Math.tan(hoodAngle) - y)));

        double turretVelComOffset = Math.atan(perpendicularComponent / ivr);
        double turretAngle = Math.toDegrees(follower.getHeading() - robotToGoalVector.getTheta() + turretVelComOffset);

        return new ShooterState(flywheelSpeed, Math.toDegrees(hoodAngle), turretAngle);
    }
}
