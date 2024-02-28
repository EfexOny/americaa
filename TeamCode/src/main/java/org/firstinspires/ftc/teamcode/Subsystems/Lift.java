package org.firstinspires.ftc.teamcode.Subsystems;

import static com.arcrobotics.ftclib.util.MathUtils.clamp;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

@Config
public class Lift extends SubsystemBase {

    public static double high=0.7;

    public static double low=-0.2;
    DcMotor left,right;
    public static double kP = 0.0035;
    TouchSensor magnetic;
    public static double kI = 0;
    Timing.Timer timp;
    public static double kD = 0;
    public static double kF = 0;
    public static int liftTargetPos = 0;
    boolean usePid;
    boolean down=false;
    public static PIDController pid;
    public static double tele = 0;
//    public int[] levelPositions = {0, 1000, 900};

    public Lift(HardwareMap hardwareMap){

        left = hardwareMap.get(DcMotor.class,"stanga_lift");
        right = hardwareMap.get(DcMotor.class,"dreapta_lift");

        magnetic = hardwareMap.get(TouchSensor.class,"magnet");

//        left.setDirection(DcMotorSimple.Direction.REVERSE);
        right.setDirection(DcMotorSimple.Direction.REVERSE);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pid = new PIDController(kP, kI, kD);

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        pid.setPID(kP, kI, kD);
    }

    public void resetTicks(){
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftTargetPos = 0;
    }
    public int getTciks(){
        return left.getCurrentPosition();
    }
    public int rightticks(){
        return right.getCurrentPosition();
    }
    public int getLiftPosition() {
        return liftTargetPos;
    }
    public void controlLift(double power){
        if(power == 0)
            liftTargetPos = getLiftPosition();

        left.setPower(power);
        right.setPower(power);
    }


    public boolean check(){
        return magnetic.isPressed();
    }

    public void setLiftLevel(int level) {
        liftTargetPos = level;
//        liftTargetPos = levelPositions[level];
    }

    @Override
    public void periodic() {

        double power = pid.calculate(-left.getCurrentPosition(), liftTargetPos);
        double output = clamp(power , low, high);

        left.setPower(output);
        right.setPower(output);

        super.periodic();
    }



    public Command norma(){
      return new InstantCommand(
              () ->   {liftTargetPos = liftTargetPos - 100;
              down =true;}

      );
    }

    public Command change(boolean s){
        return new InstantCommand(() -> down = s);
    }

    public boolean isDown(){
        return down;
    }

    public void aFost(){
        down = false;
    }

    public Command manual(int s){
        return new InstantCommand( () -> {

            if(s==0)
            {
                left.setPower(s);right.setPower(s);
                liftTargetPos=left.getCurrentPosition();
                usePid = true;
            }
            else
            {
                left.setPower(s);right.setPower(s);
               usePid=false;
            }

        });
    }

    public Command goLift(int p){
        return new InstantCommand(() -> liftTargetPos = p);
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
