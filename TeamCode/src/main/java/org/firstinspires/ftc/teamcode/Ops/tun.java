package org.firstinspires.ftc.teamcode.Ops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

@Config
@TeleOp(name = "pos tune")
public class tun extends LinearOpMode {
    public static double out = 0;
    public static double in = 0;

    public static double outst = 0;
    public static double outdr = 0;

    public static double inst = 0;
    public static double indr = 0;

    TouchSensor magnet;
    Servo s1,s2,s3;
    @Override
    public void runOpMode() throws InterruptedException {


        s1 = hardwareMap.get(Servo.class,"cuva");
        s2 = hardwareMap.get(Servo.class,"rotcuva1");
        s3 = hardwareMap.get(Servo.class,"rotcuva2");

        magnet = hardwareMap.get(TouchSensor.class,"magnet");


//        s2 sus jos

//        s1 si s3 e rotatia centrifuga
//        s1 stanga(reversed) s3 dr

        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.a)
            {
                s2.setPosition(out);
            }
            if(gamepad1.b)
            {
                s2.setPosition(in);
            }

            if(gamepad2.a)
            {
                s1.setPosition(outst);
                s3.setPosition(outdr);
            }

            if(gamepad2.b)
            {
                s1.setPosition(inst);
                s3.setPosition(indr);
            }

            telemetry.addData("mag",magnet.isPressed());
            telemetry.update();
        }
    }
}
