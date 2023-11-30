package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class FollowTrajectoryCommand extends CommandBase {

    SampleMecanumDrive drive;
    TrajectorySequence trajectory;

    public FollowTrajectoryCommand(SampleMecanumDrive drive, TrajectorySequence trajectory) {
        this.drive = drive;
        this.trajectory = trajectory;

    }

    @Override
    public void execute() {
        drive.followTrajectorySequence(trajectory);
    }

    @Override
    public boolean isFinished() {
        return !drive.trajectorySequenceRunner.isBusy();
    }
}