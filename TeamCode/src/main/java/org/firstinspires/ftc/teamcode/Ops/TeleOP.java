package org.firstinspires.ftc.teamcode.Ops;//package org.firstinspires.ftc.teamcode.Ops;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.Range;
//
//import org.firstinspires.ftc.teamcode.Subsystems.Cuva;
//import org.firstinspires.ftc.teamcode.Subsystems.Lift;
//import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;
//
//@TeleOp
//public class TeleOP extends LinearOpMode {
//
//    DcMotor lf,rf,rr,lr;
//    Virtualbar virtualbar;
//    Cuva cuva;
//    Lift lift;
//    int currentLevel = 0;
//    boolean isTurboOn = false;
//    boolean isXPressed = false;
//
//    @Override
//    public void runOpMode() {
//
//        lf = hardwareMap.get(DcMotor.class,"lf");
//        rf = hardwareMap.get(DcMotor.class,"rf");
//        rr = hardwareMap.get(DcMotor.class,"rr");
//        lr = hardwareMap.get(DcMotor.class,"lr");
//
//        rf.setDirection(DcMotorSimple.Direction.REVERSE);
//        lr.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        waitForStart();
//
//        while(opModeIsActive()){
//
//            ////////////Gamepad 1///////////
//
//            double drive  = -gamepad1. left_stick_y;
//            double strafe =  gamepad1. left_stick_x;
//            double turn   =  gamepad1.right_stick_x;
//
//            if (gamepad1.x) isTurboOn = !isTurboOn;
//
//            //Tehnic vorbind, ai putea da improve la response time daca ai da call la funtiile
//            //de setPower() doar la finalul while-ului, but, do we *really* need to?
//            lf.setPower(Range.clip(drive + turn + strafe, (isTurboOn ? -1 : -0.5), (isTurboOn ? 1 : 0.5)));
//            rf.setPower(Range.clip(drive - turn - strafe, (isTurboOn ? -1 : -0.5), (isTurboOn ? 1 : 0.5)));
//            lr.setPower(Range.clip(drive - turn + strafe, (isTurboOn ? -1 : -0.5), (isTurboOn ? 1 : 0.5)));
//            rr.setPower(Range.clip(drive + turn - strafe, (isTurboOn ? -1 : -0.5), (isTurboOn ? 1 : 0.5)));
//
//            // Iti dai seama ca asta e cod scris de Alex cand vezi identare de genu
//            if (gamepad1.dpad_up)    setMotorPowers(isTurboOn ?  1 :  0.5);
//            if (gamepad1.dpad_down)  setMotorPowers(isTurboOn ? -1 : -0.5);
//            if (gamepad1.dpad_left)  strafeLeft    (isTurboOn ?  1 :  0.5);
//            if (gamepad1.dpad_right) strafeRight   (isTurboOn ?  1 :  0.5);
//
//            ////////////Gamepad 2///////////
//
//            //TODO: deschiderea pe fiecare parte a ghiarei ar trebui sa fie independenta, pe butoane separate
//            if (gamepad2.a) virtualbar.toggleDirectClawState();
//            if (gamepad2.b) cuva      .toggleDirectClawState();
//            if (gamepad2.y) cuva      .toggleDirectArmState();
//
//            //S-ar putea sa isi ia un fuck-up daca apesi pe ambele dpad-uri consecutiv. Si e greu sa
//            //implementezi un failsafe la modul cum functioneaza java
//            if (gamepad2.dpad_up)   virtualbar.extendForward();
//            if (gamepad2.dpad_down) virtualbar.extendBack();
//
//            if (gamepad2.dpad_left) virtualbar.toggleVbarPositionDirect();
//
//            // Ridicare si coborare lift din bumpere
//            lift.controlLift(gamepad2.left_bumper ? -0.5 : gamepad2.right_bumper ? 0.5 : 0);
//
//            // Ridicare pe "dimensiuni". Sper ca asta voia. Iertati-ma dar sunt putin drogat.
//            if (gamepad2.x && !isXPressed) {
//                currentLevel = (currentLevel + 1) % 2;
//
//                if (lift.getLiftPosition() > lift.levelPositions[2])
//                    currentLevel = 0;
//
//                lift.setLiftLevel(currentLevel);
//            }
//            isXPressed = gamepad2.x;
//        }
//
//    }
//
//    void setMotorPowers(double lfp, double lrp, double rfp, double rrp) {
//        lf.setPower(lfp);
//        lr.setPower(lrp);
//        rf.setPower(rfp);
//        rr.setPower(rrp);
//    }
//
//    void setMotorPowers(double p) {
//        lf.setPower(p);
//        lr.setPower(p);
//        rf.setPower(p);
//        rr.setPower(p);
//    }
//
//    void strafeLeft(double power) {
//        setMotorPowers(-power, power, power, -power);
//    }
//
//    void strafeRight(double power) {
//        setMotorPowers(power, -power, -power, power);
//    }
//}
