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
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.BackDropCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.DelayedCommand;
import org.firstinspires.ftc.teamcode.commands.SpikeMarkCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionBlueFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
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

    boolean started=false;

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
                .splineToLinearHeading(new Pose2d(55,43,Math.toRadians(195)),Math.toRadians(0))
                .build();
        spikemark1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineToLinearHeading(new Pose2d(44.5, 27,Math.toRadians(195)))
                .build();

        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .strafeTo(new Vector2d(37,50))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(57.5,62,Math.toRadians(180)),Math.toRadians(0))
                .build();


        backboard2 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(55.3,40,Math.toRadians(195)),Math.toRadians(0))
                .build();
        spikemark2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineToLinearHeading(new Pose2d(37, 19,Math.toRadians(195)))
                .build();

        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .strafeTo(new Vector2d(37,50))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(59,63,Math.toRadians(195)),Math.toRadians(0))
                .build();


        backboard3 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(57,32,Math.toRadians(195)),Math.toRadians(0))
                .build();
        spikemark3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineToLinearHeading(new Pose2d(21,26,Math.toRadians(195)))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .strafeTo(new Vector2d(37,57))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(59,60,Math.toRadians(195)),Math.toRadians(0))
                .build();



        boolean trajectoriesCreated = false;
        while (opModeInInit() && !isStopRequested()) {
            detect = blue.detection;
            telemetry.addData("Detection", detect);
            telemetry.addData("Right value", blue.rightSum);
            telemetry.addData("Middle value", blue.middleSum);
            telemetry.update();
        }

        waitForStart();

        schedule(
                new SequentialCommandGroup(
                        vbar.Vbar_Idle(),
                        new WaitCommand(1000),
                        cuva.close(),
                        new WaitCommand(500),
                        new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true)
                                .alongWith(cuva.close(), new DelayedCommand(lift.goLift(500), 400),cuva.cuva_inapoi()),
                        cuva.open(),
                        new WaitCommand(500),
//                        cuva.close(),
//                        new WaitCommand(500),
//                        cuva.cuva_arunca(),
//                        new WaitCommand(1000),
//                        cuva.open(),
                        new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true)
                                .alongWith(vbar.vbarjos(), new DelayedCommand( lift.goLift(0), 300))
                                .alongWith(new SequentialCommandGroup(cuva.close(),
                                        new WaitCommand(500),
                                        cuva.cuva_arunca(),
                                        new WaitCommand(1000),
                                        cuva.open())),
                        new DelayedCommand(vbar.Open(),650).alongWith(vbar.Vbar_Idle()),
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