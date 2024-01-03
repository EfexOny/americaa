package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.button.Trigger;
import com.qualcomm.robotcore.hardware.DigitalChannel;


public class SenzorTrigger extends Trigger {


    public static boolean getSt(DigitalChannel b) {
        return b.getState();
    }
}
