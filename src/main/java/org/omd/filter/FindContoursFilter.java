package org.omd.filter;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Created by pbolle on 17.01.16.
 */
public class FindContoursFilter implements Filter {
    public static double MIN_BLOB_AREA = 250;
    public static double MAX_BLOB_AREA = 3000;

    @Override
    public void execute(FilterContext context) {
        Mat frame = context.getLastFrame();

        Vector<Rect> array = detectionContours(frame);
        Iterator<Rect> it = array.iterator();
        Vector<org.opencv.core.Point> detections = context.getDetections();
        while (it.hasNext()) {
            Rect obj = it.next();

            int ObjectCenterX = (int) ((obj.tl().x + obj.br().x) / 2);
            int ObjectCenterY = (int) ((obj.tl().y + obj.br().y) / 2);

            org.opencv.core.Point pt = new org.opencv.core.Point(ObjectCenterX, ObjectCenterY);
            detections.add(pt);
        }
        // ///////
        if (array.size() > 0) {
            Iterator<Rect> it3 = array.iterator();
            while (it3.hasNext()) {
                Rect obj = it3.next();

                int ObjectCenterX = (int) ((obj.tl().x + obj.br().x) / 2);
                int ObjectCenterY = (int) ((obj.tl().y + obj.br().y) / 2);

                org.opencv.core.Point pt = new org.opencv.core.Point(ObjectCenterX, ObjectCenterY);

                Imgproc.rectangle(context.getFirstFrame(), obj.br(), obj.tl(), new Scalar(0, 255, 0), 2);
                Imgproc.circle(context.getFirstFrame(), pt, 1, new Scalar(0, 0, 255), 2);
            }
        }
    }

    private static Vector<Rect> detectionContours(Mat outmat) {
        Mat v = new Mat();
        Mat vv = outmat.clone();
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(vv, contours, v, Imgproc.RETR_LIST,
                Imgproc.CHAIN_APPROX_SIMPLE);

        int maxAreaIdx = -1;
        Rect r = null;
        Vector<Rect> rect_array = new Vector<Rect>();

        for (int idx = 0; idx < contours.size(); idx++) {
            Mat contour = contours.get(idx);
            double contourarea = Imgproc.contourArea(contour);
            if (contourarea > MIN_BLOB_AREA && contourarea < MAX_BLOB_AREA) {
                // MIN_BLOB_AREA = contourarea;
                maxAreaIdx = idx;
                r = Imgproc.boundingRect(contours.get(maxAreaIdx));
                rect_array.add(r);
            }

        }

        v.release();
        return rect_array;
    }
}
