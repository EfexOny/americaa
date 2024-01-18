package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.button.Trigger;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Dist extends Trigger {

    DistanceSensor s;boolean d;
    public Dist(DistanceSensor senzor,boolean down){
        this.s = senzor;
        this.d = down;
    }

    @Override
    public boolean get() {
        return s.getDistance(DistanceUnit.CM) < 7 && d;
    }
}
