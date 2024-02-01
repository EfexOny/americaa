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
    TrajectorySequence backboard1,backboard2,backboard3,spikemark1,spikemark2,spikemark3,parkare1,parkare2,parkare3, stack1, stack2, stack3, pixel1, pixel2, pixel3;

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

        vbar.Close().schedule();


        spikemark1 = drive.trajectorySequenceBuilder(startBlue)
                .lineToLinearHeading(new Pose2d(-38,39, Math.toRadians(330)))
                .build();

        stack1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-56, 12, Math.toRadians(180)), Math.toRadians(180))
                .build();

        backboard1 = drive.trajectorySequenceBuilder(spikemark1.end())

                .lineToLinearHeading(new Pose2d(-52,4, Math.toRadians(155)))
                .lineToConstantHeading(new Vector2d(35, 4))
                .splineToConstantHeading(new Vector2d(60,39),Math.toRadians(0))

                .build();
        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .lineToLinearHeading(new Pose2d(-35, 56, Math.toRadians(270)))
                .build();


        spikemark2 = drive.trajectorySequenceBuilder(startBlue)
                .lineToConstantHeading(new Vector2d(-35,37))
                .build();

        stack2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .lineToLinearHeading(new Pose2d(-46, 37, Math.toRadians(180)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-56, 12, Math.toRadians(180)), Math.toRadians(180))
                .build();



        backboard2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .lineToConstantHeading(new Vector2d(24, 12))
                .splineToConstantHeading(new Vector2d(53, 29), Math.toRadians(0))
                .build();


        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .lineToLinearHeading(new Pose2d(-35, 59, Math.toRadians(270)))
                .build();
        spikemark3 = drive.trajectorySequenceBuilder(startBlue)
                .lineToLinearHeading(new Pose2d(-35,42, Math.toRadians(235)))
                .build();

        stack3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .lineToLinearHeading(new Pose2d(-30, 34, Math.toRadians(180)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-44.8, 5.5, Math.toRadians(195)), Math.toRadians(180))
                .build();
        pixel3 = drive.trajectorySequenceBuilder(stack3.end())
                .lineToLinearHeading(new Pose2d(-30, 6.5, Math.toRadians(195)))
                .build();

        backboard3 = drive.trajectorySequenceBuilder(stack3.end())
                .lineToConstantHeading(new Vector2d(24, 12))
                .splineToConstantHeading(new Vector2d(57, 32), Math.toRadians(0))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .lineToLinearHeading(new Pose2d(-35, 59, Math.toRadians(270)))
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
                            cuva.close(),
                            new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true)
                                    .alongWith(vbar.VJos()),
                            new DelayedCommand(vbar.Open(),100).alongWith(new DelayedCommand(vbar.Vbar_Idle(), 100)),
                            new SpikeMarkCommand(drive,stack1,stack2,stack3,detect,true),
                            new WaitCommand(500),
                            new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true)
                                    .alongWith(cuva.close(), new DelayedCommand(lift.goLift(500), 6500),cuva.cuva_inapoi()),
                            cuva.open(),
                            new WaitCommand(500)




                    )
            );
        }
    @Override
    public void run() {
        super.run();
    }
}


