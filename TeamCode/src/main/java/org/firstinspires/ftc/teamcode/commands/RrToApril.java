package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.util.Angle;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class RrToApril extends CommandBase {

    boolean gata=false;
    private VisionPortal visionPortal;               // Used to manage the video source.
    private AprilTagProcessor aprilTag;
    private static final int DESIRED_TAG_ID = 0;     // Choose the tag you want to approach or set to -1 for ANY tag.
    private AprilTagDetection desiredTag = null;
    Pose2d target;
    SampleMecanumDrive drive;
    boolean targetFound     = false;    // Set to true when an AprilTag target is detected
    int april;


    @Override
    public void execute() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if ((detection.metadata != null)
                    && ((DESIRED_TAG_ID >= 0) || (detection.id == DESIRED_TAG_ID))  ){
                targetFound = true;
                desiredTag = detection;
                break;  // don't look any further.
            }
        }

        if (targetFound) {
            drive.setWeightedDrivePower(new Pose2d(desiredTag.ftcPose.range - 12.0,desiredTag.ftcPose.bearing));
        }

            drive.update();
    }

    public RrToApril(int April, HardwareMap hardwareMap){
        this.april = April;

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();

    }
    @Override
    public void end(boolean interrupted) {
        drive.setMotorPowers(0, 0, 0, 0);
    }

    double xyP =1;

    @Override
    public boolean isFinished() {
        return !drive.isBusy();
    }
}