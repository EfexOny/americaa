package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.arunca;
import static org.firstinspires.ftc.teamcode.Constants.avion_arunca;
import static org.firstinspires.ftc.teamcode.Constants.deschis_dr;
import static org.firstinspires.ftc.teamcode.Constants.deschis_st;
import static org.firstinspires.ftc.teamcode.Constants.inapoi;
import static org.firstinspires.ftc.teamcode.Constants.inchis_dr;
import static org.firstinspires.ftc.teamcode.Constants.inchis_st;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Cuva extends SubsystemBase {

    Servo out_stanga,out_dreapta;
    Lift lift;

    Servo drop,avion;

    public Cuva(HardwareMap hardwareMap){

        lift = new Lift(hardwareMap);

        out_stanga = hardwareMap.get(Servo.class,"sec_stanga");
        out_dreapta = hardwareMap.get(Servo.class,"sec_dreapta");

        drop = hardwareMap.get(Servo.class,"cuva");

        avion = hardwareMap.get(Servo.class,"avion");

    }

    public Command close(){
        return new InstantCommand(
                () -> {
                    out_dreapta.setPosition(inchis_dr);
                    out_stanga.setPosition(inchis_st);
                }
        );
    }

    public SequentialCommandGroup mereuta(){
        return new SequentialCommandGroup(
                close(),
                new WaitCommand(500),
                lift.goLift(900),
                cuva_arunca()
//                cuva_inapoi(),
//                new WaitCommand(500),
//                open(),
//                new WaitCommand(500),
//                close(),
//                new WaitCommand(500),
//                cuva_arunca(),
//                new WaitCommand(500),
//                open(),
//                new WaitCommand(500),
//                lift.goLift(300),
//                new WaitCommand(700),
//                lift.goLift(0)
            );
    }

    public SequentialCommandGroup afterparty(){
        return new SequentialCommandGroup(
                cuva_inapoi(),
                new WaitCommand(500),
                open(),
                new WaitCommand(500),
                close(),
                new WaitCommand(500),
                cuva_arunca(),
                new WaitCommand(500),
                open(),
                new WaitCommand(500),
                lift.goLift(300),
                new WaitCommand(700),
                lift.goLift(0)
        );
    }

    public Command cuva_arunca(){
        return new InstantCommand(
                () -> {
                    drop.setPosition(arunca);
                }
        );
    }

    public Command cuva_inapoi(){
        return new InstantCommand(
                () -> {
                    drop.setPosition(inapoi);
                }
        );
    }

    public Command launch(){
        return new InstantCommand(
                () -> {
                    avion.setPosition(avion_arunca);
                }
        );
    }

    public Command open(){
        return new InstantCommand(
                () -> {
                    out_dreapta.setPosition(deschis_dr);
                    out_stanga.setPosition(deschis_st);
                }
        );
    }

}
