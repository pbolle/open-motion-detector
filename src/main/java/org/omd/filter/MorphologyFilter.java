package org.omd.filter;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by pbolle on 30.01.16.
 */
public class MorphologyFilter implements Filter {
    @Override
    public void execute(FilterContext context) {
        Mat processedFrame = context.getLastFrame().clone();

        Mat erode = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
        Mat dilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8, 8));
        Mat openElem = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3), new Point(1, 1));
        Mat closeElem = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(7, 7), new Point(3, 3));

        Imgproc.threshold(processedFrame, processedFrame, 127, 255, Imgproc.THRESH_BINARY);
        Imgproc.morphologyEx(processedFrame, processedFrame, Imgproc.MORPH_OPEN, erode);
        Imgproc.morphologyEx(processedFrame, processedFrame, Imgproc.MORPH_OPEN, dilate);
        Imgproc.morphologyEx(processedFrame, processedFrame, Imgproc.MORPH_OPEN, openElem);
        Imgproc.morphologyEx(processedFrame, processedFrame, Imgproc.MORPH_CLOSE, closeElem);

        context.putFrame("morphologyFrame", processedFrame);
    }
}
