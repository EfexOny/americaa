package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Virtualbar;

public class AutoCloseGheara extends CommandBase {

    private Command m_ont,m_onf;
    boolean condi;
    private Command selected;
    DistanceSensor senzor;Virtualbar vbar;
    public AutoCloseGheara(Command onTrue, Command onFalse,boolean bool){
      this.m_ont = onTrue;
      this.m_onf = onFalse;
      this.condi = bool;
    }

    @Override
    public void initialize() {
        if(condi){
            selected = m_ont;
        }else{
            selected = m_onf;
        }
        selected.initialize();
    }

    @Override
    public void execute() {
       selected.execute();
    }

    public boolean isFinished(){
        return true;
    }
}
