package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;

public class GlobalPose {
    public static Pose lastPose;

    public static class BlueNearZonePose {
        //Pose start, score
        public static final Pose startPose = new Pose(49.64, 137.14, Math.toRadians(180));
        public static final Pose scorePose = new Pose(48.43, 96.54, Math.toRadians(180));
        //Pose Path1
        public static final Pose pickup1_1 = new Pose(45.88, 84.19, Math.toRadians(180));
        public static final Pose pickup1_2 = new Pose(27.54, 84.52, Math.toRadians(180));
        //Pose Path2
        public static final Pose pickup2_1 = new Pose(43.84, 60.11, Math.toRadians(180));
        public static final Pose pickup2_2 = new Pose(24.6, 60.8, Math.toRadians(180));
        //Pose Path3
        public static final Pose pickup3_1 = new Pose(46.03, 39.58, Math.toRadians(180));
        public static final Pose pickup3_2 = new Pose(19.07, 39.08, Math.toRadians(180));
        //Pose End
        public static final Pose endPose = new Pose(43.61, 72.41, Math.toRadians(180));
        //Pose push lever
        public static final Pose pushLever = new Pose(12.61, 69.88, Math.toRadians(270));
    }
}
