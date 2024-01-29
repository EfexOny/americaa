package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.deschis_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.deschis_stanga;
import static org.firstinspires.ftc.teamcode.Constants.inchis_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.inchis_stanga;
import static org.firstinspires.ftc.teamcode.Constants.vbaridle_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.vbaridle_stanga;
import static org.firstinspires.ftc.teamcode.Constants.vbarjos_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.vbarjos_stanga;
import static org.firstinspires.ftc.teamcode.Constants.vbarstack1_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.vbarstack1_stanga;
import static org.firstinspires.ftc.teamcode.Constants.vbarsus_dreapta;
import static org.firstinspires.ftc.teamcode.Constants.vbarsus_stanga;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.commands.AutoCloseGheara;

import java.util.function.BooleanSupplier;


@Config
public class Virtualbar extends SubsystemBase{

    boolean jos;
    DistanceSensor s1,s2;
    public static double distgheara = 7;
    public static double jos1=0.45,jos2=0.45;
    Servo barstanga,bardreapta;
    Servo stanga_principala,dreapta_principala;
    double v1,v2;
    public Virtualbar(HardwareMap hardwareMap){
        barstanga = hardwareMap.get(Servo.class,"vbar_stanga");
        bardreapta = hardwareMap.get(Servo.class,"vbar_dreapta");

        stanga_principala = hardwareMap.get(Servo.class,"pim_stanga");
        dreapta_principala = hardwareMap.get(Servo.class,"pim_dreapta");

        s1 = hardwareMap.get(DistanceSensor.class,"s1");
        s2 = hardwareMap.get(DistanceSensor.class,"s2");
    }

    @Override
    public void periodic() {
        super.periodic();
    }

    public boolean dow1(){
        return  s1.getDistance(DistanceUnit.CM) < distgheara;
    }

    public boolean dow2(){
        return  s2.getDistance(DistanceUnit.CM) < distgheara;
    }

    public BooleanSupplier down1(){
        return () -> s1.getDistance(DistanceUnit.CM) < distgheara;
    }

    public BooleanSupplier down2(){
        return () -> s2.getDistance(DistanceUnit.CM) < distgheara;
    }
    public Command VSus(){
        return new InstantCommand(
                ()->  {
                    jos = false;
                    barstanga.setPosition(vbarsus_stanga);
                    bardreapta.setPosition(vbarsus_dreapta);
                }
        );
    }

    public Command closesep(boolean stg){
        if(stg)
            return new InstantCommand(
                    () ->
                           stanga_principala.setPosition(inchis_stanga)
            );
        else
            return new InstantCommand(
                    () ->
                            dreapta_principala.setPosition(inchis_dreapta)
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
                Close(),
                Vbar_Idle()


        );
    }

    public Command Vbar_Idle(){
        return new InstantCommand(() -> {
            jos = false;
            barstanga.setPosition(vbaridle_stanga);
            bardreapta.setPosition(vbaridle_dreapta);
        });
    }
    public Command Vbar_Stack1(){
        return new InstantCommand(() -> {
            barstanga.setPosition(vbarstack1_stanga);
            bardreapta.setPosition(vbarstack1_dreapta);
        });
    }

    public Command Close(){
        return new InstantCommand(
                () -> {
                    stanga_principala.setPosition(inchis_stanga);
                    dreapta_principala.setPosition(inchis_dreapta);}
        );
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

    public boolean VbarState(){
        return jos;

    }

    public Command VJos(){
        return new InstantCommand(
                ()->  {
                    jos = true;
                    barstanga.setPosition(vbarjos_stanga);
                    bardreapta.setPosition(vbarjos_dreapta);
                }
        );
    }

    public ParallelCommandGroup vbarjos(){
        return new ParallelCommandGroup(
                VJos(),
                new WaitCommand(1000),
                new InstantCommand(() -> stanga_principala.setPosition(jos1)),
                new InstantCommand(() -> dreapta_principala.setPosition(jos2))
        );
    }
}
