package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class SpikeMarkCommand extends SequentialCommandGroup {
    public SpikeMarkCommand(SampleMecanumDrive drive, TrajectorySequence  c1,TrajectorySequence c2,TrajectorySequence c3, int detection,boolean park){
        super(
                new ConditionalCommand(
                        new FollowTrajectoryAsync(((detection == 1) ? c1 : (detection == 2 ? c2 : c3)), drive),
                        new WaitCommand(0),
                        () -> park
                )
        );
    }
}
