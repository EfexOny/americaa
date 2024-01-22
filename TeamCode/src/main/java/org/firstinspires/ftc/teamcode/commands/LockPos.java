package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.util.Angle;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class LockPos extends CommandBase {

    Pose2d poz;
    SampleMecanumDrive drive;
   public static double xyP = 1;
    public static double headingP = 1;

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        LockTo(poz);
        drive.update();
    }


    public LockPos(Pose2d pos, SampleMecanumDrive drive){
        this.poz = pos;
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

    public void LockTo(Pose2d Target){
        Pose2d currPos = drive.getPoseEstimate();
        Pose2d difference = Target.minus(currPos);
        Vector2d xy = difference.vec().rotated(-currPos.getHeading());

        double heading = Angle.normDelta(Target.getHeading() -Angle.normDelta(currPos.getHeading()));
        drive.setWeightedDrivePower(new Pose2d(xy.times(xyP),heading*headingP));
    }

}