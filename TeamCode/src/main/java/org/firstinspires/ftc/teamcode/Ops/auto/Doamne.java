package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
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
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionRedFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous(name = "red backdrop")
public class Doamne extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    TrajectorySequence backboard;
    TrajectorySequence spikemark;
    Pose2d startRed = new Pose2d(10, -60, Math.toRadians(90));
    PropDetectionRedFar red;
    VisionPortal portal;
    public Lift lift;
    public Cuva cuva;
    ElapsedTime timer = new ElapsedTime();

    boolean started = false;
    int  detect = 2;

    @Override
    public void initialize() {

        red = new PropDetectionRedFar();

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(red)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();



        drive = new SampleMecanumDrive(hardwareMap);
        vbar = new Virtualbar(hardwareMap);
        cuva = new Cuva(hardwareMap);
        lift = new Lift(hardwareMap);

        vbar.CloseDirect();

        drive.setPoseEstimate(startRed);


//        dreapta
//                backboard = drive.trajectorySequenceBuilder(startRed)
//                .splineToConstantHeading(new Vector2d(32,-44),Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(48,-40,Math.toRadians(170)),Math.toRadians(0))
//                .build();
//
//        Park = drive.trajectorySequenceBuilder(backboard.end())
//                .lineTo(new Vector2d(39.5,-23.2 ))
//                .build();
//        dreapta


//        centru
//                backboard = drive.trajectorySequenceBuilder(startRed)
//                .splineToConstantHeading(new Vector2d(32,-44),Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(48,-35,Math.toRadians(170)),Math.toRadians(0))
//                .build();
//
//        Park = drive.trajectorySequenceBuilder(backboard.end())
//                .lineTo(new Vector2d(35,-22))
//                .build();
//        centru


//        stanga
//          backboard = drive.trajectorySequenceBuilder(startRed)
//                .splineToConstantHeading(new Vector2d(32,-44),Math.toRadians(270))
//                .splineToLinearHeading(new Pose2d(48,-28.5,Math.toRadians(170)),Math.toRadians(0))
//                .build();
//
//        Park = drive.trajectorySequenceBuilder(backboard.end())
//                .lineTo(new Vector2d(14.12,-27 ))
//                .build();
//        stanga


        while (opModeInInit() && !isStopRequested()) {

            if(!started){
                timer.reset();
                started=true;
            }

            if (timer.seconds() < 4 && started) {
                switch (red.detection) {
                    case 1:
                        telemetry.addData("Detection", "stanga");
                        break;
                    case 2:
                        telemetry.addData("Detection", "middle");
                        break;
                    case 3:
                        telemetry.addData("Detection", "dreapta");
                        break;
                    default:
                        telemetry.addData("Detection", "Did not detect yet");
                }

                telemetry.addData("Right value", red.rightSum);
                telemetry.addData("Middle value", red.middleSum);
                telemetry.update();

                detect = red.detection;
            } else
            switch (detect) {
                case 1:
                    backboard = drive.trajectorySequenceBuilder(startRed)
                            .splineToConstantHeading(new Vector2d(32,-44),Math.toRadians(270))
                            .splineToLinearHeading(new Pose2d(48,-28.5,Math.toRadians(170)),Math.toRadians(0))
                            .build();

                    spikemark = drive.trajectorySequenceBuilder(backboard.end())
                            .lineTo(new Vector2d(14.12,-27 ))
                            .build();
                    break;
                case 2:

                    backboard = drive.trajectorySequenceBuilder(startRed)
                            .splineToConstantHeading(new Vector2d(32,-44),Math.toRadians(270))
                            .splineToLinearHeading(new Pose2d(48,-35,Math.toRadians(170)),Math.toRadians(0))
                            .build();

                    spikemark = drive.trajectorySequenceBuilder(backboard.end())
                            .lineTo(new Vector2d(33,-22))
                            .build();

                    break;
                case 3:
                    backboard = drive.trajectorySequenceBuilder(startRed)
                            .splineToConstantHeading(new Vector2d(32,-44),Math.toRadians(270))
                            .splineToLinearHeading(new Pose2d(48,-40,Math.toRadians(170)),Math.toRadians(0))
                            .build();

                    spikemark = drive.trajectorySequenceBuilder(backboard.end())
                            .lineTo(new Vector2d(39.5,-23.2 ))
                            .build();
                break;
            }

            waitForStart();

            schedule(
                    new SequentialCommandGroup(
                            vbar.Vbar_Idle(),
                            cuva.close(),
                            new FollowTrajectoryCommand(backboard, drive),
    //                        new BackDropCommand(lift,cuva),
                            new WaitCommand(1000),
                            new FollowTrajectoryCommand(spikemark,drive),
                            new DelayedCommand(vbar.vbarjos(),200).andThen(vbar.Open())
                    )
            );
        }
    }
    @Override
    public void run() {
        super.run();

    }
}


