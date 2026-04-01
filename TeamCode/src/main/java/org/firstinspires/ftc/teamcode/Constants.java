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
        public static double s = 0.04;
        public static double shootingIncrement = 0;
    }

    @Configurable
    public static class HOOD {
        public static double maxAngle = 80;
        public static double minAngle = 25;
        public static double hoodOffset = 25.0;
        public static double gearRatio = 2;
        public static double servoRange = 300;
    }

    @Configurable
    public static class TURRET {
        public static double p = 0;
        public static double i = 0;
        public static double d = 0;
        public static double f = 0.;
        public static double tolerance = 0.1;
        public static double maxAngle = 180;
        public static double minAngle = -180;
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
        public static double goalShooting = .95;
        public static double farShooting = .9;
        public static double zoneThreshold = 250;
    }

    @Configurable
    public static class CAMERA {
        public static double fieldFaceAngle = 90;
    }
    public static class SHOOTER_CALCULATION {
        // https://docs.google.com/spreadsheets/d/1fzLwaEBuZ9TpgEnR5Y467lHZ0Rm3B78DA-xSx2ID6B4/edit?gid=0#gid=0
        public static final List<Double> distanceThresh = Arrays.asList(0.46, 75.58, 90.9, 95.83, 100.64, 105.5, 110.798, 125.38, 130.1, 135.3, 145.09, 150.21, 155.4, 164.89, 170.04, 180.0, 190.77, 195.57, 205.39, 250.0);
        public static final List<Double> targetAngle = Arrays.asList(25.5, 25.5, 25.5, 25.5, 26.0, 26.0, 27.0, 27.0, 28.0, 29.0, 35.0, 35.0, 35.0, 37.0, 37.0, 37.0, 38.0, 40.0, 38.0, 40.0);
        public static final List<Double> targetVelocity = Arrays.asList(980.0, 990.0, 1000.0, 1030.0, 1030.0, 1050.0, 1050.0, 1100.0, 1100.0, 1125.0, 1125.0, 1150.0, 1200.0, 1215.0, 1250.0, 1300.0, 1325.0, 13250.0, 1350.0, 1600.0);
    }
}
