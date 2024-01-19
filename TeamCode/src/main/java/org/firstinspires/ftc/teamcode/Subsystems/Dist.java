package org.firstinspires.ftc.teamcode.Subsystems;


import com.arcrobotics.ftclib.command.button.Trigger;

public class Dist extends Trigger {

    boolean s,d;

    public Dist(boolean senr,boolean state){
        this.s = senr;
        this.d = state;
    }

    @Override
    public boolean get() {
        return false;
    }

    public boolean coaie(){
        return s && d;
    }
}
