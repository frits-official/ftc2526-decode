package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

public class Constants {
    public static enum ALLIANCE { BLUE, RED }; 
    @Configurable
    public static class SHOOTER {
        public static double p = 0.005;
        public static double i = 0;
        public static double d = 0;
    }

    @Configurable
    public static class HOOD {
        public static double p = 0.15;
        public static double i = 0;
        public static double d = 0;
        public static double changeFormulaDistanceThreshold = 2.65;
        public static double maxAngle = 45.5;
        public static double minAngle = 25.5;
    }

    @Configurable
    public static class TURRET {
        public static double p = 0.012;
        public static double i = 0;
        public static double d = 0;
        public static double f = 0.16;
        public static double tolerance = 0.1;
        public static double maxAngle = 185;
        public static double minAngle = -185;
    }

    @Configurable
    public static class DOOR {
        public static double block = 0.35;
        public static double unblock = 0;
        public static double delayTime = 3;
        public static double openTime = 3;
    }

    public static class SHOOTER_CALCULATION {
        // https://docs.google.com/spreadsheets/d/1fzLwaEBuZ9TpgEnR5Y467lHZ0Rm3B78DA-xSx2ID6B4/edit?gid=0#gid=0
        // please!!! after testing all, sort the distance column in google sheet
        // and copy it into array WITHOUT changing the order
        // only get lower bound of distanceThresh and then get target velocity and angle from the returned index
        public static final double distanceThresh[] = {70.46, 75.58, 90.9, 95.83, 100.64, 105.5, 110.798, 125.38, 130.1, 135.3, 145.09, 150.21, 155.4, 164.89, 170.04, 180, 190.77, 195.57, 205.39, 250, 300.71, 305.54, 310.2, 315.23, 323.7, 330.22, 335.38, 340.21, 350.73, 355.03};
        public static final double targetAngle[] = {25.5, 25.5, 25.5, 25.5, 26, 26, 27, 27, 28, 29, 35, 35, 35, 37, 37, 37, 38, 40, 38, 38, 38, 38, 38, 38, 38, 38, 38, 38, 35, 36};
        public static final double targetVelocity[] = {980, 990, 1000, 1030, 1030, 1050, 1050, 1100, 1100, 1125, 1125, 1150, 1200, 1215, 1250, 1300, 1325, 1325, 1350, 1540, 1580, 1590, 1600, 1625, 1675, 1675, 1695, 1700, 1700, 1750};
    }
}
