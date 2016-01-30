package org.omd.filter;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by pbolle on 16.01.16.
 */
public class FilterContext {
    private Vector<Point> detections = new Vector<>();
    private final Mat firstFrame;
    private Mat lastFrame;
    private Map<String, Mat> frameMap = new HashMap<String, Mat>();
    public static final String INITIAL_FRAME = "initialFrame";

    public FilterContext(Mat frame) {
        frameMap.put(INITIAL_FRAME, frame);
        this.lastFrame = frame;
        this.firstFrame = frame;
    }

    public Mat getLastFrame() {
        return lastFrame;
    }

    public Mat getFirstFrame() {
        return firstFrame;
    }

    public void releaseFrames() {
        for (String frameKey : frameMap.keySet()) {
            frameMap.get(frameKey).release();
        }
    }

    public Vector<Point> getDetections() {
        return detections;
    }

/*
    public Map<String, Mat> getFrameMap() {
        return frameMap;
    }
*/

    public void putFrame(String key, Mat frame) {
        lastFrame = frame;
        frameMap.put(key, frame);
    }

    public Mat getFrame(String key) {
        return frameMap.get(key);
    }
}
