package org.firstinspires.ftc.teamcode.Ops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@TeleOp(name = "SENZORI MEREUTA")
public class distanta extends LinearOpMode {

    DigitalChannel b1,b2;


    @Override
    public void runOpMode() throws InterruptedException {
        b1 = hardwareMap.get(DigitalChannel.class,"b1");
        b2 = hardwareMap.get(DigitalChannel.class,"b2");

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("b1",b1.getState());
            telemetry.addData("b2",b2.getState());
            telemetry.update();
        }
    }
}
