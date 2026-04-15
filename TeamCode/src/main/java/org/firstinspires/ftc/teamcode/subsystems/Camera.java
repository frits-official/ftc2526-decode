package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.skeletonarmy.marrow.TimerEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.Constants;

public class Camera {
    HardwareMap hardwareMap;
    Limelight3A limelight;
    boolean isDetect = false;

    public void init(HardwareMap _hardwareMap, Constants.ALLIANCE alliance) {
        hardwareMap = _hardwareMap;

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        pipelineSwitch(alliance);
        limelight.start();
    }

    public void pipelineSwitch(Constants.ALLIANCE alliance) {
        if (alliance == Constants.ALLIANCE.BLUE) limelight.pipelineSwitch(0);
        else if (alliance == Constants.ALLIANCE.RED) limelight.pipelineSwitch(1);
    }

    public LLResult getLastestResult() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            isDetect = true;
            return result;
        } else {
            isDetect = false;
            return null;
        }
    }

    public LLStatus getStatus() {
        return limelight.getStatus();
    }

    public void stop() {
        limelight.stop();
    }

    public Pose getAprilTagPose(double robotYaw) {
        limelight.updateRobotOrientation(robotYaw + Constants.CAMERA.fieldFaceAngle);
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            Pose3D botPose = result.getBotpose_MT2();

            if (botPose != null) {
                isDetect = true;
                double x = botPose.getPosition().x;
                double y = botPose.getPosition().y;
                double heading = botPose.getOrientation().getYaw(AngleUnit.RADIANS);

                Pose standardFTCPose = new Pose(x, y, heading).scale(39.37);

                return FTCCoordinates.INSTANCE.convertToPedro(standardFTCPose);
            }
        }
        isDetect = false;
        return new Pose();
    }

    public boolean getDetect() {
        return isDetect;
    }
}
