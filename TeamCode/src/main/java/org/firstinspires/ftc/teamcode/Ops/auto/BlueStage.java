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
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.BackDropCommand;
import org.firstinspires.ftc.teamcode.commands.DelayedCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.SpikeMarkCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionBlueFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous(name = "blue stage")
@SuppressWarnings("unused")
public class BlueStage extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    Pose2d startBlue = new Pose2d(-35, 60, Math.toRadians(270));
    PropDetectionBlueFar blue;
    TrajectorySequence backboard1,backboard2,backboard3,spikemark1,spikemark2,spikemark3,park1,park2,park3;

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


        spikemark1 = drive.trajectorySequenceBuilder(startBlue)
                .lineToLinearHeading(new Pose2d(-36, 36, Math.toRadians(330)))
                .build();
        backboard1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .turn(Math.toRadians(-70))
                .lineToLinearHeading(new Pose2d(-35, 8, Math.toRadians(270)))
                .turn(Math.toRadians(90))
                .lineTo(new Vector2d(16,8))
                .splineToLinearHeading(new Pose2d(54, 28, Math.toRadians(170)), Math.toRadians(0))
                .build();
        park1 = drive.trajectorySequenceBuilder(backboard1.end())
                .splineToConstantHeading(new Vector2d(60, 11), Math.toRadians(0))
                .build();

        spikemark2 = drive.trajectorySequenceBuilder(startBlue)
                .lineToConstantHeading(new Vector2d(-35, 36))
                .build();
        backboard2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .strafeTo(new Vector2d(-56, 32))
                .forward(6)
                .splineTo(new Vector2d(12, 11), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(54, 35, Math.toRadians(170)), Math.toRadians(0))
                .build();
        park2 = drive.trajectorySequenceBuilder(backboard2.end())
                .splineToConstantHeading(new Vector2d(60, 11), Math.toRadians(0))
                .build();

        spikemark3 = drive.trajectorySequenceBuilder(startBlue)
                .lineToLinearHeading(new Pose2d(-34, 36, Math.toRadians(240)))
                .build();
        backboard3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .turn(Math.toRadians(30))
                .lineToLinearHeading(new Pose2d(-32, 23, Math.toRadians(270)))
                .splineTo(new Vector2d(24, 12), Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(52, 34, Math.toRadians(190)), Math.toRadians(0))
                .build();
        park3 = drive.trajectorySequenceBuilder(backboard3.end())

                .lineTo(new Vector2d(50, 20))
                .lineToLinearHeading(new Pose2d(65, 20, Math.toRadians(200)))
                .build();

        drive.setPoseEstimate(startBlue);
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
                            vbar.Close(),
                            vbar.Vbar_Idle(),
                            new WaitCommand(1000),
                            cuva.close(),
                            new WaitCommand(1000),
                            vbar.vbarjos(),
//                            new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true),
                            new FollowTrajectoryCommand(spikemark1,drive),
                            new DelayedCommand(vbar.Open(),650),
                            new DelayedCommand(vbar.Vbar_Idle(),1000),
                            new WaitCommand(1000),
//                            new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true),
                            new FollowTrajectoryCommand(backboard1, drive),
                            new BackDropCommand(lift,cuva),
                            new WaitCommand(1000),
//                            new SpikeMarkCommand(drive,park1,park2,park3,detect,true)
                            new FollowTrajectoryCommand(park2, drive)
                    )
            );
        }
    @Override
    public void run() {
        super.run();
    }
}


