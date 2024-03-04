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
                        drive.trajectorySequenceBuilder(  new Pose2d(-37, -60, Math.toRadians(90)))
                                .lineToConstantHeading(new Vector2d(-35,-37))
                                .lineToLinearHeading(new Pose2d(-47, -36, Math.toRadians(180)))
                                .setTangent(Math.toRadians(180))
                                .splineToLinearHeading(new Pose2d(-51, -12, Math.toRadians(180)), Math.toRadians(0))

                                .lineToLinearHeading(new Pose2d(13,-12, Math.toRadians(180)))
                                .setTangent(0)
                                .splineToConstantHeading(new Vector2d(52.5, -45), Math.toRadians(0))
//                                .lineToLinearHeading(new Pose2d(50,-25,Math.toRadians(185)))
                                .lineToConstantHeading(new Vector2d(41, -12))
                                .lineToConstantHeading(new Vector2d(61, -12))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
