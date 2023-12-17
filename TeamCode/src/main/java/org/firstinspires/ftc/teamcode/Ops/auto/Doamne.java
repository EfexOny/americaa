package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.DelayedCommand;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.BluePropThreshold;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name = "red front auto")
public class Doamne extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    TrajectorySequence stanga;
    TrajectorySequence backboard;
    TrajectorySequence centru;
    //ElapsedTime timer = new ElapsedTime();
    TrajectorySequence dreapta;
    TrajectorySequence parkare;
    Pose2d startBlue = new Pose2d(-36, 60, Math.toRadians(270));
    Pose2d startRed = new Pose2d(10, -60, Math.toRadians(180));
    BluePropThreshold blue;
    VisionPortal portal;
    public Lift lift;
    public Cuva cuva;
    int SPIKE;
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
        cuva = new Cuva(hardwareMap);
        lift = new Lift(hardwareMap);

        vbar.Close();

        drive.setPoseEstimate(startRed);

        backboard = drive.trajectorySequenceBuilder(startRed)
                .strafeTo(new Vector2d(-44,-12))
//                .strafeRight(15)
                .lineTo(new Vector2d(24,-12))
                .splineToConstantHeading(new Vector2d(55,-48 ),Math.toRadians(0))
                .build();

        parkare = drive.trajectorySequenceBuilder(startRed)
                .lineTo(new Vector2d(10,-59))
                .splineToConstantHeading(new Vector2d(40,-34),Math.toRadians(90))
                .build();


//        stanga = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
//                .lineTo(new Vector2d(-36, 40))
//                .strafeTo(new Vector2d(-35, 40))
//                .turn(Math.toRadians(58))
//                .build();
//
//        centru = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
//                .forward(24)
//                .build();
//
//        dreapta = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
//                .lineTo(new Vector2d(-36, 42.5))
//                .turn(Math.toRadians(-38))
//                .build();

        waitForStart();

//        schedule(
//                new SequentialCommandGroup(
//                        vbar.Vbar_Idle(),
//                        new DelayedCommand(cuva.close(),300),
//                        new FollowTrajectoryCommand(parkare),
//                        lift.goLift(900),
//                        new WaitCommand(1000),
//                        cuva.cuva_inapoi(),
//                        new WaitCommand(500),
//                        cuva.open(),
//                        new WaitCommand(500),
//                        cuva.close(),
//                        new WaitCommand(500),
//                        cuva.cuva_arunca(),
//                        new WaitCommand(500),
//                        cuva.open(),
//                        new WaitCommand(500),
//                        lift.goLift(400),
//                        new WaitCommand(500),
//                        lift.goLift(0)
//
//                )
//        );
    }

    @Override
    public void run() {

        //telementry in run ce da display la treshold-ul de pe vision portal
        telemetry.addData(">", blue.getPropPosition());
        telemetry.update();

        super.run();

    }
}


