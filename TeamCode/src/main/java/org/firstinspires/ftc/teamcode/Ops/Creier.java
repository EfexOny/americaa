package org.firstinspires.ftc.teamcode.Ops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;

public class Creier extends CommandOpMode {
    public Lift lift;

    Gamepad dr1;

    IMU imu;

    MecanumDrive stefan;

    Button nospam;
    Button avion;
    Trigger senzor,senzor2;

    public Cuva cuva;
    public Virtualbar virtualbar;

    Motor lf,rf,rb,lb;
    DigitalChannel b1,b2;
    public GamepadEx d1,d2;
    public DriveSubsystem drive;

    DriveCommand mergi;
    Button turbo;
    ButtonReader nivel;
    Button vbar_idle,auto_deposit,auto_grab;

    Button gheara_principala,gheara_secundara,vbarsus,vbarjos;
    Trigger cova1,cova2;
    Button lift_stanga,lift_dreapta;
    Button dpad1,dpad2,dpad3,dpad4;
    Button gheara_mereuta;
    Trigger Left,Right;




    public void initHardware(){
        b1 = hardwareMap.get(DigitalChannel.class,"b1");
        b2 = hardwareMap.get(DigitalChannel.class,"b2");

        lf = new Motor(hardwareMap, "lf");
        rf = new Motor(hardwareMap, "rf");
        lb = new Motor(hardwareMap, "lr");
        rb = new Motor(hardwareMap, "rr");

        lf.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        rf.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        lb.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        rb.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);

        imu = hardwareMap.get(IMU.class,"imu");


        d1 = new GamepadEx(gamepad1);

        d2 = new GamepadEx(gamepad2);

        cuva = new Cuva(hardwareMap);
        lift = new Lift(hardwareMap);
        virtualbar = new Virtualbar(hardwareMap);

        drive  = new DriveSubsystem(lf,rf,lb,rb);
        stefan = new MecanumDrive(lf, rf, lb, rb);

        register(drive,virtualbar,lift,cuva);
    }

    @Override
    public void initialize() {
        virtualbar.Close();
    }
}
