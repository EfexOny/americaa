package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.other.DelayedCommand;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.BluePropThreshold;
import org.firstinspires.ftc.vision.VisionPortal;


@TeleOp(name = "testAuto")
public class Doamne extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    TrajectorySequence stanga;
    TrajectorySequence centru;
    //ElapsedTime timer = new ElapsedTime();
    TrajectorySequence dreapta;
    Pose2d startBlue = new Pose2d(-36, 60, Math.toRadians(270));
    Pose2d startRed = new Pose2d(-36, -60, Math.toRadians(90));
    BluePropThreshold blue;
    VisionPortal portal;

    boolean detected = false;

    @Override
    public void initialize() {

        blue = new BluePropThreshold();

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(blue)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();

        drive = new SampleMecanumDrive(hardwareMap);
        vbar = new Virtualbar(hardwareMap);

        vbar.Close();

        drive.setPoseEstimate(startBlue);

        stanga = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(-36, 40))
                .strafeTo(new Vector2d(-35, 40))
                .turn(Math.toRadians(58))
                .build();

        centru = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(24)
                .build();

        dreapta = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(-36, 42.5))
                .turn(Math.toRadians(-38))
                .build();

        schedule(
                new SequentialCommandGroup(
                        new FollowTrajectoryCommand(stanga),
                        vbar.Vbar_Idle(),
                        new DelayedCommand(vbar.Open(),400)
                )
        );
    }

    @Override
    public void run() {

        //telementry in run ce da display la treshold-ul de pe vision portal
        telemetry.addData(">", blue.getPropPosition());
        telemetry.update();


        super.run();

    }
}


