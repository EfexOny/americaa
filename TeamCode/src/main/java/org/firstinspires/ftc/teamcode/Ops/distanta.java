package org.firstinspires.ftc.teamcode.Ops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp
public class distanta extends LinearOpMode {

    DistanceSensor extindere_distanta;

    @Override
    public void runOpMode() throws InterruptedException {
        extindere_distanta = hardwareMap.get(DistanceSensor.class,"extindere_distanta");

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("distanta",extindere_distanta.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
