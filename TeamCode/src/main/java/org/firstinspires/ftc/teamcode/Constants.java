package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;

import java.util.Arrays;
import java.util.List;

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
    }

    @Configurable
    public static class HOOD {
        public static double maxAngle = 80;
        public static double minAngle = 25;
        public static double hoodOffset = 25;
        public static double gearRatio = 2;
        public static double servoRange = 300;
    }

    @Configurable
    public static class TURRET {
        public static double p = 0.003; // 0.005 if using tick
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
    public static class SHOOTER_CALCULATION {
        // https://docs.google.com/spreadsheets/d/1fzLwaEBuZ9TpgEnR5Y467lHZ0Rm3B78DA-xSx2ID6B4/edit?gid=0#gid=0
        // ensure distanceThresh have strictly increasing values and match the targetVelocity and targetAngle
        public static final List<Double> distanceThresh = Arrays.asList(84.0, 109.27, 127.34, 148.9, 168.66, 187.57, 209.5, 219.74, 347.3, 369.8, 373.65);
        public static final List<Double> targetAngle = Arrays.asList(30.0, 38.0, 45.0, 50.0, 52.0, 52.0, 55.0, 53.0, 62.0, 60.0, 57.0);
        public static final List<Double> targetVelocity = Arrays.asList(950.0, 960.0, 1000.0, 1040.0, 1075.0, 1125.0, 1160.0, 1160.0, 1447.0, 1415.0, 1430.0);
    }
}
