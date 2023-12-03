package org.firstinspires.ftc.teamcode.Ops.auto;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.BluePropThreshold;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp
public class testdetec extends LinearOpMode {

    BluePropThreshold blue;
    VisionPortal portal;

    @Override
    public void runOpMode() throws InterruptedException {
        blue = new BluePropThreshold();

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(blue)
                .setCameraResolution(new Size(640, 480))
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData(">",blue.getPropPosition());
            telemetry.addData("<",blue.coaie());
            telemetry.addData("<",blue.coaie2());
            telemetry.update();
        }
    }
}
