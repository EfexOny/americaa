package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.util.Angle;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class LockPosCommand extends CommandBase {

    Pose2d target;
    SampleMecanumDrive drive;
    boolean gata=false;


    @Override
    public void initialize() {
        LockTo(target);
        super.initialize();
    }

    @Override
    public void execute() {
        drive.update();
    }

    public LockPosCommand(Pose2d target, SampleMecanumDrive drive){
        this.drive = drive;
        this.target = target;
    }
    @Override
    public void end(boolean interrupted) {
        drive.setMotorPowers(0, 0, 0, 0);
    }

    double xyP =1;

    public void LockTo(Pose2d targetPos){
        Pose2d currPos = drive.getPoseEstimate();
        Pose2d difference = targetPos.minus(currPos);
        Vector2d xy = difference.vec().rotated(-currPos.getHeading());

        double heading = Angle.normDelta(targetPos.getHeading()
                - Angle.normDelta(currPos.getHeading()));
        drive.setWeightedDrivePower(new Pose2d(xy,heading));
        gata = true;

    }

    @Override
    public boolean isFinished() {
        return gata;
    }
}