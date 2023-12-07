package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Canvas;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Config
public class RedPropThreshold implements VisionProcessor {
    Mat testMat = new Mat();
    Mat highMat = new Mat();
    Mat lowMat = new Mat();
    Mat finalMat = new Mat();
    double averagedMiddleBox;
    double averagedRightBox;
    public static double redThreshold = 0.025;

    String outStr = "left"; //default

    public static int x1=0,x2=50,y1=150,y2=250,x3=0,x4=640,y3=320,y4=480;




    static final Rect RIGHT_RECTANGLE = new Rect(
            new Point(x1, y1 ),
            new Point(x2, y2)
    );

    static final Rect MIDDLE_RECTANGLE = new Rect(
            new Point(x3, y3),
            new Point(x4,y4 )
    );

    @Override
    public void init(int width, int height, CameraCalibration calibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        Imgproc.cvtColor(frame, testMat, Imgproc.COLOR_RGB2HSV);


    Scalar lowHSVRedLower = new Scalar(0, 100, 20);
    Scalar lowHSVRedUpper = new Scalar(10, 255, 255);

    Scalar redHSVRedLower = new Scalar(160, 100, 20);
    Scalar highHSVRedUpper = new Scalar(180, 255, 255);

        Core.inRange(testMat, lowHSVRedLower, lowHSVRedUpper, lowMat);
        Core.inRange(testMat, redHSVRedLower, highHSVRedUpper, highMat);

        testMat.release();

        Core.bitwise_or(lowMat, highMat, finalMat);

        lowMat.release();
        highMat.release();

        double rightBox = Core.sumElems(finalMat.submat(RIGHT_RECTANGLE)).val[0];
        double midBox = Core.sumElems(finalMat.submat(MIDDLE_RECTANGLE)).val[0];

        averagedMiddleBox  = midBox  / MIDDLE_RECTANGLE.area()  / 255;
        averagedRightBox = rightBox / RIGHT_RECTANGLE.area() / 255; //Makes value [0,1]


        if(averagedRightBox > redThreshold){
            outStr = "right";
        }else if(averagedMiddleBox > redThreshold){
            outStr = "middle";
        }else{
            outStr = "left";
        }

        finalMat.copyTo(frame);

        return null;




    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    public String getPropPosition(){  //Returns postion of the prop in a String
        return outStr;
    }

    public double coaie(){return averagedMiddleBox;}
    public double coaie2(){return averagedRightBox;}


}