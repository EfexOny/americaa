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
                        drive.trajectorySequenceBuilder(  new Pose2d(-35, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(-38,39, Math.toRadians(330)))
                                .lineToLinearHeading(new Pose2d(-45, 37, Math.toRadians(180)))
                                .setTangent(Math.toRadians(270))
                                .splineToLinearHeading(new Pose2d(-44.8, 12, Math.toRadians(180)), Math.toRadians(180))
                                .lineToLinearHeading(new Pose2d(7,12, Math.toRadians(180)))
                                .splineToConstantHeading(new Vector2d(53.5, 33), Math.toRadians(0))
                                .setTangent(Math.toRadians(270))
                                .lineToLinearHeading(new Pose2d(38,26,Math.toRadians(180)))
                                .splineToLinearHeading(new Pose2d(59,9,Math.toRadians(180)),Math.toRadians(0))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
