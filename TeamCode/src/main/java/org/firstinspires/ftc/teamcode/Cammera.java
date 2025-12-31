package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp
public class Cammera extends LinearOpMode {

    private VisionPortal myVisionPortal;
    private AprilTagProcessor myAprilTagProcessor;

    @Override
    public void runOpMode() {

        AprilTagLibrary myAprilTagLibrary = AprilTagGameDatabase.getDecodeTagLibrary();

        myAprilTagProcessor = new AprilTagProcessor.Builder()
                .setTagLibrary(myAprilTagLibrary)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .build();

        myVisionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(myAprilTagProcessor)
                .build();

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("AprilTags Detected", myAprilTagProcessor.getDetections().size());
            telemetry.update();
        }

        if (myVisionPortal != null) {
            myVisionPortal.close();
        }
    }
}
