package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Camera {
    HardwareMap hardwareMap;
    Limelight3A limelight;
    LLResult result;

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

    public LLResult getLastestValidResult() {
        LLResult preResult = limelight.getLatestResult();
        if (preResult != null && preResult.isValid()) {
            result = preResult;
        }
        return result;
    }

    public LLStatus getStatus() {
        return limelight.getStatus();
    }

    public void stop() {
        limelight.stop();
    }

    /** in centimeters **/
    public double getDistanceFromGoalTagCM() {
        // d = (h2 - h1) / tan(a1 + a2)
        return 44 / (Math.tan(6.33 + getLastestValidResult().getTx()) * (Math.PI / 180.0)) - 3.54;
    }
}
