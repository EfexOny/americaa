package org.firstinspires.ftc.teamcode.Ops.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.commands.LockPosCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@TeleOp(name = "test lock")
public class testLock extends CommandOpMode {

    SampleMecanumDrive drive;
    DcMotorEx lf,rf,rb,lb;
    TrajectorySequence start;

    @Override
    public void initialize() {
        drive = new SampleMecanumDrive(hardwareMap);
        lf = hardwareMap.get(DcMotorEx.class,"lf");
        rf = hardwareMap.get(DcMotorEx.class,"rf");
        lb = hardwareMap.get(DcMotorEx.class,"lb");
        rb = hardwareMap.get(DcMotorEx.class,"rb");


        drive.setPoseEstimate(new Pose2d(0,0));
        waitForStart();

        schedule(
                new LockPosCommand(new Pose2d(0,0),drive)
        );
    }

    @Override
    public void run() {
        telemetry.addData("lf",lf.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("rf",rf.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("lb",lb.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.addData("rb",rb.getCurrent(CurrentUnit.MILLIAMPS));
        telemetry.update();

        super.run();
    }
}
