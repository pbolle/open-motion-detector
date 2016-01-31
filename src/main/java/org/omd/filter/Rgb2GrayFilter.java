package org.omd.filter;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Created by pbolle on 31.01.16.
 */
public class Rgb2GrayFilter implements Filter{
    @Override
    public void execute(FilterContext context) {
        Mat lastFrame = context.getLastFrame();
        Mat targetFrame = new Mat(lastFrame.rows(), lastFrame.cols(), CvType.CV_8SC1);
        Imgproc.cvtColor(lastFrame,targetFrame,Imgproc.COLOR_RGB2GRAY);
        context.putFrame("grayFrame", targetFrame);
    }
}
