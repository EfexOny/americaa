package org.firstinspires.ftc.teamcode.Ops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;

import java.util.List;

@Photon
@TeleOp(name="Optimi")
public class fasterTele extends CommandOpMode {

    public Lift lift;

    ButtonReader spame;
    Button reset;
    Button nospam;
    Button avion;
    Trigger senzor,senzor2,magnet;

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
    Trigger dpad1,dpad2,dpad3,dpad4;
    Button gheara_mereuta;
    Trigger Left,Right;

    private List<LynxModule> hubs;
    private ElapsedTime elapsedtime;

    @Override
    public void initialize() {

        hubs = hardwareMap.getAll(LynxModule.class);
        hubs.forEach(hub -> hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL));

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

        d1 = new GamepadEx(gamepad1);
        d2 = new GamepadEx(gamepad2);

        cuva = new Cuva(hardwareMap);
        lift = new Lift(hardwareMap);
        virtualbar = new Virtualbar(hardwareMap);

        drive  = new DriveSubsystem(lf,rf,lb,rb);

        register(drive,virtualbar,lift,cuva);

        nospam = new GamepadButton(d2,GamepadKeys.Button.BACK).whenPressed(lift.norma());
        spame = new ButtonReader(d2,GamepadKeys.Button.BACK);

        avion = new GamepadButton(d1, GamepadKeys.Button.Y).toggleWhenPressed(cuva.stefan());

        gheara_principala = new GamepadButton(d2, GamepadKeys.Button.A).toggleWhenPressed(virtualbar.Open_wide());
        gheara_secundara = new GamepadButton(d2, GamepadKeys.Button.B).toggleWhenPressed(cuva.close(),cuva.open());

        vbarjos = new GamepadButton(d2,GamepadKeys.Button.DPAD_DOWN).whenPressed(virtualbar.vbarjos());
        vbarsus = new GamepadButton(d2,GamepadKeys.Button.DPAD_UP).whenPressed(virtualbar.VSus());

        auto_deposit = new GamepadButton(d2,GamepadKeys.Button.Y).toggleWhenPressed(cuva.mereuta(300),cuva.afterparty());
        lift_dreapta = new GamepadButton(d2,GamepadKeys.Button.RIGHT_BUMPER).toggleWhenPressed(cuva.mereuta(450),cuva.afterparty());
        cova1 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)!=0))
                .toggleWhenActive(cuva.mereuta(600), cuva.afterparty());

        cova2 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)!=0))
                .whileActiveContinuous(cuva.ridicare(1)).whenInactive(cuva.ridicare(0));
        lift_stanga = new GamepadButton(d2,GamepadKeys.Button.LEFT_BUMPER).whileHeld(cuva.ridicare(-1)).whenReleased(cuva.ridicare(0));

        vbar_idle = new GamepadButton(d2,GamepadKeys.Button.DPAD_LEFT).toggleWhenPressed(virtualbar.Vbar_Idle(),true );

        auto_grab = new GamepadButton(d2,GamepadKeys.Button.X).toggleWhenPressed(virtualbar.cekkt(),true);
        gheara_mereuta = new GamepadButton(d2,GamepadKeys.Button.DPAD_RIGHT).toggleWhenPressed(virtualbar.Close());


        turbo = new GamepadButton(d1,GamepadKeys.Button.X).toggleWhenPressed(drive.turbo(1),drive.turbo(1));

        dpad1 = new GamepadButton(d1,GamepadKeys.Button.DPAD_UP).whileHeld(drive.Valer(0,0.5,0,true));
        dpad2 = new GamepadButton(d1,GamepadKeys.Button.DPAD_DOWN).whileHeld( drive.Valer(0,-0.5,0,true));
        dpad3 = new GamepadButton(d1,GamepadKeys.Button.DPAD_LEFT).whileHeld( drive.Valer(-0.5,0,0,true));
        dpad4 = new GamepadButton(d1,GamepadKeys.Button.DPAD_RIGHT).whileHeld( drive.Valer(0.5,0,0,true));

        Left = new Trigger(() -> (d1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) != 0))
                .whileActiveContinuous( drive.Valer(0,0,-0.5,true));
        Right = new Trigger(() -> (d1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) != 0))
                .whileActiveContinuous( drive.Valer(0,0,0.5,true));

        senzor = new Trigger(() -> virtualbar.dow1() && virtualbar.VbarState());
        senzor2 = new Trigger(() -> virtualbar.dow2() && virtualbar.VbarState());
        magnet = new Trigger(() -> lift.check() && lift.isDown());

        magnet.toggleWhenActive( new InstantCommand( () -> {lift.resetTicks();
            lift.aFost();}));

        senzor.toggleWhenActive(
                new SequentialCommandGroup(
                        new WaitCommand(500),
                        virtualbar.closesep(false),
                        new InstantCommand(() ->gamepad1.rumble(0,1,400)),
                        new InstantCommand(() -> gamepad2.rumble(0,1,400))
                )
        );
        senzor2.toggleWhenActive(  new SequentialCommandGroup(
                        new WaitCommand(500),
                        virtualbar.closesep(true),
                        new InstantCommand(() -> gamepad1.rumble(1,0,400)),
                        new InstantCommand(() -> gamepad2.rumble(1,0,400))
                )
        );

        senzor.and(senzor2).toggleWhenActive(new SequentialCommandGroup(
                new WaitCommand(100),virtualbar.cekkt()
        ));


        mergi = new DriveCommand(drive,d1::getLeftX,d1::getLeftY,d1::getRightX);



        register(drive);
        drive.setDefaultCommand(mergi);



    }

    @Override
    public void run() {
        super.run();
        hubs.forEach(LynxModule::clearBulkCache);
    }
}
