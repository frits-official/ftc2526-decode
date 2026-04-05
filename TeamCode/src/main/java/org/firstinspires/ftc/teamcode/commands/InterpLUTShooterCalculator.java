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
                                getTurretHeadingFromOdometry(pose, ShootingMath.getStaticGoalVector(pose)));
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
                getTurretHeadingFromOdometry(follower.getPose(), newGoal));
    }

    /** this will return degree with -180deg -> 180deg will run in counterclockwise
     * use this for nearly accurate turning, we will use limelight for more accuracy **/
    public static double getTurretHeadingFromOdometry(Pose pose, Vector goal) {
        Vector bot = new Vector(1, pose.getHeading());
        Vector perpBot = new Vector(1, pose.getHeading() + Math.PI / 2);

        double angle = goal.getTheta() - bot.getTheta();
        return Math.toDegrees(Math.IEEEremainder(angle, 2 * Math.PI));
    }
}
