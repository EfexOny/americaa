package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.Ops.Pose;
import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.commands.SpikeMarkCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.DelayedCommand;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionRedFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Photon
@Autonomous(name = "red stage")
@SuppressWarnings("unused")
public class RedStage extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;

    TrajectorySequence backboard1,backboard2,backboard3,spikemark1,spikemark2,spikemark3,parkare1,parkare2,parkare3, stack1, stack2, stack3, gate1, gate2, gate3;

    Pose2d startRed = new Pose2d(-33, -60, Math.toRadians(90));
    PropDetectionRedFar red;
    VisionPortal portal;
    public Lift lift;
    public Cuva cuva;
    ElapsedTime timer = new ElapsedTime();

    boolean started=false;
    int  detect=2;


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

        vbar.Close().schedule();


        drive.setPoseEstimate(startRed);

        spikemark1 = drive.trajectorySequenceBuilder(startRed)
                .lineToLinearHeading(new Pose2d(-39,-37, Math.toRadians(30)))
                .build();

        stack1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .lineToLinearHeading(new Pose2d(-45, -37, Math.toRadians(180)))
                .lineToLinearHeading(new Pose2d(-44.8, -10, Math.toRadians(180)))
                .build();
        gate1 = drive.trajectorySequenceBuilder(stack1.end())
                .lineToLinearHeading(new Pose2d(13,-10, Math.toRadians(180)))
                .build();

        backboard1 = drive.trajectorySequenceBuilder(gate1.end())
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(51, -36), Math.toRadians(0))
                .build();

        parkare1 = drive.trajectorySequenceBuilder(backboard1.end())
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(54,-7,Math.toRadians(185)),Math.toRadians(0))
                .lineToConstantHeading(new Vector2d(61, -8))
                .build();

        spikemark2 = drive.trajectorySequenceBuilder(startRed)
                .lineToConstantHeading(new Vector2d(-35,-39))
                .build();

        stack2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .lineToLinearHeading(new Pose2d(-47, -36, Math.toRadians(180)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-51, -12, Math.toRadians(180)), Math.toRadians(0))
                .build();

        gate2 = drive.trajectorySequenceBuilder(stack2.end())

                .lineToLinearHeading(new Pose2d(13,-12, Math.toRadians(180)))
                .build();

        backboard2 = drive.trajectorySequenceBuilder(gate2.end())

                .setTangent(0)
                .splineToConstantHeading(new Vector2d(51.5, -35), Math.toRadians(0))
                .build();

        parkare2 = drive.trajectorySequenceBuilder(backboard2.end())
////                .lineToLinearHeading(new Pose2d(50,-25,Math.toRadians(185)))
//                .setTangent(Math.toRadians(180))
//                .splineToLinearHeading(new Pose2d(57,-8,Math.toRadians(180)),Math.toRadians(0))
//
//
//                .lineToLinearHeading(new Pose2d(61, -18,Math.toRadians(180)))
//                .lineToLinearHeading(new Pose2d(61, -18,Math.toRadians(180)))
                .lineToConstantHeading(new Vector2d(41, -12))
                .strafeTo(new Vector2d(61, -12))
                .build();

        spikemark3 = drive.trajectorySequenceBuilder(startRed)
                .lineToLinearHeading(new Pose2d(-41,-45, Math.toRadians(100)))
                .build();

        stack3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .lineToLinearHeading(new Pose2d(-33, -37, Math.toRadians(180)))
                .lineToConstantHeading(new Vector2d(-33, -11))
                .build();

        gate3 = drive.trajectorySequenceBuilder(stack3.end())

                .lineToLinearHeading(new Pose2d(15,-11, Math.toRadians(180)))
                .build();

        backboard3 = drive.trajectorySequenceBuilder(gate3.end())
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(53, -30, Math.toRadians(180)), Math.toRadians(0))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(backboard3.end())

//                .setTangent(Math.toRadians(180))
//                .splineToLinearHeading(new Pose2d(57,-8,Math.toRadians(185)),Math.toRadians(0))
//                .lineToConstantHeading(new Vector2d(61, -8))
                .lineToConstantHeading(new Vector2d(41, -12))
                .lineToConstantHeading(new Vector2d(61, -12))
                .build();

        while (opModeInInit() && !isStopRequested()) {
            detect = red.detection;
            telemetry.addData("Detection", detect);
            telemetry.addData("Right value", red.leftSum);
            telemetry.addData("Middle value", red.middleSum);
            telemetry.update();
        }



        waitForStart();

        schedule(
                new SequentialCommandGroup(
                        vbar.Close(),
                        vbar.VJos(),
                        new WaitCommand(1000),
                        cuva.close(),
                        new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true).alongWith(vbar.VJos()),
                        vbar.Open().alongWith(new DelayedCommand(vbar.Vbar_Idle(), 395)),
                        new WaitCommand(100),
                        new SpikeMarkCommand(drive,stack1,stack2,stack3,detect,true),
                        new SpikeMarkCommand(drive,gate1,gate2,gate3,detect,true),
                        new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true).alongWith( cuva.mereuta(400) ),
                        cuva.open(),
                        new WaitCommand(500),
                        new SpikeMarkCommand(drive,parkare1,parkare2,parkare3,detect,true).alongWith( new DelayedCommand(cuva.afterparty(),200))
                )
        );
    }
    @Override
    public void run() {
        super.run();
        telemetry.addData("caz",detect);
        telemetry.update();

    }
}


