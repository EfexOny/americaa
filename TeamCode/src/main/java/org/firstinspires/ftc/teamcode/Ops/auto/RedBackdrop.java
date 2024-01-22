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


        drive.setPoseEstimate(startRed);



        backboard1 = drive.trajectorySequenceBuilder(startRed)
                .splineToConstantHeading(new Vector2d(33, -41), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(57, -44, Math.toRadians(163)), Math.toRadians(0))
                .build();

        spikemark1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineToLinearHeading(new Pose2d(43, -24, Math.toRadians(173)))
                .build();
        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(46,-60,Math.toRadians(160)),Math.toRadians(0))
                .build();

        backboard2 = drive.trajectorySequenceBuilder(startRed)
                .splineToConstantHeading(new Vector2d(33, -41), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(57, -37, Math.toRadians(163)), Math.toRadians(0))
                .build();

        spikemark2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineToLinearHeading(new Pose2d(25.6, -29.5, Math.toRadians(125)))
                .build();

        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(48,-65,Math.toRadians(163)),Math.toRadians(0))
                .build();

        backboard3 = drive.trajectorySequenceBuilder(startRed)
                .splineToConstantHeading(new Vector2d(33, -41), Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(57, -31, Math.toRadians(163)), Math.toRadians(0))
                .build();

        spikemark3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineToLinearHeading(new Pose2d(14.5, -34, Math.toRadians(130)))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(46,-64,Math.toRadians(160)),Math.toRadians(0))
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
                        new WaitCommand(1000),
                        vbar.Vbar_Idle(),
                        cuva.close(),
                        new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true),
//                        new FollowTrajectoryCommand(backboard, drive),
                        new BackDropCommand(lift,cuva),
                        new WaitCommand(1000),
                        vbar.vbarjos(),
//                        new FollowTrajectoryCommand(spikemark,drive),
                        new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true),
                        new DelayedCommand(vbar.Open(),650),
                        new DelayedCommand(vbar.Vbar_Idle(),1000),
                        new WaitCommand(1000),
                        new SpikeMarkCommand(drive,parkare1,parkare2,parkare3,detect,true)
//                        new FollowTrajectoryCommand(parkare,drive)
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


