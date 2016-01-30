package org.omd.filter;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

/**
 * Created by pbolle on 23.01.16.
 */
public class Source {
    VideoCapture camera;

    public Source() {
        camera = new VideoCapture();
    }

    public boolean open() {
        return camera.open(0);
//        camera.open(1);
//      return  camera.open(2);
//        return camera.open("rtsp://admin:instar@192.168.178.39:554/11");
    }

    public boolean read(Mat buffer) {
        boolean readable = camera.read(buffer);
        if (!readable) {
            // retry
            if (open()) {
                readable = camera.read(buffer);
            }
        }
        return readable;
    }
}
