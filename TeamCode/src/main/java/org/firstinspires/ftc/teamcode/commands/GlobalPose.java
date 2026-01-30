package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;

import org.opencv.core.Mat;

public class GlobalPose {
    public static Pose lastPose;

    public static class BLUE {
        public static final Pose scorePoseEnd = new Pose(51.79, 106.09, Math.toRadians(135));

        public static class BlueNearZonePose {
            //Pose start, score
            public static final Pose startPose = new Pose(49.64, 137.14, Math.toRadians(180));
            public static final Pose scorePose = new Pose(48.43, 96.54, Math.toRadians(135));
            //Pose End
            public static final Pose endPose = new Pose(43.61, 72.41, Math.toRadians(180));
            public static final Pose pushLeverPath1 = new Pose(14.6, 75.4, Math.toRadians(90));
            public static final Pose pushLeverPath2 = new Pose(6.98, 75.64, Math.toRadians(270));

        }

        public static class BlueFarZonePose {
            public static final Pose startPose = new Pose(47.56, 7.43, Math.toRadians(180));
            public static final Pose scorePose = new Pose(59.16, 21.1, Math.toRadians(111));
            public static final Pose endPose = new Pose(54.97, 37.15, Math.toRadians(111));
            public static final Pose pushLever = new Pose(10.12, 70.49, Math.toRadians(270));
        }

        public static class PICKUP_POSE_BLUE {
            public static final Pose pickup1_1 = new Pose(43.08, 83.27, Math.toRadians(180));
            public static final Pose pickup1_2 = new Pose(22.24, 83.27, Math.toRadians(180));
            public static final Pose pickup2_1 = new Pose(42.37, 59.32, Math.toRadians(180));
            public static final Pose pickup2_2 = new Pose(20.51, 58.32, Math.toRadians(180));
            public static final Pose pickup3_1 = new Pose(42.8, 38.37, Math.toRadians(180));
            public static final Pose pickup3_2 = new Pose(16.24, 38.37, Math.toRadians(180));
            public static final Pose pickup4_1 = new Pose(7.23, 32.37, Math.toRadians(270));
            public static final Pose pickup4_2 = new Pose(7.23, 10.08, Math.toRadians(270));
            public static final Pose indulge1 = new Pose(7.33, 25.67, Math.toRadians(135));
            public static final Pose indulge2 = new Pose(7.63, 54.51, Math.toRadians(90));
        }
    }

    public static class RED {
        public static final Pose scorePoseEnd = new Pose(88.38, 104.69, Math.toRadians(45));

        public static class RedNearZonePose {
            //Pose start, score
            public static final Pose startPose = new Pose(97.33, 136.35, Math.toRadians(0));
            public static final Pose scorePose = new Pose(90.2, 90.06, Math.toRadians(45));
            //Pose End
            public static final Pose endPose = BLUE.BlueNearZonePose.endPose.mirror();
            public static final Pose pushLeverPath1 = new Pose(130.47, 79.66, Math.toRadians(180));
            public static final Pose pushLeverPath2 = new Pose(131.72, 74.15, Math.toRadians(270));
        }

        public static class RedFarZonePose {
            public static final Pose startPose = new Pose(96.04, 7.59, Math.toRadians(0));
            public static final Pose scorePose = new Pose(82.53, 22.1, Math.toRadians(69));
            public static final Pose endPose = BLUE.BlueFarZonePose.endPose.mirror();
            public static final Pose pushLever = new Pose(130.72, 72.75, Math.toRadians(270));
        }

        public static class PICKUP_POSE_RED {
            public static final Pose pickup1_1 = new Pose(102.98, 85.96, Math.toRadians(0));
            public static final Pose pickup1_2 = new Pose(126.4, 85.96, Math.toRadians(0));
            public static final Pose pickup2_1 = new Pose(102.89, 62.75, Math.toRadians(0));
            public static final Pose pickup2_2 = new Pose(127.46, 62.75, Math.toRadians(0));

            public static final Pose pickup3_1 = new Pose(100.33, 37.82, Math.toRadians(0));
            public static final Pose pickup3_2 = new Pose(130.82, 37.82, Math.toRadians(0));
            public static final Pose pickup4_1 = new Pose(137.69, 35.39, Math.toRadians(270));
            public static final Pose pickup4_2 = new Pose(137.69, 12.53, Math.toRadians(270));
            public static final Pose indulge1 = new Pose(133.23, 30.73, Math.toRadians(45));
            public static final Pose indulge2 = new Pose(138.09, 58.72, Math.toRadians(90));
        }
    }
}
