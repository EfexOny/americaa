package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.commands.BackDropCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.DelayedCommand;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionBlueFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous(name = "blue backdrop")
public class cefac extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    TrajectorySequence backboard;
    TrajectorySequence Park;
    Pose2d startBlue = new Pose2d(10, 60, Math.toRadians(270));
    PropDetectionBlueFar blue;
    VisionPortal portal;
    public Lift lift;
    public Cuva cuva;
    ElapsedTime timer = new ElapsedTime();

    boolean started=false,finished=false,follow=false;
    int  detect=2;

    @Override
    public void initialize() {



        blue = new PropDetectionBlueFar();

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

        drive.setPoseEstimate(startBlue);



// CENTRU
//        backboard = drive.trajectorySequenceBuilder(startBlue)
//                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(51.48,37,Math.toRadians(180)),Math.toRadians(0))
//                .build();
//
//        Park = drive.trajectorySequenceBuilder(backboard.end())
//                .lineTo(new Vector2d(35,14))
//                .build();
// CENTRU


// DREAPTA
//        backboard = drive.trajectorySequenceBuilder(startBlue)
//                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(50.4,33,Math.toRadians(180)),Math.toRadians(0))
//                .build();
//
//        Park = drive.trajectorySequenceBuilder(backboard.end())
//                .lineTo(new Vector2d(18,28))
//                .build();
// DREAPTA


//
//        backboard = drive.trajectorySequenceBuilder(startBlue)
//                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(49.8,40,Math.toRadians(180)),Math.toRadians(0))
//                .build();
//
//        Park = drive.trajectorySequenceBuilder(backboard.end())
//                .lineTo(new Vector2d(40,25.6))
//                .build();


        while (opModeInInit() && !isStopRequested()) {
            telemetry.addData("Detection", blue.detection);
            telemetry.addData("Left value", blue.leftSum);
            telemetry.addData("Niddle value", blue.middleSum);
            telemetry.update();

            if(!started){
                timer.reset();
                started=true;
            }
            if(timer.seconds() <4) {
                detect = blue.detection;
            }

            //pt ce e if-ul asta aici?
            finished = true;
            if(finished){
                finished=false;
                follow=true;
                switch (detect) {
                    case 1: {
                        backboard = drive.trajectorySequenceBuilder(startBlue)
                                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
                                .splineToLinearHeading(new Pose2d(50.4,40,Math.toRadians(180)),Math.toRadians(0))
                                .build();

                        Park = drive.trajectorySequenceBuilder(backboard.end())
                                .lineTo(new Vector2d(40,25.6))
                                .build();
                        break;
                    }

                    case 2: {
                        backboard = drive.trajectorySequenceBuilder(startBlue)
                                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
                                .splineToLinearHeading(new Pose2d(51.48,34,Math.toRadians(180)),Math.toRadians(0))
                                .build();

                        Park = drive.trajectorySequenceBuilder(backboard.end())
                                .lineTo(new Vector2d(35,14))
                                .build();
                        break;
                    }
                    case 3: {
                        backboard = drive.trajectorySequenceBuilder(startBlue)
                                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
                                .splineToLinearHeading(new Pose2d(50.4,33,Math.toRadians(180)),Math.toRadians(0))
                                .build();

                        Park = drive.trajectorySequenceBuilder(backboard.end())
                                .lineTo(new Vector2d(18,28))
                                .build();
                        break;
                    }
                }
            }


    }


        waitForStart();

        schedule(
                new SequentialCommandGroup(
                        vbar.Vbar_Idle(),
                        cuva.close(),
                        new FollowTrajectoryCommand(backboard, drive),

                        new BackDropCommand(lift,cuva),
                        new WaitCommand(1000),
                        new FollowTrajectoryCommand(Park,drive),
                        new DelayedCommand(vbar.vbarjos(),200).andThen(vbar.Open()),
                        new InstantCommand(() -> finished=true)
                )
        );
    }
    @Override
    public void run() {
        super.run();

//        if(!started){
//            timer.reset();
//            started=true;
//        }
//        if(timer.seconds() <4) {
//            detect = blue.detection;
//        }
//
//        if(finished){
//            finished=false;
//            follow=true;
//                switch (detect) {
//                    case 1: {
//                        backboard = drive.trajectorySequenceBuilder(startBlue)
//                                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
//                                .splineToLinearHeading(new Pose2d(50.4,40,Math.toRadians(180)),Math.toRadians(0))
//                                .build();
//
//                        Park = drive.trajectorySequenceBuilder(backboard.end())
//                                .lineTo(new Vector2d(40,25.6))
//                                .build();
//                    }
//
//                    case 2: {
//                        backboard = drive.trajectorySequenceBuilder(startBlue)
//                                .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
//                                .splineToLinearHeading(new Pose2d(51.48,34,Math.toRadians(180)),Math.toRadians(0))
//                                .build();
//
//                        Park = drive.trajectorySequenceBuilder(backboard.end())
//                                .lineTo(new Vector2d(35,14))
//                                .build();
//                    }
//                    case 3: {
//                                backboard = drive.trajectorySequenceBuilder(startBlue)
//                                        .splineToConstantHeading(new Vector2d(33,44),Math.toRadians(270))
//                                        .splineToLinearHeading(new Pose2d(50.4,33,Math.toRadians(180)),Math.toRadians(0))
//                                        .build();
//
//                                Park = drive.trajectorySequenceBuilder(backboard.end())
//                                        .lineTo(new Vector2d(18,28))
//                                        .build();
//                    }
//                }
//            }
//        }
    }
    }


