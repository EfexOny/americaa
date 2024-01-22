package org.firstinspires.ftc.teamcode.Ops.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.LockPos;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name = "pl")
public class Lock extends CommandOpMode {
    SampleMecanumDrive drive;

    Pose2d pos = new Pose2d(2,2);


    @Override
    public void initialize() {

        drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(pos);

        waitForStart();

        schedule(
                new SequentialCommandGroup(
                     new LockPos(new Pose2d(0,0),drive)
            )
        );
    }

    @Override
    public void run() {
        super.run();
        telemetry.addData("poz",drive.getPoseEstimate());
        telemetry.update();
    }
}
