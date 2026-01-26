package org.firstinspires.ftc.teamcode.commands;

import com.pedropathing.geometry.Pose;

public class GlobalPose {
    public static Pose lastPose = new Pose(0, 0, 0);
    public static final Pose startPoseBlueNearZone = new Pose(49.64, 137.14, Math.toRadians(180));
    public static final Pose scorePoseBlueNearZone = new Pose(48.43, 96.54, Math.toRadians(135));

    public static class BlueNearZoneNoLever {
        //Pose Path1
        public static final Pose pickup1_1 = new Pose(45.88, 84.19, Math.toRadians(180));
        public static final Pose pickup1_2 = new Pose(27.54, 84.52, Math.toRadians(180));
        //Pose Path2
        public static final Pose pickup2_1 = new Pose(43.84, 60.11, Math.toRadians(180));
        public static final Pose pickup2_2 = new Pose(27.31, 60.12, Math.toRadians(180));
        //Pose Path3
        public static final Pose pickup3_1 = new Pose(44.25, 37.91, Math.toRadians(180));
        public static final Pose pickup3_2 = new Pose(25.3, 38.54, Math.toRadians(180));
        //Pose End
        public static final Pose endPose = new Pose(43.61, 72.41, Math.toRadians(180));
    }
}
