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
        Mat processedFrame = context.getLastFrame().clone();
        Mat diffFrame = new Mat(processedFrame.size(), CvType.CV_8UC1);
        processFrame(processedFrame, diffFrame, mBGSub);

        context.putFrame("processedFrame", processedFrame);
        context.putFrame("diffFrame", diffFrame);
    }

    // background substractionMOG2
    public static void processFrame(Mat mRgba,
                                    Mat mFGMask, BackgroundSubtractorMOG2 mBGSub) {
        // GREY_FRAME also works and exhibits better performance
        mBGSub.apply(mRgba, mFGMask, learningRate);

        Imgproc.cvtColor(mFGMask, mRgba, Imgproc.COLOR_GRAY2BGRA, 0);

        Mat erode = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(
                8, 8));
        Mat dilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new Size(8, 8));

        Mat openElem = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new Size(3, 3), new Point(1, 1));
        Mat closeElem = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new Size(7, 7), new Point(3, 3));

        Imgproc.threshold(mFGMask, mFGMask, 127, 255, Imgproc.THRESH_BINARY);
        Imgproc.morphologyEx(mFGMask, mFGMask, Imgproc.MORPH_OPEN, erode);
        Imgproc.morphologyEx(mFGMask, mFGMask, Imgproc.MORPH_OPEN, dilate);
        Imgproc.morphologyEx(mFGMask, mFGMask, Imgproc.MORPH_OPEN, openElem);
        Imgproc.morphologyEx(mFGMask, mFGMask, Imgproc.MORPH_CLOSE, closeElem);

    }

}
