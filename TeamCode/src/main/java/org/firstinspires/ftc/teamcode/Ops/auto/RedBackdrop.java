package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.outoftheboxrobotics.photoncore.Photon;
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
@Photon
@Autonomous(name = "red backdrop")
@SuppressWarnings("unused")
public class RedBackdrop extends CommandOpMode {

    public static SampleMecanumDrive drive;
    Virtualbar vbar;

    TrajectorySequence parkare1,spikemark1,backboard1,parkare2,parkare3,spikemark2,spikemark3,backboard3,backboard2;

    Pose2d startRed = new Pose2d(12, -60, Math.toRadians(90));
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



        backboard3 = drive.trajectorySequenceBuilder(startRed)
                .splineToLinearHeading(new Pose2d(51,-40,Math.toRadians(180)),Math.toRadians(0))
                .build();
        spikemark3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineToLinearHeading(new Pose2d(39, -27,Math.toRadians(180)))
                .build();
        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .setTangent(Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(38,-44,Math.toRadians(180)))
                .splineToLinearHeading(new Pose2d(57,-59,Math.toRadians(180)),Math.toRadians(0))
                .build();
        backboard2 = drive.trajectorySequenceBuilder(startRed)
                .splineToLinearHeading(new Pose2d(51,-35,Math.toRadians(180)),Math.toRadians(0))
                .build();
        spikemark2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineToLinearHeading(new Pose2d(31, -19,Math.toRadians(180)))
                .build();
        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .setTangent(Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(38,-46,Math.toRadians(180)))
                .splineToLinearHeading(new Pose2d(57,-62d,Math.toRadians(180)),Math.toRadians(0))
                .build();


        backboard1 = drive.trajectorySequenceBuilder(startRed)
                .splineToLinearHeading(new Pose2d(52.5,-28.5,Math.toRadians(180)),Math.toRadians(0))
                .build();
        spikemark1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineToLinearHeading(new Pose2d(18,-26,Math.toRadians(180)))
                .build();
        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .setTangent(Math.toRadians(270))
                .lineToLinearHeading(new Pose2d(38,-45,Math.toRadians(180)))
                .splineToLinearHeading(new Pose2d(57,-62,Math.toRadians(180)),Math.toRadians(0))
                .build();

        boolean trajectoriesCreated = false;

        while (opModeInInit() && !isStopRequested()) {
//            detect = red.detection;
            detect = 2;
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
                            new WaitCommand(1000),
                            cuva.close(),
                            new WaitCommand(500),
                            new SpikeMarkCommand(drive,backboard1,backboard2,backboard3,detect,true).alongWith( cuva.mereuta(380) ),
                            cuva.open(),
                            new WaitCommand(500),
                            new SpikeMarkCommand(drive,spikemark1,spikemark2,spikemark3,detect,true)
                                    .alongWith(cuva.close(),
                                       cuva.afterparty(), new DelayedCommand(vbar.VJos(), 1000)),
                            new DelayedCommand(vbar.Open(),600 ).andThen(vbar.Vbar_Idle()).alongWith(cuva.open()),
                            new SpikeMarkCommand(drive,parkare1,parkare2,parkare3,detect,true)
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


