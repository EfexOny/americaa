package org.firstinspires.ftc.teamcode.Ops.auto;



import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.Set;


@TeleOp(name = "testAuto")
public class mamaArePula extends CommandOpMode {

    SampleMecanumDrive drive;
    Virtualbar vbar;
    TrajectorySequence stanga;
    TrajectorySequence   centru;
    TrajectorySequence dreapta;
    Pose2d startBlue = new Pose2d(-36, 60, Math.toRadians(270));
    Pose2d startRed = new Pose2d(-36, -60, Math.toRadians(90));


    @Override
    public void initialize() {

        drive = new SampleMecanumDrive(hardwareMap);
        vbar = new Virtualbar(hardwareMap);

        drive.setPoseEstimate(startBlue);

        stanga = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(-36, 40))
                .strafeTo(new Vector2d(-35, 40))
                .turn(Math.toRadians(58))
                .build();

        centru = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .forward(24)
                .build();

        dreapta = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .lineTo(new Vector2d(-36, 42.5))
                .turn(Math.toRadians(-38))
                .build();

        schedule(vbar.Vbar_Idle());
        schedule(vbar.Close());
    }

    @Override
    public void run() {
        schedule(new SequentialCommandGroup(
                new FollowTrajectoryCommand(drive, dreapta),
                vbar.VJos(),
                vbar.Open()
        ));
    }
}


