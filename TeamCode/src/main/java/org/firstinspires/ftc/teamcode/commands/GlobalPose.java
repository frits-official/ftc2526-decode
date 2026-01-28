package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;

public class GlobalPose {
    public static Pose lastPose;

    public static class BLUE {
        public static final Pose pushLever = new Pose(10.12, 70.49, Math.toRadians(270));
        public static final Pose scorePoseEnd = new Pose(51.79, 106.09, Math.toRadians(135));

        public static class BlueNearZonePose {
            //Pose start, score
            public static final Pose startPose = new Pose(49.64, 137.14, Math.toRadians(180));
            public static final Pose scorePose = new Pose(48.43, 96.54, Math.toRadians(135));
            //Pose End
            public static final Pose endPose = new Pose(43.61, 72.41, Math.toRadians(180));

        }

        public static class BlueFarZonePose {
            public static final Pose startPose = new Pose(47.56, 7.43, Math.toRadians(180));
            public static final Pose scorePose = new Pose(59.16, 21.1, Math.toRadians(111));
            public static final Pose endPose = new Pose(54.97, 37.15, Math.toRadians(111));
        }

        public static class PICKUP_POSE_BLUE {
            public static final Pose pickup1_1 = new Pose(43.08, 83.27, Math.toRadians(180));
            public static final Pose pickup1_2 = new Pose(20.24, 83.27, Math.toRadians(180));
            public static final Pose pickup2_1 = new Pose(42.37, 59.32, Math.toRadians(180));
            public static final Pose pickup2_2 = new Pose(22.11, 59.32, Math.toRadians(180));
            public static final Pose pickup3_1 = new Pose(42.8, 35.97, Math.toRadians(180));
            public static final Pose pickup3_2 = new Pose(20.24, 35.97, Math.toRadians(180));
            public static final Pose pickup4_1 = new Pose(7.23, 32.37, Math.toRadians(270));
            public static final Pose pickup4_2 = new Pose(7.23, 10.08, Math.toRadians(270));
            public static final Pose indulge1 = new Pose(7.33, 25.67, Math.toRadians(135));
            public static final Pose indulge2 = new Pose(7.63, 54.51, Math.toRadians(90));
        }
    }

    public static class RED {
        public static final Pose pushLever = BLUE.pushLever.mirror();
        public static final Pose scorePoseEnd = BLUE.scorePoseEnd.mirror();

        public static class RedNearZonePose {
            //Pose start, score
            public static final Pose startPose = BLUE.BlueNearZonePose.startPose.mirror();
            public static final Pose scorePose = BLUE.BlueNearZonePose.scorePose.mirror();
            //Pose End
            public static final Pose endPose = BLUE.BlueNearZonePose.endPose.mirror();
        }

        public static class RedFarZonePose {
            public static final Pose startPose = BLUE.BlueFarZonePose.startPose.mirror();
            public static final Pose scorePose = BLUE.BlueFarZonePose.scorePose.mirror();
            public static final Pose endPose = BLUE.BlueFarZonePose.endPose.mirror();
        }

        public static class PICKUP_POSE_RED {
            public static final Pose pickup1_1 = BLUE.PICKUP_POSE_BLUE.pickup1_1.mirror();
            public static final Pose pickup1_2 = BLUE.PICKUP_POSE_BLUE.pickup1_2.mirror();
            public static final Pose pickup2_1 = BLUE.PICKUP_POSE_BLUE.pickup2_1.mirror();
            public static final Pose pickup2_2 = BLUE.PICKUP_POSE_BLUE.pickup2_2.mirror();
            public static final Pose pickup3_1 = BLUE.PICKUP_POSE_BLUE.pickup3_1.mirror();
            public static final Pose pickup3_2 = BLUE.PICKUP_POSE_BLUE.pickup3_2.mirror();
            public static final Pose pickup4_1 = BLUE.PICKUP_POSE_BLUE.pickup4_1.mirror();
            public static final Pose pickup4_2 = BLUE.PICKUP_POSE_BLUE.pickup4_2.mirror();
            public static final Pose indulge1 = BLUE.PICKUP_POSE_BLUE.indulge1.mirror();
            public static final Pose indulge2 = BLUE.PICKUP_POSE_BLUE.indulge2.mirror();
        }
    }
}
