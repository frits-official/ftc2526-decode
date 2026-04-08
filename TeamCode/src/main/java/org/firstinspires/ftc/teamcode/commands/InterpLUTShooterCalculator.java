package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;


import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.ShooterState;

public class InterpLUTShooterCalculator {

    public static ShooterState calcShoot(Pose pose) {
        double distance = ShootingMath.getStaticGoalVector(pose).getMagnitude();
        return new ShooterState(Robot.velocityLUT.get(distance),
                                Robot.angleLUT.get(distance),
                                ShootingMath.getTurretHeadingFromOdometry(pose, ShootingMath.getStaticGoalVector(pose)));
    }

    // this for shooting on the move
    public static ShooterState calcShootOnMoving(Follower follower) {
        // calc the static one
        ShooterState staticShoot = calcShoot(follower.getPose());
        Vector staticGoal = ShootingMath.getStaticGoalVector(follower.getPose());

        double time = staticGoal.getMagnitude() / (ShootingMath.getVelFromTick(staticShoot.getVelocity()) * Math.cos(Math.toRadians(staticShoot.getAngle())));

        Vector robotVel = follower.getVelocity();
        Vector offset = new Vector(robotVel.getMagnitude() * time, robotVel.getTheta());
        Vector newGoal = staticGoal.minus(offset);
        double distance = newGoal.getMagnitude();
        return new ShooterState(Robot.velocityLUT.get(distance),
                Robot.angleLUT.get(distance),
                ShootingMath.getTurretHeadingFromOdometry(follower.getPose(), newGoal));
    }

}
