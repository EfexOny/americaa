package org.firstinspires.ftc.teamcode.Ops;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;

import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;

public class Creier extends CommandOpMode {

    public Lift lift;
    public Cuva cuva;
    public Virtualbar virtualbar;

    Motor lf,rf,rb,lb;
    public GamepadEx d1,d2;
    public DriveSubsystem drive;

    DriveCommand mergi;
    Button turbo;
    Button vbar_idle,auto_deposit,auto_grab;
    Button gheara_principala,gheara_secundara,vbarsus,vbarjos;
    Trigger cova1,cova2;
    Button lift_stanga,lift_dreapta;
    Button dpad1,dpad2,dpad3,dpad4;
    Button gheara_mereuta;
    Trigger Left,Right;




    public void initHardware(){

        lf = new Motor(hardwareMap, "lf");
        rf = new Motor(hardwareMap, "rf");
        lb = new Motor(hardwareMap, "lr");
        rb = new Motor(hardwareMap, "rr");
        d1 = new GamepadEx(gamepad1);

        d2 = new GamepadEx(gamepad2);

        cuva = new Cuva(hardwareMap);
        lift = new Lift(hardwareMap);
        virtualbar = new Virtualbar(hardwareMap);

        drive = new DriveSubsystem(lf,rf,lb,rb);

        register(drive);
    }

    @Override
    public void initialize() {
        lift.goLift(0);
    }
}
