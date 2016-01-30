package org.omd.filter;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;

/**
 * Created by pbolle on 16.01.16.
 */
public class BackgroundSubtractorFilter implements Filter {
    public static double learningRate = 0.005;
    private BackgroundSubtractorMOG2 mBGSub;

    public BackgroundSubtractorFilter() {
        mBGSub = Video.createBackgroundSubtractorMOG2();
    }

    @Override
    public void execute(FilterContext context) {
        Mat processedFrame = context.getLastFrame();
        Mat diffFrame = new Mat(processedFrame.size(), CvType.CV_8UC1);
        mBGSub.apply(processedFrame, diffFrame, learningRate);

        context.putFrame("diffFrame", diffFrame);
    }

}
