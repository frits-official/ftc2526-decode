package org.firstinspires.ftc.teamcode.misc;

import com.pedropathing.geometry.Pose;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;


public class GlobalPose {
    public static class TEST_POSE {
        public static final Pose startPose = new Pose(0, 0, Math.toRadians(180));
        public static final Pose endPose = new Pose(0, 0, Math.toRadians(180));
    }
    public static class ZONES {
        public static final PolygonZone closeLaunchZone = new PolygonZone(new Point(144, 144), new Point(72, 72), new Point(0, 144));
        public static final PolygonZone farLaunchZone = new PolygonZone(new Point(48, 0), new Point(72, 24), new Point(96, 0));
        public static final PolygonZone blueGate = new PolygonZone(new Point(19, 72), 10, 2.75);
        public static final PolygonZone redGate = new PolygonZone(new Point(125, 72), 10, 2.75);
    }

    public static class BLUE {
        public static class RETAKE_POSE {
            public static final Pose pushLever = new Pose(10.6, 59.75, Math.toRadians(160));
            public static final Pose reTake1 = new Pose(11.44, 57.18, Math.toRadians(130));
            public static final Pose reTake2 = new Pose(9, 52.78, Math.toRadians(107));
            public static final Pose reTake3 = new Pose(8.33, 60.6, Math.toRadians(100));
            public static final Pose reTakeOut = new Pose(12.12, 54.9, Math.toRadians(100));
        }
        public static class BASIC_POSE_NEAR {
            public static final Pose startPose = new Pose(18.28, 121.71, Math.toRadians(322));
            public static final Pose scorePoseStart = new Pose(   58.35, 78.25, Math.toRadians(322));
            public static final Pose scorePosePath = new Pose(58.35, 77.25, Math.toRadians(225));
            public static final Pose endPose = new Pose(51.25, 113.97, Math.toRadians(180));

        }

        public static class BASIC_POSE_FAR {
            public static final Pose startPose = new Pose(47.92, 7.48, Math.toRadians(180));
            public static final Pose scorePose = new Pose(48.26, 10.76, Math.toRadians(180));
            public static final Pose endPose = new Pose(37.54, 12.47, Math.toRadians(180));
        }

        public static class PICKUP_POSE {
            public static final Pose pickup1 = new Pose(14.44, 83.47, Math.toRadians(180));
            public static final Pose pickup2 = new Pose(14.45, 59.64, Math.toRadians(180));
            public static final Pose pickup3 = new Pose(12.79, 35.88, Math.toRadians(180));
            public static final Pose pickupHuman = new Pose(11.97, 7.32, Math.toRadians(180));
        }
    }

    public static class RED {
        public static final Pose pushLever = new Pose(130.27, 59.9, Math.toRadians(41));
        public static final Pose reTake = new Pose(131.6, 58.3, Math.toRadians(41));
        public static class BASIC_POSE_NEAR {
            public static final Pose startPose = new Pose(127.07, 119.48, Math.toRadians(217));
            public static final Pose scorePose1 = new Pose(95.92, 95.56);
            public static final Pose scorePose2 = new Pose(85.67, 84.09);
            public static final Pose endPose = new Pose(105.95, 83.11);
        }

        public static class BASIC_POSE_FAR {
            public static final Pose startPose = new Pose(93.73, 6.67);
            public static final Pose scorePose = new Pose(84.74, 10.76);
            public static final Pose endPose = new Pose(106.46, 12.47);
        }

        public static class PICKUP_POSE {
            public static final Pose pickup1 = new Pose(118.95, 83.2);
            public static final Pose pickup2 = new Pose(123.94, 59.73);
            public static final Pose pickup3 = new Pose(127.21, 35.88);
            public static final Pose pickupHuman = new Pose(132.03, 7.32);

        }
    }
}
