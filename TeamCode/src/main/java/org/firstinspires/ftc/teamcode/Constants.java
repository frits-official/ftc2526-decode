package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

public class Constants {
    public static double nominalVoltage = 12.5;
    public static enum ALLIANCE { BLUE, RED };
    public static final Pose RED_GOAL_POS = new Pose(138, 138);
    public static final Pose BLUE_GOAL_POS = RED_GOAL_POS.mirror();

    @Configurable
    public static class DRIVE {
        public static double turnSpeedMultiplier = .8;
    }
    @Configurable
    public static class SHOOTER {
        public static double p = 0.01;
        public static double i = 0;
        public static double d = 0;
        public static double v = 0.00039;
        public static double s = 0.05;
        public static double shootingIncrement = 0;
        public static double timeCoef = 1;
    }

    @Configurable
    public static class HOOD {
        public static double maxAngle = 64;
        public static double minAngle = 25;
        public static double hoodOffset = 25;
        public static double gearRatio = 2;
        public static double servoRange = 300;
    }

    @Configurable
    public static class TURRET {
        public static double p = 0.001; // 0.005 if using tick
        public static double i = 0;
        public static double d = 0;
        public static double f = 0.16;
        public static double tolerance = 0.5;
        public static double maxAngle = 5;
        public static double minAngle = -365;
    }

    @Configurable
    public static class DOOR {
        public static double block = .78;
        public static double unblock = 1;
        public static double delayTime = 2;
        public static double openTime = 2;
    }

    @Configurable
    public static class INTAKE {
        public static double normal = .7;
        public static double goalShooting = .99;
        public static double farShooting = .99;
        public static double zoneThreshold = 250;
    }

    @Configurable
    public static class CAMERA {
        public static double fieldFaceAngle = 90;
    }
}
