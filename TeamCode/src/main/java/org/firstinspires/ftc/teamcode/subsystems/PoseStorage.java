package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.pedropathing.geometry.Pose;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PoseStorage {
    public static Pose lastPose = new Pose(0, 0, 0);
    static File file = new File("/sdcard/FIRST/pose.txt");;

    @SuppressLint("SdCardPath")
    public static void init() {
        try {
            file.createNewFile();
        } catch (IOException ignored) {
        }
    }

    public static void setPose(Pose pose) {
        if (pose.getX() == 0 && pose.getY() == 0 && pose.getHeading() == 0) return;
        PoseStorage.lastPose = pose;

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(pose.getX() + " " + pose.getY() + " " + pose.getHeading());
        } catch (IOException ignored) {
        }
    }

    public static Pose getPose() {
        if (lastPose.getX() != 0 || lastPose.getY() != 0 || lastPose.getHeading() != 0)
            return PoseStorage.lastPose;

        try (Scanner fileReader = new Scanner(file)) {
            double x = fileReader.nextDouble();
            double y = fileReader.nextDouble();
            double heading = fileReader.nextDouble();

            return new Pose(x, y, heading);
        } catch (FileNotFoundException ignored) {
            return new Pose(0, 0, 0);
        }
    }
}
