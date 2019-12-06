package irdm.ui;

import irdm.indexers.IndexedImage;
import irdm.ui.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BatchImagesViewerFrame extends JFrame {

    public BatchImagesViewerFrame(ArrayList<IndexedImage> images)
    {
        super("Result");
        JPanel contentPane = new JPanel();

        contentPane.setLayout(new GridLayout(2, 3, 30, 30));

        for (int i = 0; i < 6 && i < images.size(); i++) {
            contentPane.add(new ImageViewerPanel(images.get(i).getImageIcon()));
        }

        this.setSize(Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setContentPane(contentPane);
    }
}
