package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.Ops.Pose;
import org.firstinspires.ftc.teamcode.Ops.Pose;
import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.DelayedCommand;
import org.firstinspires.ftc.teamcode.commands.SpikeMarkCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionBlueFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Photon
@Autonomous(name = "blue backdrop")
@SuppressWarnings("unused")
public class BlueBackdrop extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    TrajectorySequence backboard;
    TrajectorySequence parkare;

    TrajectorySequence backboard1,backboard2,backboard3,spikemark1,spikemark2,spikemark3,parkare1,parkare2,parkare3;


    TrajectorySequence spikemark,parkare4,spikemark4,backboard4;
    Pose2d startBlue = new Pose2d(11, 60, Math.toRadians(270));
    PropDetectionBlueFar blue;
    VisionPortal portal;
    public Lift lift;
    public Cuva cuva;
    ElapsedTime timer = new ElapsedTime();

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

        vbar.Close().schedule();


        drive.setPoseEstimate(startBlue);



        backboard1 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(52,38,Math.toRadians(185)),Math.toRadians(0))
                .build();
        spikemark1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineToLinearHeading(new Pose2d(39, 27,Math.toRadians(180)))
                .build();
        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .setTangent(Math.toRadians(90))
                .lineToLinearHeading(new Pose2d(38,45,Math.toRadians(185)))
                .splineToLinearHeading(new Pose2d(59,63,Math.toRadians(185)),Math.toRadians(0))
                .build();


        backboard2 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(52,32,Math.toRadians(185)),Math.toRadians(0))
                .build();
        spikemark2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineToLinearHeading(new Pose2d(31, 19,Math.toRadians(180)))
                .build();
        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .setTangent(Math.toRadians(90))
                .lineToLinearHeading(new Pose2d(38,45,Math.toRadians(185)))
                .splineToLinearHeading(new Pose2d(59,63,Math.toRadians(185)),Math.toRadians(0))
                .build();


        backboard3 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(52,28,Math.toRadians(185)),Math.toRadians(0))
                .build();
        spikemark3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineToLinearHeading(new Pose2d(16.5,26,Math.toRadians(180)))
                .build();
        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .setTangent(Math.toRadians(90))
                .lineToLinearHeading(new Pose2d(38,45,Math.toRadians(185)))
                .splineToLinearHeading(new Pose2d(59,63,Math.toRadians(185)),Math.toRadians(0))
                .build();



        while (opModeInInit() && !isStopRequested()) {
            detect = blue.detection;
//            detect = 3;
            telemetry.addData("Detection", detect);
            telemetry.addData("Right value", blue.rightSum);
            telemetry.addData("Middle value", blue.middleSum);
            telemetry.update();
        }

        waitForStart();

        schedule(
                new SequentialCommandGroup(
                        vbar.Close(),
                        vbar.Vbar_Idle(),
                        new WaitCommand(1000),
                        cuva.close(),
                        new WaitCommand(500),
                        new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true).alongWith( cuva.mereuta(0) ),
                        cuva.openCustom(detect),
                        new WaitCommand(500),
                        new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true)
                                .alongWith(vbar.VJos(),cuva.close(),
                                   cuva.afterparty()),
                        new DelayedCommand(vbar.Open(),600 ).andThen(vbar.Vbar_Idle()).alongWith(cuva.open()),
                        new SpikeMarkCommand(drive,parkare1,parkare2,parkare3,detect,true)
                )
        );
    }
    @Override
    public void run() {
        super.run();
        telemetry.addData("caz",detect);
        telemetry.addData("leftTarget: ", lift.getTciks());
        telemetry.update();
    }
}