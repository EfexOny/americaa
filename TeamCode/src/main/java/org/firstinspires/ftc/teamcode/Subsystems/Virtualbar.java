package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.deschis_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.deschis_stanga;
import static org.firstinspires.ftc.teamcode.Constants.inchis_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.inchis_stanga;
import static org.firstinspires.ftc.teamcode.Constants.vbaridle_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.vbaridle_stanga;
import static org.firstinspires.ftc.teamcode.Constants.vbarjos_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.vbarjos_stanga;
import static org.firstinspires.ftc.teamcode.Constants.vbarsus_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.vbarsus_stanga;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.function.BooleanSupplier;


@Config
public class Virtualbar extends SubsystemBase{

//    DigitalChannel b1,b2;
    Servo barstanga,bardreapta;
    Servo stanga_principala,dreapta_principala;
    double v1,v2;
    public Virtualbar(HardwareMap hardwareMap){

        barstanga = hardwareMap.get(Servo.class,"vbar_stanga");
        bardreapta = hardwareMap.get(Servo.class,"vbar_dreapta");

        stanga_principala = hardwareMap.get(Servo.class,"pim_stanga");
        dreapta_principala = hardwareMap.get(Servo.class,"pim_dreapta");

//        b1 = hardwareMap.get(DigitalChannel.class,"b1");
//        b2 = hardwareMap.get(DigitalChannel.class,"b2");
    }

    @Override
    public void periodic() {
//        if(!b1.getState())
//            dreapta_principala.setPosition(inchis_dreapta);
//        if(!b2.getState())
//            stanga_principala.setPosition(inchis_stanga);
        super.periodic();
    }

    public Command VSus(){
        return new InstantCommand(
                ()->  {
                    barstanga.setPosition(vbarsus_stanga);
                    bardreapta.setPosition(vbarsus_dreapta);
                }
        );
    }

    public SequentialCommandGroup cekkt(){
        return new SequentialCommandGroup(
                Close(),
                new WaitCommand(500),
                VSus(),
                new WaitCommand(900),
                Open(),
                new WaitCommand(800),
                Vbar_Idle()
        );
    }

    public Command Vbar_Idle(){
        return new InstantCommand(() -> {
            barstanga.setPosition(vbaridle_stanga);
            bardreapta.setPosition(vbaridle_dreapta);
        });
    }

    public Command Close(){
        return new InstantCommand(
                () -> {
                    stanga_principala.setPosition(inchis_stanga);
                    dreapta_principala.setPosition(inchis_dreapta);
                }
        );
    }

    public void CloseDirect() {
        stanga_principala.setPosition(inchis_stanga);
        dreapta_principala.setPosition(inchis_dreapta);
    }



    public Command Open(){
        return new InstantCommand(
                () -> {
                    stanga_principala.setPosition(deschis_stanga);
                    dreapta_principala.setPosition(deschis_dreapta);}
        );
    }


    public Command Open_wide(){
        return new InstantCommand(
                () -> {
                    stanga_principala.setPosition(0.45);
                    dreapta_principala.setPosition(0.27);}
        );
    }
    public Command VJos(){
        return new InstantCommand(
                ()->  {
                    barstanga.setPosition(vbarjos_stanga);
                    bardreapta.setPosition(vbarjos_dreapta);
                }
        );
    }

    public ParallelCommandGroup vbarjos(){
        return new ParallelCommandGroup(
                VJos()
        );
    }

//    public BooleanSupplier g1(){
//        return () -> b1.getState();
//    }
//
//    public ConditionalCommand da(){
//        return new ConditionalCommand(
//                Open_wide(),Close(),g1()
//        );
//    }

}
