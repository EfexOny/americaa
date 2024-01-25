package org.firstinspires.ftc.teamcode.Ops;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Dist;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;

//@Photon
@TeleOp(name="ruble")
public class tele extends Creier {

    @Override
    public void initialize() {


        initHardware();
        super.initialize();

        senzor = new Trigger(() -> virtualbar.dow1() && virtualbar.VbarState());
        senzor2 = new Trigger(() -> virtualbar.dow2() && virtualbar.VbarState());

        senzor.toggleWhenActive(
                new SequentialCommandGroup(
                new WaitCommand(1500),
                virtualbar.closesep(false)
                )
        );
        senzor2.toggleWhenActive(  new SequentialCommandGroup(
                        new WaitCommand(1500),
                        virtualbar.closesep(true)
                )
        );

        senzor.and(senzor2).toggleWhenActive(new SequentialCommandGroup(
                new WaitCommand(100),virtualbar.cekkt()
        ));

        avion = new GamepadButton(d1, GamepadKeys.Button.Y).toggleWhenPressed(cuva.stefan());

        gheara_principala = new GamepadButton(d2, GamepadKeys.Button.A).toggleWhenPressed(virtualbar.Open_wide());
        gheara_secundara = new GamepadButton(d2, GamepadKeys.Button.B).toggleWhenPressed(cuva.close(),cuva.open());

        vbarjos = new GamepadButton(d2,GamepadKeys.Button.DPAD_DOWN).whenPressed(virtualbar.vbarjos());
        vbarsus = new GamepadButton(d2,GamepadKeys.Button.DPAD_UP).whenPressed(virtualbar.VSus());


//

        auto_deposit = new GamepadButton(d2,GamepadKeys.Button.Y).toggleWhenPressed(cuva.mereuta(500),cuva.afterparty());
        lift_dreapta = new GamepadButton(d2,GamepadKeys.Button.RIGHT_BUMPER).toggleWhenPressed(cuva.mereuta(700),cuva.afterparty());
        cova1 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)!=0))
                .toggleWhenActive(cuva.mereuta(900), cuva.afterparty());

        cova2 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)!=0))
                .whileActiveContinuous(cuva.ridicare(1)).whenInactive(cuva.ridicare(0));
        lift_stanga = new GamepadButton(d2,GamepadKeys.Button.LEFT_BUMPER).whileHeld(cuva.ridicare(-1)).whenReleased(cuva.ridicare(0));

        vbar_idle = new GamepadButton(d2,GamepadKeys.Button.DPAD_LEFT).toggleWhenPressed(virtualbar.Vbar_Idle(),true );

        auto_grab = new GamepadButton(d2,GamepadKeys.Button.X).toggleWhenPressed(virtualbar.cekkt(),true);
        gheara_mereuta = new GamepadButton(d2,GamepadKeys.Button.DPAD_RIGHT).toggleWhenPressed(virtualbar.Close());


        turbo = new GamepadButton(d1,GamepadKeys.Button.X).toggleWhenPressed(drive.turbo(1),drive.turbo(1));

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
        telemetry.addData("left tficks",lift.getTciks());
        telemetry.addData("dreapta ticks",lift.rightticks());
        telemetry.addData("d1",virtualbar.dow1());
        telemetry.addData("d2",virtualbar.dow2());
        telemetry.addData("state",virtualbar.VbarState());
        telemetry.addData("pula",senzor.get());
        telemetry.update();
        super.run();
    }
}
