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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Cuva extends SubsystemBase {

    Servo out_stanga,out_dreapta;
    public static double av1=1;
    Lift lift;


// st = 0.15, dr = 0.75
    Servo drop,avion;

    DcMotor r1,r2;
    public Cuva(HardwareMap hardwareMap){

        r1 = hardwareMap.get(DcMotor.class,"spate");
        r2 = hardwareMap.get(DcMotor.class,"dreapta");

        avion = hardwareMap.get(Servo.class,"avion");

        r1.setDirection(DcMotorSimple.Direction.REVERSE);

        r1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        r2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift = new Lift(hardwareMap);

        out_stanga = hardwareMap.get(Servo.class,"sec_stanga");
        out_dreapta = hardwareMap.get(Servo.class,"sec_dreapta");



        drop = hardwareMap.get(Servo.class,"cuva");

        avion = hardwareMap.get(Servo.class,"avion");

    }


    public Command stefan(){
        return new InstantCommand(
                () -> avion.setPosition(av1)
        );
    }

    public Command ridicare(double put){
        return new InstantCommand(
                () -> {
                        r1.setPower(put);
                        r2.setPower(put);
                }
        );
    }


    public Command close(){
        return new InstantCommand(
                () -> {
                    out_dreapta.setPosition(inchis_dr);
                    out_stanga.setPosition(inchis_st);
                }
        );
    }



    public SequentialCommandGroup mereuta(int l1){
        return new SequentialCommandGroup(
                close(),
                new WaitCommand(300),
                lift.goLift(l1),
                new WaitCommand(300),
                cuva_inapoi()
            );
    }

    public SequentialCommandGroup afterparty(){
        return new SequentialCommandGroup(
                open(),
                new WaitCommand(450),
                close(),
                new WaitCommand(700),
                cuva_arunca(),
                new WaitCommand(400),
                open(),
                new WaitCommand(500),
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

    public Command open(){
        return new InstantCommand(
                () -> {
                    out_dreapta.setPosition(deschis_dr);
                    out_stanga.setPosition(deschis_st);
                }
        );
    }

    public Command open_insprejos(){
        return new InstantCommand(
                () -> {
                    out_dreapta.setPosition(0.75);
                    out_stanga.setPosition(0.15);
                }
        );
    }

}
