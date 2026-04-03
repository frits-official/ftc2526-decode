package org.firstinspires.ftc.teamcode.misc;

public class ShooterState {
    private double velocity, angle, heading;

    public ShooterState(double velocity, double angle, double heading) {
        this.velocity = velocity;
        this.angle = angle;
        this.heading = heading;
    }

    public void setVelocity(double velocity) { this.velocity = velocity; }
    public void setAngle(double angle) { this.angle = angle; }
    public void setHeading(double heading) { this.heading = heading; }

    public double getVelocity() { return velocity; }
    public double getAngle() { return angle; }
    public double getHeading() { return heading; }
}
