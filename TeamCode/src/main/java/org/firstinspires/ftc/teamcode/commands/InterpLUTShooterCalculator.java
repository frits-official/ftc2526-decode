package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.seattlesolvers.solverslib.util.InterpLUT;


import org.firstinspires.ftc.teamcode.misc.ShooterState;

import java.util.Arrays;
import java.util.List;

public class InterpLUTShooterCalculator {
    // https://docs.google.com/spreadsheets/d/1fzLwaEBuZ9TpgEnR5Y467lHZ0Rm3B78DA-xSx2ID6B4/edit?gid=0#gid=0
    // ensure distanceThresh have strictly increasing values and match the targetVelocity and targetAngle
    public static final List<Double> distanceThresh = Arrays.asList(84.0, 109.27, 127.34, 148.9, 168.66, 187.57, 209.5, 219.74, 347.3, 369.8, 373.65);
    public static final List<Double> targetAngle = Arrays.asList(30.0, 38.0, 45.0, 50.0, 52.0, 52.0, 55.0, 53.0, 62.0, 60.0, 57.0);
    public static final List<Double> targetVelocity = Arrays.asList(950.0, 960.0, 1000.0, 1040.0, 1075.0, 1125.0, 1160.0, 1160.0, 1447.0, 1415.0, 1430.0);
    public static InterpLUT velocityLUT, angleLUT, timeLUT;

    public static void init() {
        velocityLUT = new InterpLUT(distanceThresh, targetVelocity);
        angleLUT = new InterpLUT(distanceThresh, targetAngle);

        velocityLUT.createLUT();
        angleLUT.createLUT();
    }

    public static ShooterState calcShoot(Pose pose) {
        double distance = ShootingMath.getStaticGoalVector(pose).getMagnitude();
        return new ShooterState(velocityLUT.get(distance),
                                angleLUT.get(distance),
                                ShootingMath.getTurretHeadingFromOdometry(pose, ShootingMath.getStaticGoalVector(pose)));
    }

    // this for shooting on the move
    public static ShooterState calcShootOnMoving(Follower follower) {
        // calc the static one
        Vector staticGoal = ShootingMath.getStaticGoalVector(follower.getPose());

        double time = timeLUT.get(staticGoal.getMagnitude());

        Vector robotVel = follower.getVelocity();
        Vector offset = new Vector(robotVel.getMagnitude() * time, robotVel.getTheta());
        Vector newGoal = staticGoal.minus(offset);
        double distance = newGoal.getMagnitude();
        return new ShooterState(velocityLUT.get(distance),
                angleLUT.get(distance),
                ShootingMath.getTurretHeadingFromOdometry(follower.getPose(), newGoal));
    }

}
