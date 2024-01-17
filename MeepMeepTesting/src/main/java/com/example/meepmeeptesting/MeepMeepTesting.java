package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(52.48291908330528, 52.48291908330528, Math.toRadians(206.4298901690336), Math.toRadians(206.4298901690336), 13.32)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(11, 60, Math.toRadians(270)))
                                .splineToLinearHeading(new Pose2d(55,37,Math.toRadians(195)),Math.toRadians(0))
                                .lineToConstantHeading(new Vector2d(40,11))
                                .setReversed(true)
                                .strafeTo(new Vector2d(37,38))
                                .splineToLinearHeading(new Pose2d(59,65,Math.toRadians(195)),Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
