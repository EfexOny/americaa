package org.firstinspires.ftc.teamcode.Ops.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@TeleOp(name = "testAuto")
public class MamaArePula extends CommandOpMode {

    public static SampleMecanumDrive drive;
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

        vbar.Close();

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

    }

    @Override
    public void run() {
        schedule(new FollowTrajectoryCommand(dreapta),
        vbar.VJos(),
        vbar.Open()
        );
    }
}


