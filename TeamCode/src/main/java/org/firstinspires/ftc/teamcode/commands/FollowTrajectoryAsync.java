package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class FollowTrajectoryAsync extends CommandBase {

    TrajectorySequence trajectory;
    SampleMecanumDrive drive;

    @Override
    public void initialize() {
        drive.followTrajectorySequenceAsync(trajectory);
    }

    @Override
    public void execute() {
        drive.update();
    }

    public FollowTrajectoryAsync(TrajectorySequence trajectory, SampleMecanumDrive drive){
        this.trajectory = trajectory;
        this.drive = drive;
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