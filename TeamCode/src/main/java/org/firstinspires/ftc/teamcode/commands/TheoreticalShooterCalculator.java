package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.pedropathing.math.Vector;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.misc.ShooterState;


public class TheoreticalShooterCalculator {

    /**
     * use theoretical shooting formula. return in cm/s, deg, deg
     * @param follower
     * @return shooterState in cm/s, deg, deg
     */
    public static ShooterState calcShoot(Follower follower) {
        Pose currentPose = follower.getPose();
        Vector robotToGoalVector = ShootingMath.getStaticGoalVector(currentPose);

        double g = 9.8 * 100;
        double x = robotToGoalVector.getMagnitude() - TheoreticalCalculatorConstant.passThroughPoint;
        if (x < 0.1) x = 0.1;
        double y = TheoreticalCalculatorConstant.scoreHeight;
        double a = Math.toRadians(-TheoreticalCalculatorConstant.scoreAngle);

        double hoodAngle = MathFunctions.clamp(Math.atan((2 * y / x) - Math.tan(a)), Math.toRadians(Constants.HOOD.minAngle), Math.toRadians(Constants.HOOD.maxAngle));

        double flywheelSpeed = Math.sqrt(g * Math.pow(x, 2) / (2 * Math.pow(Math.cos(hoodAngle), 2) * (x * Math.tan(hoodAngle) - y)));

        Vector robotVel = follower.getVelocity();

        double coordinateTheta = robotVel.getTheta() - robotToGoalVector.getTheta();

        double parallelComponent = -Math.cos(coordinateTheta) * robotVel.getMagnitude();
        double perpendicularComponent = Math.sin(coordinateTheta) * robotVel.getMagnitude();

        if (flywheelSpeed < 0.1) flywheelSpeed = 0.1;

        double vz = flywheelSpeed * Math.sin(hoodAngle);
        double time = x / (flywheelSpeed * Math.cos(hoodAngle));
        double ivr = x / time + parallelComponent;
        double nvr = Math.sqrt(Math.pow(ivr, 2) + Math.pow(perpendicularComponent, 2));
        double ndr = nvr * time;

        hoodAngle = MathFunctions.clamp(Math.atan(vz / nvr),
                Math.toRadians(Constants.HOOD.minAngle), Math.toRadians(Constants.HOOD.maxAngle));

        flywheelSpeed = Math.sqrt(g * Math.pow(ndr, 2) / (2 * Math.pow(Math.cos(hoodAngle), 2) * (ndr * Math.tan(hoodAngle) - y)));

        double turretVelComOffset = Math.atan2(perpendicularComponent, ivr);
        double turretAngle = robotToGoalVector.getTheta() - follower.getHeading() - turretVelComOffset;

        turretAngle = Math.IEEEremainder(turretAngle, 2 * Math.PI);

        return new ShooterState(ShootingMath.getTickFromVel(flywheelSpeed), 90 - Math.toDegrees(hoodAngle), Math.toDegrees(turretAngle));
    }
}
