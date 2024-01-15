package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;



public class DriveSubsystem extends SubsystemBase {

    private final MecanumDrive m_drive;
    double limit=0.7;

    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem(Motor lf, Motor rf, Motor lb, Motor rb) {
        m_drive = new MecanumDrive(lf, rf, lb, rb);
    }

    /**
     * Drives the robot using arcade controls.
     *
     * @param str the commanded strafe movement
     * @param fwd the commanded forward movement
     * @param rot the commanded rotation movement
     */


    public void drive(double str, double fwd, double rot) {
        m_drive.driveRobotCentric(-str * limit, -fwd * limit, -rot * limit);
    }


    public void lat(boolean stg,boolean dr){
        double valoare=0;
        if(dr)
            valoare = -0.6;
        if(stg)
            valoare = 0.6;

        m_drive.driveRobotCentric(valoare,0,0);

    }

    public Command turbo(double val){
       return new InstantCommand(()-> limit = val);
    }

        public void ver(boolean sus,boolean jos){

        double valoare=0;
        if(jos)
            valoare = 0.6;
        if(sus)
            valoare = -0.6;

        m_drive.driveRobotCentric(0,valoare,0);

    }

    public void rot(boolean stg,boolean dr){
        double valoare=0;
        if(dr)
            valoare = -0.4;
        if(stg)
            valoare = 0.4;

        m_drive.driveRobotCentric(0,0,valoare);
    }

    public Command dpad_vertical(boolean sus,boolean jos){
        return new InstantCommand(() -> ver(sus,jos));
    }

    public Command dpad_orizontal(boolean stg,boolean dr){
        return new InstantCommand(() -> lat(stg,dr));
    }

    public Command bumper_rotire(boolean stg,boolean dr){
        return new InstantCommand(() -> rot(stg,dr));
    }



}

