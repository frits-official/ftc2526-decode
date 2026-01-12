package Subsystem;

import com.bylazar.configurables.annotations.Configurable;

public class ConstantFTC {
    @Configurable
    public static class SHOOTER {
        public static double p = 0.01;
        public static double i = 0;
        public static double d = 0;
    }

    @Configurable
    public static class HOOD {
        public static double p = 0.225;
        public static double i = 0;
        public static double d = 0;
        public static double threshold = 2.65;
    }

    @Configurable
    public static class TURRET {
        public static double p = 0.015;
        public static double i = 0;
        public static double d = 0;
        public static double TURRETMAX = 225;
        public static double TURRETMIN = -225;
    }
}
