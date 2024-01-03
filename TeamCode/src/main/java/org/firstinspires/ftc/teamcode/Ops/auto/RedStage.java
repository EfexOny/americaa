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
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.teste.PropDetectionRedFar;
import org.firstinspires.ftc.vision.VisionPortal;

@Config
@Autonomous(name = "red stage")
@SuppressWarnings("unused")
public class RedStage extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;
    TrajectorySequence spikemark;
    TrajectorySequence backboard;
    TrajectorySequence park;
    Pose2d startRed = new Pose2d(-35, -60, Math.toRadians(90));
    PropDetectionRedFar red;
    VisionPortal portal;
    public Lift lift;
    public Cuva cuva;
    ElapsedTime timer = new ElapsedTime();

    boolean started=false,finished=false,follow=false;
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

        vbar.Close();

        drive.setPoseEstimate(startRed);

        while (opModeInInit() && !isStopRequested()) {
            if(!started){
                timer.reset();
                started=true;
            }
            if(timer.seconds() < 4) {
                switch (red.detection) {
                    case 1:
                        telemetry.addData("Detection", "stanga");
                        break;
                    case 2:
                        telemetry.addData("Detection", "middle");
                        break;
                    case 3:
                        telemetry.addData("Detection", "dreapta");
                        break;
                    default:
                        telemetry.addData("Detection", "Did not detect yet");
                }

                telemetry.addData("Right value", red.leftSum);
                telemetry.addData("Middle value", red.middleSum);
                telemetry.update();

                detect = red.detection;
            } else switch (detect) {
                case 1:
                    spikemark = drive.trajectorySequenceBuilder(startRed)
                            .lineToLinearHeading(new Pose2d(-35, -36, Math.toRadians(135)))
                            .build();
                    backboard = drive.trajectorySequenceBuilder(spikemark.end())
                            .lineToLinearHeading(new Pose2d(-35, -24, Math.toRadians(90)))
                            .splineTo(new Vector2d(12, -12), Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(48, -35, Math.toRadians(170)), Math.toRadians(0))
                            .build();
                    park = drive.trajectorySequenceBuilder(backboard.end())
                            .splineToConstantHeading(new Vector2d(60, -9), Math.toRadians(0))
                            .build();
                    break;
                case 2:
                    spikemark = drive.trajectorySequenceBuilder(startRed)
                            .lineToConstantHeading(new Vector2d(-35, -36))
                            .build();
                    backboard = drive.trajectorySequenceBuilder(spikemark.end())
                            .strafeTo(new Vector2d(-56, -32))
                            .forward(6)
                            .splineTo(new Vector2d(12, -12), Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(48, -35, Math.toRadians(170)), Math.toRadians(0))
                            .build();
                    park = drive.trajectorySequenceBuilder(backboard.end())
                            .splineToConstantHeading(new Vector2d(60, -9), Math.toRadians(0))
                            .build();
                    break;
                case 3:
                    spikemark = drive.trajectorySequenceBuilder(startRed)
                            .lineToLinearHeading(new Pose2d(-35, -36, Math.toRadians(30)))
                            .build();
                    backboard = drive.trajectorySequenceBuilder(spikemark.end())
                            .lineToLinearHeading(new Pose2d(-35, -24, Math.toRadians(90)))
                            .splineTo(new Vector2d(24, -12), Math.toRadians(0))
                            .splineToLinearHeading(new Pose2d(48, -35, Math.toRadians(170)), Math.toRadians(0))
                            .build();
                    park = drive.trajectorySequenceBuilder(backboard.end())
                            .splineToConstantHeading(new Vector2d(60, -9), Math.toRadians(0))
                            .build();
                    break;
            }

            waitForStart();

            schedule(
                    new SequentialCommandGroup(
                            vbar.Vbar_Idle(),
                            cuva.close(),
                            new FollowTrajectoryCommand(spikemark, drive),
                            new DelayedCommand(vbar.vbarjos(),200).andThen(vbar.Open()),
                            new DelayedCommand(vbar.Vbar_Idle(), 100),
                            new FollowTrajectoryCommand(backboard, drive),
                            new BackDropCommand(lift,cuva),
                            new WaitCommand(1000),
                            new FollowTrajectoryCommand(park, drive),
                            new InstantCommand(() -> finished=true)
                    )
            );
        }
    }
    @Override
    public void run() {
        super.run();
    }
}


