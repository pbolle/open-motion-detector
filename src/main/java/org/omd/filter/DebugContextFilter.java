package org.omd.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by pbolle on 24.01.16.
 */
public class DebugContextFilter extends DebugSingeFrameFilter {
    private final Log log = LogFactory.getLog(getClass());

    @Override
    public void execute(FilterContext context) {
        // debug write to file
        if (!context.getDetections().isEmpty()) {
            Mat frame = context.getFirstFrame();
            Mat target = new Mat(frame.rows() * 2, frame.cols(), CvType.CV_8UC3);

            // 1 copy org
            Mat submat = target.submat(0, frame.rows(), 0, frame.cols());
            frame.copyTo(submat);
            submat.release();

            // 2 copy diffFrame
            submat = target.submat(frame.rows(), frame.rows()*2, 0, frame.cols());
            Mat diffFrame = context.getFrame("diffFrame");
            Mat diffTarget = new Mat(diffFrame.rows(), frame.cols(), CvType.CV_8UC3);
            Imgproc.cvtColor(diffFrame,diffTarget,Imgproc.COLOR_GRAY2BGR);
            diffTarget.copyTo(submat);
            submat.release();
            diffTarget.release();

            writeMatToFile(target);
        }
    }
}
