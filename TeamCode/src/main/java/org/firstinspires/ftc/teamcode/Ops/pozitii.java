package org.firstinspires.ftc.teamcode.Ops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="pozitii")
public class pozitii extends LinearOpMode {

    Servo barstanga,bardreapta;
    Servo stanga_principala,dreapta_principala;
    Servo stanga_sec,dreapta_sec;


    Servo drop;

    public static double vbarsus_stanga,vbarsus_dreapta,vbarjos_stanga,vbarjos_dreapta;
    public static double inchis_stanga,inchis_dreapta,deschis_stanga,deschis_dreapta;

    public static double inchis_st,inchis_dr,deschist_st,deschis_dr;

    public static double arunca,inapoi;

    @Override
    public void runOpMode() {

        barstanga = hardwareMap.get(Servo.class,"vbar_stanga");
        bardreapta = hardwareMap.get(Servo.class,"vbar_dreapta");

        stanga_principala = hardwareMap.get(Servo.class,"pim_stanga");
        dreapta_principala = hardwareMap.get(Servo.class,"pim_dreapta");

        dreapta_sec = hardwareMap.get(Servo.class,"sec_dreapta");
        stanga_sec = hardwareMap.get(Servo.class,"sec_stanga");

        drop = hardwareMap.get(Servo.class,"cuva");


        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.a) {
                barstanga.setPosition(vbarsus_stanga);
                bardreapta.setPosition(vbarsus_dreapta);
            }

            if(gamepad1.b){
                barstanga.setPosition(vbarjos_stanga);
//                bardreapta.setPosition(vbarjos_dreapta);
            }

            if(gamepad1.x){
                stanga_principala.setPosition(deschis_stanga);
                dreapta_principala.setPosition(deschis_dreapta);
            }

            if(gamepad1.y){
                stanga_principala.setPosition(inchis_stanga);
                dreapta_principala.setPosition(inchis_dreapta);
            }

            if(gamepad2.a){
                stanga_sec.setPosition(deschist_st);
                dreapta_sec.setPosition(deschis_dr);
            }

            if(gamepad2.b){
                stanga_sec.setPosition(inchis_st);
                dreapta_sec.setPosition(inchis_dr);
            }

            if(gamepad2.x){
                drop.setPosition(arunca);
            }

            if(gamepad2.y){
                drop.setPosition(inapoi);
            }

        }

    }
}
