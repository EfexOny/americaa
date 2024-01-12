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
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 60, Math.toRadians(270)))
                                .lineToLinearHeading(new Pose2d(-36, 36, Math.toRadians(330)))
                                .turn(Math.toRadians(-70))
                                .turn(Math.toRadians(-70))
                                .lineToLinearHeading(new Pose2d(-35, 8, Math.toRadians(270)))
                                .turn(Math.toRadians(90))
                                .lineTo(new Vector2d(16,8))
                                .splineToLinearHeading(new Pose2d(54, 35, Math.toRadians(170)), Math.toRadians(0))
//                                .lineTo(new Vector2d(50, 20))
//                                .lineToLinearHeading(new Pose2d(65, 20, Math.toRadians(200)))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
