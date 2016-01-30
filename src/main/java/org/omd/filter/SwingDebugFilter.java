package org.omd.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omd.services.OpenCVHelper;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pbolle on 16.01.16.
 */
public class SwingDebugFilter implements Filter {
    private final Log log = LogFactory.getLog(getClass());
    JFrame jFrame;
    JLabel vidopanel;

    @Override
    public void execute(FilterContext context) {
        if (jFrame == null) {
            initJFrame();
        }
        Mat frame = context.getFirstFrame();
//        Mat frame = context.getFrame(FilterContext.INITIAL_FRAME);
//        Mat frame = context.getFrame("processedFrame");
//        Mat frame = context.getFrame("diffFrame");
        ImageIcon image = new ImageIcon(OpenCVHelper.Mat2bufferedImage(frame));
        vidopanel.setIcon(image);
        vidopanel.repaint();
    }

    private void initJFrame() {
        jFrame = new JFrame("Swing Debug Filter");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setLayout(null);
        jFrame.setBounds(0, 0, 1280, 720);
        vidopanel = new JLabel();
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1280, 720);
        panel.add(vidopanel);
        jFrame.getContentPane().add(panel);
        jFrame.repaint();
        jFrame.setVisible(true);
    }

}
