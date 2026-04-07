package org.firstinspires.ftc.teamcode.misc;

import com.pedropathing.geometry.Pose;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

public class GlobalPose {
    public static Pose lastPose = new Pose(72, 72, Math.toRadians(0));
    public static class ZONES {
        public static final PolygonZone closeLaunchZone = new PolygonZone(new Point(144, 144), new Point(72, 72), new Point(0, 144));
        public static final PolygonZone farLaunchZone = new PolygonZone(new Point(48, 0), new Point(72, 24), new Point(96, 0));
        public static final PolygonZone blueGate = new PolygonZone(new Point(19, 72), 10, 2.75);
        public static final PolygonZone redGate = new PolygonZone(new Point(125, 72), 10, 2.75);
    }

    public static class BLUE {
        public static final Pose pushLever = new Pose(13.73, 61, Math.toRadians(130));
        public static class BASIC_POSE_NEAR {
            public static final Pose startPose = new Pose(18.57, 114.17, Math.toRadians(180));
            public static final Pose scorePose = new Pose(48.08, 95.56, Math.toRadians(180));
            public static final Pose endPose = new Pose(38.05, 83.11, Math.toRadians(180));

        }

        public static class BASIC_POSE_FAR {
            public static final Pose startPose = new Pose(47.53, 7.47, Math.toRadians(180));
            public static final Pose scorePose = new Pose(59.26, 10.76, Math.toRadians(180));
            public static final Pose endPose = new Pose(37.54, 12.47, Math.toRadians(180));
        }

        public static class PICKUP_POSE {
            public static final Pose pickup1 = new Pose(25.05, 83.2, Math.toRadians(180));
            public static final Pose pickup2 = new Pose(20.06, 59.73, Math.toRadians(180));
            public static final Pose pickup3 = new Pose(16.79, 35.88, Math.toRadians(180));
            public static final Pose pickupHuman = new Pose(11.97, 7.32, Math.toRadians(180));
        }
    }

    public static class RED {
        public static final Pose pushLever = new Pose(129.62, 59.75, Math.toRadians(40));
        public static class BASIC_POSE_NEAR {
            public static final Pose startPose = new Pose(127.03, 111.97);
            public static final Pose scorePose = new Pose(95.92, 95.56);
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
