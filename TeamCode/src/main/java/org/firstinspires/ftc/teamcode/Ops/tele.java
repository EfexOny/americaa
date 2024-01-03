package org.firstinspires.ftc.teamcode.Ops;

import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.ams.AMSColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;

@TeleOp(name="ruble")
public class tele extends Creier {
    boolean jos = false,dus = false;


    @Override
    public void initialize() {


        initHardware();


        gheara_principala = new GamepadButton(d2, GamepadKeys.Button.A).toggleWhenPressed(virtualbar.Open_wide());
        gheara_secundara = new GamepadButton(d2, GamepadKeys.Button.B).toggleWhenPressed(cuva.close(),cuva.open());

        vbarjos = new GamepadButton(d2,GamepadKeys.Button.DPAD_DOWN).whenPressed(virtualbar.vbarjos());

        vbarsus = new GamepadButton(d2,GamepadKeys.Button.DPAD_UP).whenPressed(virtualbar.VSus());

        cova1 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)!=0))
                .toggleWhenActive(cuva.cuva_inapoi(), cuva.cuva_arunca());
//        cova2 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)!=0))
//                .toggleWhenActive(cuva.cuva_arunca());

        lift_stanga = new GamepadButton(d2,GamepadKeys.Button.LEFT_BUMPER).toggleWhenPressed(lift.goLift(800));
        lift_dreapta = new GamepadButton(d2,GamepadKeys.Button.RIGHT_BUMPER).toggleWhenPressed(lift.goLift(300),lift.goLift(0));

        vbar_idle = new GamepadButton(d2,GamepadKeys.Button.DPAD_LEFT).toggleWhenPressed(virtualbar.Vbar_Idle(),true );

        auto_deposit = new GamepadButton(d2,GamepadKeys.Button.Y).toggleWhenPressed(cuva.mereuta(),cuva.afterparty());

        auto_grab = new GamepadButton(d2,GamepadKeys.Button.X).toggleWhenPressed(virtualbar.cekkt(),true);
        gheara_mereuta = new GamepadButton(d2,GamepadKeys.Button.DPAD_RIGHT).toggleWhenPressed(virtualbar.Close());


        turbo = new GamepadButton(d1,GamepadKeys.Button.X).toggleWhenPressed(drive.turbo(1),drive.turbo(0.7));

        dpad1 = new GamepadButton(d1,GamepadKeys.Button.DPAD_UP).whileHeld(drive.dpad_vertical(true,false))
                .whenReleased(drive.dpad_vertical(false,false));
        dpad2 = new GamepadButton(d1,GamepadKeys.Button.DPAD_DOWN).whileHeld(drive.dpad_vertical(false,true))
                .whenReleased(drive.dpad_vertical(false,false));
        dpad3 = new GamepadButton(d1,GamepadKeys.Button.DPAD_LEFT).whileHeld(drive.dpad_orizontal(true,false))
                .whenReleased(drive.dpad_orizontal(false,false));
        dpad4 = new GamepadButton(d1,GamepadKeys.Button.DPAD_RIGHT).whileHeld(drive.dpad_orizontal(false,true))
                .whenReleased(drive.dpad_vertical(false,false));

        Left = new Trigger(() -> (d1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) != 0))
                .whileActiveContinuous(drive.bumper_rotire(true,false));
        Right = new Trigger(() -> (d1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) != 0))
                .whileActiveContinuous(drive.bumper_rotire(false,true));

        mergi = new DriveCommand(drive,d1::getLeftX,d1::getLeftY,d1::getRightX);


        register(drive);
        drive.setDefaultCommand(mergi);
    }

    @Override
    public void run() {
        telemetry.addData("target pos",lift.getLiftPosition());
        telemetry.addData("ticks",lift.getTciks());
        telemetry.addData("b1",virtualbar.b1());
        telemetry.addData("b2",virtualbar.b2());
        telemetry.addData("jos",virtualbar.jos());
        telemetry.addData("dus",virtualbar.dus());
        telemetry.update();



        super.run();
    }
}
