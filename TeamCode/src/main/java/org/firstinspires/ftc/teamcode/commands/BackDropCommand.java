package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;

public class BackDropCommand extends SequentialCommandGroup {
    public static int delay1=1000;
    public BackDropCommand(Lift lift, Cuva cuva) {
        addCommands(
                cuva.close(),
                new WaitCommand(600),
                lift.goLift(440
                ),
                cuva.cuva_arunca(),
                new WaitCommand(1000),
                cuva.cuva_inapoi(),
                new WaitCommand(400),
                cuva.open(),
                new WaitCommand(500),
                cuva.close(),
                new WaitCommand(500),
                cuva.cuva_arunca(),
                new WaitCommand(500),
                cuva.open_insprejos(),
                new WaitCommand(500),
                lift.goLift(200),
                new WaitCommand(1000),
                lift.goLift(0)
                );
    }
}