package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(47, 47, Math.toRadians(206.4298901690336), Math.toRadians(206.4298901690336), 13.32)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 60, Math.toRadians(270)))
                                .lineTo(new Vector2d(-35,36))
                                .lineToLinearHeading(new Pose2d(-54,36, Math.toRadians(180)))
                                .lineTo(new Vector2d(-44,15 ))
                                .setReversed(true)
                                .setTangent(Math.toRadians(270))
                                .lineToConstantHeading(new Vector2d(35, 14))
//                                .lineToLinearHeading(new Pose2d(-35,18,Math.toRadians(90)))
//                                necomplicat stanga
//                                .lineToLinearHeading(new Pose2d(-40,18,Math.toRadians(120)))
//
//                                .lineToLinearHeading(new Pose2d(-34,18,Math.toRadians(45)))
//
//                                .setTangent(Math.toRadians(180))
//                                .splineToLinearHeading(new Pose2d(-65,24,Math.toRadians(180)),Math.toRadians(180))
//
//                                .setReversed(true)
//                                .splineToLinearHeading(new Pose2d(45,10,Math.toRadians(180)),Math.toRadians(0))

                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
