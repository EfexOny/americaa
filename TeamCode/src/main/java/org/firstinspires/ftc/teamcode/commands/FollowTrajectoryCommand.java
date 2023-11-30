package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Ops.auto.MamaArePula;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class FollowTrajectoryCommand extends CommandBase {

    TrajectorySequence trajectory;

    public FollowTrajectoryCommand(TrajectorySequence trajectory) {
        this.trajectory = trajectory;
    }

    @Override
    public void initialize() {
        MamaArePula.drive.followTrajectorySequence(trajectory);
    }

    @Override
    public void execute() {
        MamaArePula.drive.update();
    }

    @Override
    public void end(boolean interrupted) {
        MamaArePula.drive.setMotorPowers(0, 0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        return Thread.currentThread().isInterrupted() || !MamaArePula.drive.isBusy();
    }
}