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
    Pose2d startBlue = new Pose2d(11, 60, Math.toRadians(270));
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


        backboard1 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(55.5,42,Math.toRadians(195)),Math.toRadians(0))
                .build();

        spikemark1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineToLinearHeading(new Pose2d(44, 25.5,Math.toRadians(195)))
                .build();

        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .strafeTo(new Vector2d(37,50))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(58,64,Math.toRadians(180)),Math.toRadians(0))
                .build();

        backboard2 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(5639,Math.toRadians(195)),Math.toRadians(0))
                .build();
        spikemark2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineToLinearHeading(new Pose2d(37, 14,Math.toRadians(195)))
                .build();

        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .strafeTo(new Vector2d(37,50))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(59,65,Math.toRadians(180)),Math.toRadians(0))
                .build();


        backboard3 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(63,33.5,Math.toRadians(200)),Math.toRadians(0))
                .build();

        spikemark3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineToLinearHeading(new Pose2d(18,22,Math.toRadians(195)))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .strafeTo(new Vector2d(37,57))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(58,63,Math.toRadians(180)),Math.toRadians(0))
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

                            new SequentialCommandGroup(
                                    vbar.Close(),
                                    vbar.Vbar_Idle(),
                                    new WaitCommand(1000),
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
//

//                            new BackDropCommand(lift,cuva),
//                            new WaitCommand(1000),
//                            new SpikeMarkCommand(drive,parkare2,parkare2,parkare2,detect,true)

//                            vbar.Close(),
//                            vbar.Vbar_Idle(),
//                            new WaitCommand(1000),
//                            cuva.close(),
//                            new WaitCommand(1000),
//                            vbar.vbarjos(),
//                            new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true),
//                            new DelayedCommand(vbar.Open(),650),
//                            new DelayedCommand(vbar.Vbar_Idle(),1000),
//                            new WaitCommand(1000),
////                            new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true),
//                            new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true),
//                            new BackDropCommand(lift,cuva),
//                            new WaitCommand(1000),
////                            new SpikeMarkCommand(drive,park1,park2,park3,detect,true)
//                            new SpikeMarkCommand(drive,park1,park2,park3,detect,true)
                    )
                )
            );
        }
    @Override
    public void run() {
        super.run();
    }
}


