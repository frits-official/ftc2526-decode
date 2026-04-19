package org.firstinspires.ftc.teamcode.misc;

import com.pedropathing.geometry.Pose;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.opencv.core.Mat;


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
            public static final Pose pushLever = new Pose(12.44, 60.75, Math.toRadians(160));
        }
        public static class BASIC_POSE_NEAR {
            public static final Pose startPose = new Pose(22.09, 126.75, Math.toRadians(-43));
            public static final Pose scorePoseStart = new Pose(58.67, 92.66, Math.toRadians(-43));
            public static final Pose scorePosePath = new Pose(60.35, 79.25, Math.toRadians(225));
            public static final Pose endPose = new Pose(51.25, 113.97, Math.toRadians(180));

        }

        public static class BASIC_POSE_FAR {
            public static final Pose startPose = new Pose(47.92, 7.48, Math.toRadians(180));
            public static final Pose scorePose = new Pose(48.26, 10.76, Math.toRadians(180));
            public static final Pose endPose = new Pose(37.54, 12.47, Math.toRadians(180));
        }

        public static class PICKUP_POSE {
            public static final Pose pickup1 = new Pose(11.44, 83.47, Math.toRadians(180));
            public static final Pose pickup2 = new Pose(14.45, 59.64, Math.toRadians(180));
            public static final Pose pickup3 = new Pose(12.79, 35.88, Math.toRadians(180));
            public static final Pose pickupHuman = new Pose(12.38, 9.07, Math.toRadians(180));
        }
    }

    public static class RED {
        public static final Pose pushLever = new Pose(128.76, 61.35, Math.toRadians(20.5));
        public static class BASIC_POSE_NEAR {
            public static final Pose startPose = new Pose(119.06, 129.29, Math.toRadians(-135));
            public static final Pose scorePoseStart = new Pose(87.74, 96.83, Math.toRadians(-135));
            public static final Pose scorePosePath = new Pose(81.15, 79.25, Math.toRadians(-45));
            public static final Pose endPose = new Pose(94.87, 121.14);
        }

        public static class BASIC_POSE_FAR {
            public static final Pose startPose = new Pose(95.33, 7.78);
            public static final Pose scorePose = new Pose(91, 13.17);
            public static final Pose endPose = new Pose(103.68, 15.48);
        }

        public static class PICKUP_POSE {
            public static final Pose pickup1 = new Pose(123.68, 83.61);
            public static final Pose pickup2 = new Pose(126.25, 58.22);
            public static final Pose pickup3 = new Pose(126.95, 36.22);
            public static final Pose pickupHuman = new Pose(132.32, 9.19);

        }
    }
}
