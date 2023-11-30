package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.red;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name = "red")
public class Auto extends LinearOpMode {

    SampleMecanumDrive drive;

    TrajectorySequence mark;

    Lift lift;
    Virtualbar vbar;

//    private Thread backdropLift;
//    private Thread vBar;
//
//    Pose2d start = new Pose2d(-36,60);
    VisionPortal portal;
    red Thrash;


    @Override
    public void runOpMode() {

        red Thrash = new red();
        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(Thrash)
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();


//        drive = new SampleMecanumDrive(hardwareMap);
//
//        vBar = new Thread( () -> {
//                 vbar.VaJos();
//        });


//        mark = drive.trajectorySequenceBuilder(start)
//                .strafeTo(new Vector2d(-25,40))
//                .build();

       // dreapta =  strafe to -47 40
//       centru  lineto -36 34

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("da",Thrash.getPropPosition());
            telemetry.update();


        }
    }

}
