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

        drive.setPoseEstimate(startBlue);



        backboard1 = drive.trajectorySequenceBuilder(startBlue)
                .splineToConstantHeading(new Vector2d(33, 44), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(56.2, 44, Math.toRadians(190)), Math.toRadians(0))
                .build();
        spikemark1 = drive.trajectorySequenceBuilder(backboard1.end())
                .lineTo(new Vector2d(20, 35))
                .build();
        parkare1 = drive.trajectorySequenceBuilder(spikemark1.end())
                .lineToLinearHeading(new Pose2d(33, 53, Math.toRadians(180)))
                .lineTo(new Vector2d(60, 63))
                .build();


        backboard2 = drive.trajectorySequenceBuilder(startBlue)
                .splineToConstantHeading(new Vector2d(33, 41.5), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(57, 41, Math.toRadians(193)), Math.toRadians(0))
                .build();
        spikemark2 = drive.trajectorySequenceBuilder(backboard2.end())
                .lineTo(new Vector2d(34.5,12))
                .build();
        parkare2 = drive.trajectorySequenceBuilder(spikemark2.end())
                .lineToLinearHeading(new Pose2d(33, 53, Math.toRadians(180)))
                .lineTo(new Vector2d(60, 63))
                .build();


        backboard3 = drive.trajectorySequenceBuilder(startBlue)
                .splineToConstantHeading(new Vector2d(33, 44), Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(59.5, 35.4, Math.toRadians(197)), Math.toRadians(0))
                .build();
        spikemark3 = drive.trajectorySequenceBuilder(backboard3.end())
                .lineTo(new Vector2d(48.5,16))
                .build();

        parkare3 = drive.trajectorySequenceBuilder(spikemark3.end())
                .lineToLinearHeading(new Pose2d(33, 53, Math.toRadians(180)))
                .lineTo(new Vector2d(60, 63))
                .build();

        backboard4 = drive.trajectorySequenceBuilder(startBlue)
                .splineToLinearHeading(new Pose2d(51,35,Math.toRadians(180)),Math.toRadians(0))
                .build();
        spikemark4 = drive.trajectorySequenceBuilder(backboard4.end())
                .lineToConstantHeading(new Vector2d(18,24))
                .build();

        parkare4 = drive.trajectorySequenceBuilder(spikemark4.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(60,60,Math.toRadians(180)),Math.toRadians(0))
                .build();



        boolean trajectoriesCreated = false;
        while (opModeInInit() && !isStopRequested()) {
            detect = blue.detection;
            telemetry.addData("Detection", detect);
            telemetry.addData("Right value", blue.rightSum);
            telemetry.addData("Middle value", blue.middleSum);
            telemetry.update();
        }

            //
//
//            if(!started){
//                timer.reset();
//                started=true;
//            }
//            if(timer.seconds() < 3) {
//                detect = blue.detection;
//                telemetry.addData("Detection", detect);
//                telemetry.addData("Right value", blue.rightSum);
//                telemetry.addData("Middle value", blue.middleSum);
//                telemetry.addData("Seconds Left", 3 - timer.seconds());
//                telemetry.update();
//            } else {
//                telemetry.addData("Detection", "Complete");
//                telemetry.addData("Case", detect);
//
//                if (!trajectoriesCreated) {
//
//                    switch (detect) {
//                        case 3:
//                            backboard = drive.trajectorySequenceBuilder(startBlue)
//                                    .splineToConstantHeading(new Vector2d(33, 44), Math.toRadians(270))
//                                    .splineToLinearHeading(new Pose2d(58.5, 32, Math.toRadians(193)), Math.toRadians(0))
//                                    .build();
//
//                            spikemark = drive.trajectorySequenceBuilder(backboard.end())
//                                    .lineToLinearHeading(new Pose2d(18, 41, Math.toRadians(220)))
//                                    .build();
//
//                            parkare = drive.trajectorySequenceBuilder(spikemark.end())
//                                    .lineToLinearHeading(new Pose2d(33, 53, Math.toRadians(180)))
//                                    .lineTo(new Vector2d(60, 60))
//                                    .build();
//                            break;
//
//                        case 2:
//
//                            backboard = drive.trajectorySequenceBuilder(startBlue)
//                                    .splineToConstantHeading(new Vector2d(33, 41.5), Math.toRadians(270))
//                                    .splineToLinearHeading(new Pose2d(57, 35, Math.toRadians(193)), Math.toRadians(0))
//                                    .build();
//
//                            spikemark = drive.trajectorySequenceBuilder(backboard.end())
//                                    .lineToLinearHeading(new Pose2d(27, 38.7, Math.toRadians(250)))
//                                    .build();
//                            parkare = drive.trajectorySequenceBuilder(spikemark.end())
//                                    .lineToLinearHeading(new Pose2d(33, 53, Math.toRadians(180)))
//                                    .lineTo(new Vector2d(60, 60))
//                                    .build();
//
//
//                            break;
//
//
//                        case 1:
//                            backboard = drive.trajectorySequenceBuilder(startBlue)
//                                    .splineToConstantHeading(new Vector2d(33, 44), Math.toRadians(270))
//                                    .splineToLinearHeading(new Pose2d(56.2, 44, Math.toRadians(190)), Math.toRadians(0))
//                                    .build();
//
//                            spikemark = drive.trajectorySequenceBuilder(backboard.end())
//                                    .lineToLinearHeading(new Pose2d(31, 50, Math.toRadians(250)))
//                                    .build();
//                            parkare = drive.trajectorySequenceBuilder(spikemark.end())
//                                    .lineToLinearHeading(new Pose2d(33, 53, Math.toRadians(180)))
//                                    .lineTo(new Vector2d(60, 60))
//                                    .build();
//
//                            break;
//
//                    }
//                    trajectoriesCreated = true;
//                    telemetry.addData("Trajectories", "Created");
//                }
//                telemetry.update();
//            }

        waitForStart();

        schedule(
                new SequentialCommandGroup(
                        vbar.Close(),
                        vbar.Vbar_Idle(),
                        new WaitCommand(1000),
                        cuva.close(),
//                        new FollowTrajectoryCommand(backboard, drive),
                        new SpikeMarkCommand(drive,backboard4,backboard4,backboard4,detect,true)
                                .alongWith(lift.goLift(500)),
                        new WaitCommand(2000),
                        cuva.cuva_arunca(),
                        new WaitCommand(1000),
                        cuva.cuva_inapoi(),
                        new WaitCommand(400),
                        cuva.open(),
                        new WaitCommand(500),
                        cuva.close(),
                        new WaitCommand(500),
                        cuva.cuva_arunca(),
                        new WaitCommand(500),
                        cuva.open_insprejos(),
                        new WaitCommand(500),
                        lift.goLift(0),
//                        new BackDropCommand(lift,cuva),
//                        new WaitCommand(1000),
//                        vbar.vbarjos(),
//                        new FollowTrajectoryCommand(spikemark,drive),
                        new SpikeMarkCommand(drive,spikemark4,spikemark4,spikemark4,detect,true)
                                .alongWith(vbar.Vbar_Idle()),
                        new DelayedCommand(vbar.Open(),650),
//                        new DelayedCommand(vbar.Vbar_Idle(),1000),
//                        new WaitCommand(1500),
                        new SpikeMarkCommand(drive,parkare4,parkare4,parkare4,detect,true)
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

