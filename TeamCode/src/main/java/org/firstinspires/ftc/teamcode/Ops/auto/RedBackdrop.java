package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
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
@Autonomous(name = "red backdrop")
@SuppressWarnings("unused")
public class RedBackdrop extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;

    TrajectorySequence parkare1,spikemark1,backboard1,parkare2,parkare3,spikemark2,spikemark3,backboard3,backboard2;

    Pose2d startRed = new Pose2d(10, -60, Math.toRadians(90));
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




        backboard1 = drive.trajectorySequenceBuilder(startRed)
                .splineToLinearHeading(new Pose2d(52,-43,Math.toRadians(160)),Math.toRadians(0))
                .build();
        spikemark1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineToLinearHeading(new Pose2d(45.5, -23,Math.toRadians(160)))
                .build();

        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .setTangent(Math.toRadians(90))
                .lineToLinearHeading(new Pose2d(38,-45,Math.toRadians(165)))
                .splineToLinearHeading(new Pose2d(59,-63,Math.toRadians(165)),Math.toRadians(0))
                .build();


        backboard2 = drive.trajectorySequenceBuilder(startRed)
                .splineToLinearHeading(new Pose2d(53,-36.5,Math.toRadians(168)),Math.toRadians(0))
                .build();
        spikemark2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineToLinearHeading(new Pose2d(37, -15,Math.toRadians(168)))
                .build();

        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .setTangent(Math.toRadians(90))
                .lineToLinearHeading(new Pose2d(38,-45,Math.toRadians(165)))
                .splineToLinearHeading(new Pose2d(59,-63,Math.toRadians(165)),Math.toRadians(0))
                .build();


        backboard3 = drive.trajectorySequenceBuilder(startRed)
                .splineToLinearHeading(new Pose2d(55,-30,Math.toRadians(159)),Math.toRadians(0))
                .build();
        spikemark3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineToLinearHeading(new Pose2d(19,-26,Math.toRadians(159)))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .setTangent(Math.toRadians(90))
                .lineToLinearHeading(new Pose2d(38,-45,Math.toRadians(165)))
                .splineToLinearHeading(new Pose2d(59,-63,Math.toRadians(165)),Math.toRadians(0))
                .build();

        boolean trajectoriesCreated = false;

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
                                vbar.Vbar_Idle(),
                                new WaitCommand(800),
                                cuva.close(),
                                new WaitCommand(500),
                                new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true)
                                        .alongWith(cuva.close(), new DelayedCommand(lift.goLift(500), 400),cuva.cuva_inapoi()
                                                ),
                                cuva.open(),
                                new WaitCommand(850),
                                cuva.close(),
                                new WaitCommand(500),
                                cuva.cuva_arunca(),
                                new WaitCommand(1000),
                                cuva.open(),
                                new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true)
                                        .alongWith(vbar.vbarjos(),cuva.close(),cuva.cuva_inapoi()),
                                new DelayedCommand(vbar.Open(),1600).andThen(vbar.Vbar_Idle()).alongWith(cuva.open()),
                                new SpikeMarkCommand(drive,parkare1,parkare2,parkare3,detect,true).alongWith(lift.goLift(0)
                                )
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


