package org.omd.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omd.filter.FilterContext;
import org.omd.filter.Source;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pbolle on 16.01.16.
 */
public class FilterThread extends Thread {
    private final Log log = LogFactory.getLog(getClass());
    private FilterChain filterChain;

    public void run() {
        //init Source
        Source source = new Source();

        while (true) {
            try {

                Mat inFrame = new Mat();
                if (source.read(inFrame)) {
                    FilterContext context = new FilterContext(inFrame);
                    filterChain.execute(context);
                    context.releaseFrames();

                } else {
                    log.warn("problem reading camera source");
                }

                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    log.info("thread interruped", e);
                }
            } catch (Throwable t) {
                log.error("error in Filterprocessing", t);
            }

        }
    }

    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }
}
