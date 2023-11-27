package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
public class Lift extends SubsystemBase {

    DcMotor left,right;
    public static double kP = 0.002;
    public static double kI = 0;
    Timing.Timer timp;
    public static double kD = 0;
    public static double kF = 0.0008;
    public static int liftTargetPos = 0;
    public static PIDController pid;
    public static double tele = 0;
//    public int[] levelPositions = {0, 1000, 900};

    public Lift(HardwareMap hardwareMap){

        left = hardwareMap.get(DcMotor.class,"stanga_lift");
        right = hardwareMap.get(DcMotor.class,"dreapta_lift");

        left.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pid = new PIDController(kP, kI, kD);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

//        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pid.setPID(kP, kI, kD);
    }

    public int getTciks(){
        return left.getCurrentPosition();
    }
    public int getLiftPosition() {
        return left.getCurrentPosition();
    }
    public void controlLift(double power){
        if(power == 0)
            liftTargetPos = getLiftPosition();

        left.setPower(power);
        right.setPower(power);
    }


    public void setLiftLevel(int level) {
        liftTargetPos = level;
//        liftTargetPos = levelPositions[level];
    }

    @Override
    public void periodic() {
        double power = pid.calculate(left.getCurrentPosition(),liftTargetPos);
        double ff = kF * left.getCurrentPosition();

        left.setPower(power + ff);
        right.setPower(power + ff);

        super.periodic();
    }

    public Command goLift(int pula){
        return new InstantCommand(() -> liftTargetPos = pula);
    }

    public Command ridicare(int joystick){
        return new InstantCommand(() -> {
            if(liftTargetPos>900)
                liftTargetPos=900;
            if(liftTargetPos<0)
                liftTargetPos=0;
            liftTargetPos = liftTargetPos + joystick;
        });
    }
}
