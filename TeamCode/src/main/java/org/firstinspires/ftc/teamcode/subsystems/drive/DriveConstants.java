package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DriveConstants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            //Setup
            .mass(13.0)
            .forwardZeroPowerAcceleration(-45.98)
            .lateralZeroPowerAcceleration(-64.33)
            //TranslationPIDF
            .useSecondaryTranslationalPIDF(true)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.07,0,0.005,0.02))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.1,0,0.01,0))
            //HeadingPIDF
            .useSecondaryHeadingPIDF(true)
            .headingPIDFCoefficients(new PIDFCoefficients(1,0,0.01,0.02))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.1,0,0.05,0))
            //DrivePIDF
            .useSecondaryDrivePIDF(true)
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.05,0,0.0006 ,0.6,0.01))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.01,0,0.0001,0.6,0.0001))
            //Centripetal
            .centripetalScaling(0.0005);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-1.88976377952756)
            .strafePodX(-3.34645669291339)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .leftFrontMotorName("lf")
            .leftRearMotorName("lr")
            .rightFrontMotorName("rf")
            .rightRearMotorName("rr")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .xVelocity(58.73)
            .yVelocity(42.14);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 0.8, 1);
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}
