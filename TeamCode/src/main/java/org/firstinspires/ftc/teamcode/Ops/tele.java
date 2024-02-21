package org.firstinspires.ftc.teamcode.Ops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;

@TeleOp(name="ruble")
public class tele extends Creier {

    @Override
    public void initialize() {

        initHardware();
        super.initialize();

        nospam = new GamepadButton(d2,GamepadKeys.Button.BACK).whenPressed(lift.norma());
        spame = new ButtonReader(d2,GamepadKeys.Button.BACK);

        avion = new GamepadButton(d1, GamepadKeys.Button.Y).toggleWhenPressed(cuva.stefan());

        gheara_principala = new GamepadButton(d2, GamepadKeys.Button.A).toggleWhenPressed(virtualbar.Open_wide());
        gheara_secundara = new GamepadButton(d2, GamepadKeys.Button.B).toggleWhenPressed(cuva.close(),cuva.open());

        vbarjos = new GamepadButton(d2,GamepadKeys.Button.DPAD_DOWN).whenPressed(virtualbar.vbarjos());
        vbarsus = new GamepadButton(d2,GamepadKeys.Button.DPAD_UP).whenPressed(virtualbar.VSus());

        reset = new GamepadButton(d1,GamepadKeys.Button.B).whenPressed(new InstantCommand(()-> drift.setPoseEstimate(new Pose2d(0,0))));

        auto_deposit = new GamepadButton(d2,GamepadKeys.Button.Y).toggleWhenPressed(cuva.mereuta(450),cuva.afterparty());
        lift_dreapta = new GamepadButton(d2,GamepadKeys.Button.RIGHT_BUMPER).toggleWhenPressed(cuva.mereuta(750),cuva.afterparty());
        cova1 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)!=0))
                .toggleWhenActive(cuva.mereuta(750), cuva.afterparty());

        cova2 = new Trigger(() -> (d2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)!=0))
                .whileActiveContinuous(cuva.ridicare(1)).whenInactive(cuva.ridicare(0));
        lift_stanga = new GamepadButton(d2,GamepadKeys.Button.LEFT_BUMPER).whileHeld(cuva.ridicare(-1)).whenReleased(cuva.ridicare(0));

        vbar_idle = new GamepadButton(d2,GamepadKeys.Button.DPAD_LEFT).toggleWhenPressed(virtualbar.Vbar_Idle(),true );

        auto_grab = new GamepadButton(d2,GamepadKeys.Button.X).toggleWhenPressed(virtualbar.cekkt(),true);
        gheara_mereuta = new GamepadButton(d2,GamepadKeys.Button.DPAD_RIGHT).toggleWhenPressed(virtualbar.Close());


        turbo = new GamepadButton(d1,GamepadKeys.Button.X).toggleWhenPressed(drive.turbo(1),drive.turbo(1));

        dpad1 = new GamepadButton(d1,GamepadKeys.Button.DPAD_UP).whileHeld(
                drive.Valer(0,0.5,0,true));
//                .whenInactive(drive.dpad_vertical(false,false));

        dpad2 = new GamepadButton(d1,GamepadKeys.Button.DPAD_DOWN).whileHeld( drive.Valer(0,-0.5,0,true));
//                .whenInactive(drive.dpad_vertical(false,false));
        dpad3 = new GamepadButton(d1,GamepadKeys.Button.DPAD_LEFT).whileHeld( drive.Valer(-0.5,0,0,true));
//                .whenInactive(drive.dpad_orizontal(false,false));
        dpad4 = new GamepadButton(d1,GamepadKeys.Button.DPAD_RIGHT).whileHeld( drive.Valer(0.5,0,0,true));
//                .whenInactive(drive.dpad_vertical(false,false));

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

        telemetry.addData("Odom angle: ",drift.getPoseEstimate().getHeading());
        telemetry.addData("Target pos: ",lift.getLiftPosition());
        telemetry.addData("Motor lift ticks: ",lift.getTciks());
        telemetry.addData("Sensor dr: ",virtualbar.dow1());
        telemetry.addData("Sensor stg: ",virtualbar.dow2());
        telemetry.addData("Vbar state: ",virtualbar.VbarState());
        telemetry.addData("Vbar down: ",senzor.get());
        telemetry.addData("touch sensor: `",lift.check());
        telemetry.addData("Lift isDown: ",lift.isDown());
        telemetry.addData("Dist1 : ",virtualbar.down1());
        telemetry.addData("Dist2 : ",virtualbar.down2());
        telemetry.update();

        super.run();
    }
}
