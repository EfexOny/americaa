package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.Servo;

public class MotionProfile extends CommandBase {
    // Servo instance and its limits
    private  Servo servo;
    final double STEP = 0.01;
    private long startTime;
    private  double minAngle, maxAngle;

    private  double MAX_VEL, MAX_ACCEL, MAX_DECEL;

    private TrapezoidProfile profile;
    private double targetPos, currPos, currTime;

    public MotionProfile(Servo servo, double targetPos, double maxVel, double maxAccel, double maxDecel) {
        this.servo = servo;
        this.minAngle = 0;
        this.maxAngle = 1;
        this.targetPos = Math.min(Math.max(targetPos, minAngle), maxAngle);
        this.MAX_VEL = maxVel;
        this.MAX_ACCEL = maxAccel;
        this.MAX_DECEL = maxDecel;
    }

    @Override
    public void initialize() {
        super.initialize();
        TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(MAX_VEL,MAX_ACCEL);

        TrapezoidProfile.State initialState = new TrapezoidProfile.State(currPos, 0.0);
        TrapezoidProfile.State targetState = new TrapezoidProfile.State(targetPos, 0.0);

        profile = new TrapezoidProfile(constraints,targetState,initialState);
    }

    @Override
    public void execute() {
        currTime += STEP;
        double nextPos = ((TrapezoidProfile.State) profile.calculate(currTime)).position;

        nextPos = Math.min(Math.max(nextPos, minAngle), maxAngle);

        servo.setPosition(nextPos);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(targetPos - servo.getPosition()) <= 0.05;
    }
}
