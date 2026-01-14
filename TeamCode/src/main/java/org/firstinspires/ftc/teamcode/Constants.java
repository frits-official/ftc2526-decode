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
        public static double p = 0.015;
        public static double i = 0;
        public static double d = 0;
        public static double maxAngle = 225;
        public static double minAngle = -225;
    }

    @Configurable
    public static class DOOR {
        public static double block = 0.25;
        public static double unblock = 0;
        public static double delayTime = 2.5;
        public static double openTime = 2.5;
    }
}
