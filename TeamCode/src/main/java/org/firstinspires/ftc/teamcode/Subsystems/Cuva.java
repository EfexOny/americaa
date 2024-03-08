package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Constants.arunca;
import static org.firstinspires.ftc.teamcode.Constants.auto_out;
import static org.firstinspires.ftc.teamcode.Constants.auto_outdr;
import static org.firstinspires.ftc.teamcode.Constants.auto_outst;
import static org.firstinspires.ftc.teamcode.Constants.avion_arunca;
import static org.firstinspires.ftc.teamcode.Constants.deschis_dr;
import static org.firstinspires.ftc.teamcode.Constants.deschis_st;
import static org.firstinspires.ftc.teamcode.Constants.inapoi;
import static org.firstinspires.ftc.teamcode.Constants.inchis_dr;
import static org.firstinspires.ftc.teamcode.Constants.inchis_st;
import static org.firstinspires.ftc.teamcode.Constants.indr;
import static org.firstinspires.ftc.teamcode.Constants.ingheara;
import static org.firstinspires.ftc.teamcode.Constants.inst;
import static org.firstinspires.ftc.teamcode.Constants.outdr;
import static org.firstinspires.ftc.teamcode.Constants.outgheara;
import static org.firstinspires.ftc.teamcode.Constants.outst;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.function.BooleanSupplier;

@Config
public class Cuva extends SubsystemBase {

    Servo out_stanga,out_dreapta;
    public static double av1=1;
    Lift lift;
    Servo s1,s2,s3;

    boolean canBeLaunched = false;

// st = 0.15, dr = 0.75
    TouchSensor aliniere;
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

//        s2 sus jos
//        s1 si s3 e rotatia centrifuga
//        s1 stanga(reversed) s3 dr

        s1 = hardwareMap.get(Servo.class,"cuva");
        s2 = hardwareMap.get(Servo.class,"rotcuva1");
        s3 = hardwareMap.get(Servo.class,"rotcuva2");

        avion = hardwareMap.get(Servo.class,"avion");

    }



    public Command stefan(){
        return new InstantCommand(
                () -> avion.setPosition(av1)
        );
    }



    public Command ridicare(double put){
        canBeLaunched = true;
        return new InstantCommand(
                () -> {
                        r1.setPower(-put);
                        r2.setPower(-put);
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



    public Command mereuta(int l1){
        return new SequentialCommandGroup(
                close(),
                new WaitCommand(300),
                new ParallelCommandGroup(
                cuva_arunca(),
                lift.goLift(l1)
                        ),
                new WaitCommand(300)
//                lift.change(false)
            );
    }

    public SequentialCommandGroup afterparty(){
        return new SequentialCommandGroup(
                open(),
                new WaitCommand(450),
                close(),
                new WaitCommand(550),
                open(),
                cuva_inapoi(),
                new WaitCommand(200),
                lift.goLift(0),
                new InstantCommand(() -> s2.setPosition(ingheara))
//                lift.change(true)
        );
    }

    public Command cuva_arunca(){
        return new SequentialCommandGroup(
                new InstantCommand(() -> s2.setPosition(outgheara)),
                    new WaitCommand(200),

                    new InstantCommand(() -> {
                        s1.setPosition(outst);
                        s3.setPosition(outdr);}
                    )
        );
    }

    public Command auto_cuva_arunca(){
        return new SequentialCommandGroup(
                new InstantCommand(() -> s2.setPosition(auto_out)),
                new WaitCommand(200),

                new InstantCommand(() -> {
                    s1.setPosition(auto_outst);
                    s3.setPosition(auto_outdr);}
                )
        );
    }


    public Command cuva_inapoi(){
        return new SequentialCommandGroup(
                    new InstantCommand(() -> s1.setPosition(inst)),
                    new InstantCommand(() -> s3.setPosition(indr))
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



    //sa ma bata Sf Petru pentru asta
    public Command openCustom(int ccase){
        if (ccase == 3)
            return new InstantCommand(
                    () -> {
                        out_dreapta.setPosition(deschis_dr);
                        out_stanga.setPosition(inchis_st);
                    }
            );
        return new InstantCommand(
                () -> {
                    out_dreapta.setPosition(deschis_dr);
                    out_stanga.setPosition(deschis_st);
                }
        );
    }

}
