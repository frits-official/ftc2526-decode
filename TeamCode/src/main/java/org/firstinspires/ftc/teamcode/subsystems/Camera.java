package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Camera {
    HardwareMap hardwareMap;
    Limelight3A limelight;

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
            return result;
        } else return null;
    }

    public LLStatus getStatus() {
        return limelight.getStatus();
    }

    public void stop() {
        limelight.stop();
    }

    /** in centimeters, 4 number behind dot **/
    public double getDistanceFromGoalTagCM() {
        // d = (h2 - h1) / tan(a1 + a2)
        return Math.round((44 / Math.tan((17.5 + Math.abs(getLastestResult().getTx())) * (Math.PI / 180.0)) - 3.54) * 10000.0) / 10000.0;
    }
}
