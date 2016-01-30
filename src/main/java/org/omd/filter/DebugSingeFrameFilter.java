package org.omd.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pbolle on 24.01.16.
 */
public class DebugSingeFrameFilter implements Filter {
    private final Log log = LogFactory.getLog(getClass());

    @Override
    public void execute(FilterContext context) {
        // debug write to file
        if (!context.getDetections().isEmpty()) {
            Mat frame = context.getFirstFrame();

            writeMatToFile(frame);
        }
    }

    protected void writeMatToFile(Mat frame) {
        MatOfByte bytemat = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, bytemat);
        byte[] bytes = bytemat.toArray();

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss_SSS");

        FileOutputStream stream;
        try {
            stream = new FileOutputStream(new File("/home/pbolle/temp/test/" + dt.format(new Date()) + ".jpg"));
            try {
                stream.write(bytes);
            } finally {
                stream.close();
            }
        } catch (Exception e) {
            log.error("can't write file", e);
        }
    }
}
