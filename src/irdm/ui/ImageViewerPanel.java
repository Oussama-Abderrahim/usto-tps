package irdm.ui;

import irdm.FileManager;
import irdm.ui.theme.SLabel;
import irdm.ui.theme.SPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageViewerPanel extends SPanel {

    private SLabel imageLabel;

    public ImageViewerPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        imageLabel = new SLabel("Put Your Image Here");
        imageLabel.setOpaque(false);
        add(imageLabel, BorderLayout.CENTER);
        repaint();
    }

    public ImageViewerPanel(BufferedImage image) {
        this();
        showImage(image);
    }

    public ImageViewerPanel(ImageIcon image) {
        this();
        showImage(image);
    }

    public void showImage(BufferedImage image)
    {
        showImage(new ImageIcon(image));
    }

    public void showImage(ImageIcon image)
    {
        imageLabel.setText("");
        imageLabel.setIcon(image);
        invalidate();
        repaint();
    }
}
