package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.Blue;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name = "detec")
public class blue extends LinearOpMode {

    Blue vision;
    VisionPortal portal;
    SampleMecanumDrive drive;

    TrajectorySequence mark;

    Lift lift;
    Virtualbar vbar;

    private Thread backdropLift;
    private Thread vBar;

    boolean drop = false;
    Pose2d start = new Pose2d(0,0);


    @Override
    public void runOpMode() {

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .setCamera(BuiltinCameraDirection.BACK)
                .addProcessor(vision)
                .build();

        drive = new SampleMecanumDrive(hardwareMap);

        vBar = new Thread( () -> {
                 vbar.VJos();
        });


        mark = drive.trajectorySequenceBuilder(start)
                .addDisplacementMarker(() -> {
                    vBar.start();
                })
//                .lineToConstantHeading()
                .build();

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("da",vision.getPropPosition());
            telemetry.update();
        }
    }

}
