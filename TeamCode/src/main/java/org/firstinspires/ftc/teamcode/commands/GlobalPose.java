package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

public class GlobalPose {
    public static class ZONES {
        public static final PolygonZone closeLaunchZone = new PolygonZone(new Point(144, 144), new Point(72, 72), new Point(0, 144));
        public static final PolygonZone farLaunchZone = new PolygonZone(new Point(48, 0), new Point(72, 24), new Point(96, 0));
        public static final PolygonZone blueGate = new PolygonZone(new Point(19, 72), 10, 2.75);
        public static final PolygonZone redGate = new PolygonZone(new Point(125, 72), 10, 2.75);
    }

    public static class BLUE {
        public static final Pose scorePoseEnd = new Pose(51.79, 106.09);

        public static class BlueNearZonePose {
            //Pose start, score
            public static final Pose startPose = new Pose(18.05, 113.56);
            public static final Pose scorePose = new Pose(48.43, 96.54);
            //Pose End
            public static final Pose endPose = new Pose(43.61, 72.41);
            public static final Pose pushLeverPath1 = new Pose(14.6, 75.4);
            public static final Pose pushLeverPath2 = new Pose(6.98, 75.64);

        }

        public static class BlueFarZonePose {
            public static final Pose startPose = new Pose(47.56, 7.43);
            public static final Pose scorePose = new Pose(59.16, 21.1);
            public static final Pose endPose = new Pose(54.97, 37.15);
            public static final Pose pushLever = new Pose(10.12, 70.49);
        }

        public static class PICKUP_POSE_BLUE {
            public static final Pose pickup1_1 = new Pose(43.08, 83.27);
            public static final Pose pickup1_2 = new Pose(22.24, 83.27);
            public static final Pose pickup2_1 = new Pose(42.37, 59.32);
            public static final Pose pickup2_2 = new Pose(20.51, 58.32);
            public static final Pose pickup3_1 = new Pose(42.8, 38.37);
            public static final Pose pickup3_2 = new Pose(16.24, 38.37);
            public static final Pose pickup4_1 = new Pose(7.23, 32.37);
            public static final Pose pickup4_2 = new Pose(7.23, 10.08);
            public static final Pose indulge1 = new Pose(7.33, 25.67);
            public static final Pose indulge2 = new Pose(7.63, 54.51);
        }
    }

    public static class RED {
        public static final Pose scorePoseEnd = new Pose(88.38, 104.69);

        public static class RedNearZonePose {
            //Pose start, score
            public static final Pose startPose = new Pose(125.47, 113.69);
            public static final Pose scorePose = new Pose(90.2, 90.06);
            //Pose End
            public static final Pose endPose = BLUE.BlueNearZonePose.endPose.mirror();
            public static final Pose pushLeverPath1 = new Pose(130.47, 79.66);
            public static final Pose pushLeverPath2 = new Pose(131.72, 74.15);
        }

        public static class RedFarZonePose {
            public static final Pose startPose = new Pose(96.04, 7.59);
            public static final Pose scorePose = new Pose(82.53, 22.1);
            public static final Pose endPose = BLUE.BlueFarZonePose.endPose.mirror();
            public static final Pose pushLever = new Pose(130.72, 72.75);
        }

        public static class PICKUP_POSE_RED {
            public static final Pose pickup1_1 = new Pose(102.98, 85.96);
            public static final Pose pickup1_2 = new Pose(126.4, 85.96);
            public static final Pose pickup2_1 = new Pose(102.89, 62.75);
            public static final Pose pickup2_2 = new Pose(127.46, 62.75);

            public static final Pose pickup3_1 = new Pose(100.33, 37.82);
            public static final Pose pickup3_2 = new Pose(130.82, 37.82);
            public static final Pose pickup4_1 = new Pose(137.69, 35.39);
            public static final Pose pickup4_2 = new Pose(137.69, 12.53);
            public static final Pose indulge1 = new Pose(133.23, 30.73);
            public static final Pose indulge2 = new Pose(138.09, 58.72);
        }
    }
}
