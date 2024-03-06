package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;



@Config
public class DriveSubsystem extends SubsystemBase {
    double valoare=0;
      final MecanumDrive m_drive;
      public static double strafe = 0.8;
    double limit=1;

    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem(Motor lf, Motor rf, Motor lb, Motor rb) {
        m_drive = new MecanumDrive(lf, rf, lb, rb);
        m_drive.setRightSideInverted(true);


    }

    /**
     * Drives the robot using arcade controls.
     *
     * @param str the commanded strafe movement
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation movement
     */


    public void drive(double str, double fwd, double rot) {
        m_drive.driveRobotCentric(-str * strafe, -fwd * limit, -rot * limit,true);
    }

    //Field Centric
//    public void drive(double str, double fwd, double rot, double angle) {
//        m_drive.driveFieldCentric(-str * limit, -fwd * limit, -rot * limit, angle, true);
//    }


//    public void drive(double str, double fwd, double rot) {
//        m_drive.driveRobotCentric(-str * limit, -fwd * limit, -rot * limit);
//    }


    public void lat(boolean stg,boolean dr){
        if(dr)
            valoare = -0.6;
        if(stg)
            valoare = 0.6;

        m_drive.driveRobotCentric(valoare,0,0,true);

    }

    public Command turbo(double val){
       return new InstantCommand(()-> limit = val);
    }

    public void ver(boolean sus,boolean jos) {

//        if(jos)
            valoare = 0.6;
//        if(sus)
//            valoare = -0.6;

        m_drive.driveRobotCentric(0,valoare,0,true);

    }

    public void rot(boolean stg,boolean dr){
        if(dr)
            valoare = -0.4;
        if(stg)
            valoare = 0.4;

        m_drive.driveRobotCentric(0,0,valoare,true);
    }

    public Command dpad_vertical(boolean sus,boolean jos){
        return new RunCommand(() -> ver(sus,jos));
    }

    public Command Valer(double l1,double l2,double l3,boolean sq){
        return new RunCommand(() -> m_drive.driveRobotCentric(-l1,-l2,-l3,sq));
    }

    public Command dpad_orizontal(boolean stg,boolean dr){
        return new RunCommand(() -> lat(stg,dr));
    }

    public Command bumper_rotire(boolean stg,boolean dr){
        return new RunCommand(() -> rot(stg,dr));
    }



}

