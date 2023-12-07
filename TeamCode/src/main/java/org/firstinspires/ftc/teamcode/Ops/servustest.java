package org.firstinspires.ftc.teamcode.Ops;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Subsystems.Servus;

@Disabled
@Config
@TeleOp(name="test")
public class servustest extends Creier {

    Servus servus;
    Servo mama;
    @Override
    public void initialize() {
        servus = new Servus(mama,"mama",hardwareMap);
        super.initialize();
    }
}
