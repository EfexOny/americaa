package org.firstinspires.ftc.teamcode.Ops;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class pidtune extends OpMode {

    DcMotor left,right;
    public static double kP = 0.002 ;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0.0008;
    public static int liftTargetPos = 0;
    public static PIDController pid;

    @Override
    public void init() {
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

        pid.setPID(kP, kI, kD);
    }

    @Override
    public void loop() {
        double power = pid.calculate(left.getCurrentPosition(),liftTargetPos);
        double ff = kF * left.getCurrentPosition();

        left.setPower(power + ff);
        right.setPower(power + ff);

        telemetry.addData("stanga", left.getCurrentPosition());
        telemetry.addData("dreapta", right.getCurrentPosition());

        telemetry.update();
    }
}
