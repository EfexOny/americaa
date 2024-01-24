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
    TrajectorySequence backboard1,backboard2,backboard3,spikemark1,spikemark2,spikemark3,parkare1,parkare2,parkare3, stack1, stack2, stack3;

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
                .lineToLinearHeading(new Pose2d(-38,43, Math.toRadians(330)))
                .build();

        stack1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .lineToLinearHeading(new Pose2d(-35,40, Math.toRadians(180)))
                .build();

        backboard1 = drive.trajectorySequenceBuilder(spikemark1.end())

                .lineToLinearHeading(new Pose2d(-52,4, Math.toRadians(155)))
                .lineToConstantHeading(new Vector2d(35, 4))
                .splineToConstantHeading(new Vector2d(60,39),Math.toRadians(0))

                .build();
        parkare1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineToLinearHeading(new Pose2d(-35, 60, Math.toRadians(270)))
                .build();

        spikemark2 = drive.trajectorySequenceBuilder(startBlue)
                .lineToConstantHeading(new Vector2d(-35,37))
                .build();

        stack2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .lineToLinearHeading(new Pose2d(-35,40, Math.toRadians(180)))

                .build();

        backboard2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .strafeTo(new Vector2d(-53,38))
                .strafeTo(new Vector2d(-52,8))
                .setReversed(true)
                .setTangent(Math.toRadians(270))
                .lineToConstantHeading(new Vector2d(35, 8))
                .splineToConstantHeading(new Vector2d(60,39),Math.toRadians(0))
                .build();
        parkare2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineToLinearHeading(new Pose2d(-35, 60, Math.toRadians(270)))
                .build();
        spikemark3 = drive.trajectorySequenceBuilder(startBlue)
//                .setTangent(Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(-35,46, Math.toRadians(235)))
                .build();

        stack3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-65,39,Math.toRadians(180)),Math.toRadians(180))
                .build();

        backboard3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .lineTo(new Vector2d(-57,30 ))
                .setReversed(true)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(35,14,Math.toRadians(230)),Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(51,35),Math.toRadians(0))

                .build();
        parkare3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineToLinearHeading(new Pose2d(-35, 60, Math.toRadians(270)))
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
                            vbar.VJos(),
                            new WaitCommand(1000),
                            cuva.open(),
                            new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true)
                                    .alongWith(vbar.VJos()),
                            new DelayedCommand(vbar.Open(),180).alongWith(new DelayedCommand(vbar.Vbar_Idle(), 100))

                    )
            );
        }
    @Override
    public void run() {
        super.run();
    }
}


