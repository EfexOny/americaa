package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Servus extends SubsystemBase {

    ElapsedTime elapsed_time;
    Servo servo;
    public Servus(Servo servo,String name,HardwareMap hardwareMap){
        servo = hardwareMap.get(Servo.class,name);
        elapsed_time = new ElapsedTime();
    }

    boolean USE = false;

    double maxVelocity,acceleration,initialPoistion,deceleration,targetPosition;
    double cuttrent_pos = initialPoistion;

    @Override
    public void periodic() {
        if(USE) {
            elapsed_time.startTime();
            if (elapsed_time.time() <= (2 * maxVelocity / acceleration)) {
                cuttrent_pos = initialPoistion + 0.5 * acceleration * Math.pow(elapsed_time.time(),2);
            } else if (elapsed_time.time() <= (2 * maxVelocity / acceleration) + ((targetPosition - initialPoistion) / maxVelocity)) {
                cuttrent_pos = initialPoistion + maxVelocity * (elapsed_time.time() - (2*maxVelocity/acceleration));
            }
            else {
                cuttrent_pos = targetPosition - 0.5 * deceleration * Math.pow(elapsed_time.time()-(2*maxVelocity / acceleration)-((targetPosition-initialPoistion)/maxVelocity),2);
            }
            USE=false;
        }
        else
        {
            servo.setPosition(cuttrent_pos);
        }
        elapsed_time.reset();
        super.periodic();

    }

    public void motionProfile(double initialPoistion,double maxVelocity,double acceleration,double targetPosition){
        this.initialPoistion =  initialPoistion;
        this.maxVelocity = maxVelocity;
        this.acceleration = acceleration;
        this.targetPosition = targetPosition;

        USE= true;
    }
}
