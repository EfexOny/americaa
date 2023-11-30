package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.vision.blue;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp(name = "detec")
public class AutoTest extends LinearOpMode {

    SampleMecanumDrive drive;

//    TrajectorySequence mark;

    Lift lift;
    Virtualbar vbar;

//    private Thread backdropLift;
//    private Thread vBar;
//
//    Pose2d start = new Pose2d(0,0);
    VisionPortal portal;
    blue Thrash;


    @Override
    public void runOpMode() {

        blue Thrash = new blue();
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
//                .addDisplacementMarker(() -> {
//                    vBar.start();
//                })
//                .lineToConstantHeading()
//                .build();

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("da",Thrash.getPropPosition());
            telemetry.update();
        }
    }

}
