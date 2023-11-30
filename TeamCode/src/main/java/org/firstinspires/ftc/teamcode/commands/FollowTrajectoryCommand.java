package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class FollowTrajectoryCommand extends CommandBase {

    TrajectorySequence trajectory;
    SampleMecanumDrive drive;


    public FollowTrajectoryCommand(TrajectorySequence trajectory) {
        this.trajectory = trajectory;
    }

    @Override
    public void initialize() {
        drive.followTrajectorySequence(trajectory);
    }

    @Override
    public void execute() {
        drive.update();
    }

    @Override
    public void end(boolean interrupted) {
        drive.setMotorPowers(0, 0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return  !drive.isBusy();
    }
}