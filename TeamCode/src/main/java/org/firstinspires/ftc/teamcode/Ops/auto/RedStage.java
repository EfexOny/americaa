package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Ops.Creier;
//import org.firstinspires.ftc.teamcode.Ops.Pose;
import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.BackDropCommand;
import org.firstinspires.ftc.teamcode.commands.DelayedCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.SpikeMarkCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionBlueFar;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionRedFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous(name = "red stage")
@SuppressWarnings("unused")
public class RedStage extends Creier {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    Pose2d startRed = new Pose2d(-35, -60, Math.toRadians(90));
    PropDetectionRedFar red;
    TrajectorySequence backboard1,backboard2,backboard3,spikemark1,spikemark2,spikemark3,parkare1,parkare2,parkare3, stack1, stack2, stack3;

    VisionPortal portal;
    public Lift lift;
    public Cuva cuva;
    ElapsedTime timer = new ElapsedTime();

    boolean started=false,finished=false,follow=false;
    int  detect=2;


    @Override
    public void initialize() {

        initHardware();
        super.initialize();

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



        spikemark1 = drive.trajectorySequenceBuilder(startRed)
                .lineToLinearHeading(new Pose2d(-40,-39, Math.toRadians(30)))
                .build();


        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .lineToLinearHeading(new Pose2d(-35, -56, Math.toRadians(90)))
                .build();



        spikemark2 = drive.trajectorySequenceBuilder(startRed)
                .lineToConstantHeading(new Vector2d(-35,-37))
                .build();

        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .lineToLinearHeading(new Pose2d(-35, -59, Math.toRadians(90)))
                .build();


        spikemark3 = drive.trajectorySequenceBuilder(startRed)
//                .setTangent(Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(-35,-42, Math.toRadians(125)))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .lineToLinearHeading(new Pose2d(-35, -59, Math.toRadians(90)))
                .build();

        drive.setPoseEstimate(startRed);
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
                        cuva.open(),
                        new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true)
                                .alongWith(vbar.VJos()),
                        new DelayedCommand(vbar.Open(),100).alongWith(new DelayedCommand(vbar.Vbar_Idle(), 100)),
                        new SpikeMarkCommand(drive,parkare1,parkare2,parkare3,detect,true)
//                        new InstantCommand(()-> Pose.currentPose = drive.getPoseEstimate())


                )
        );
    }
    @Override
    public void run() {
        super.run();
    }
}


