package irdm.ui;

import irdm.indexers.IndexedImage;
import irdm.ui.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BatchImagesViewerFrame extends JFrame {

    public BatchImagesViewerFrame(ArrayList<IndexedImage> images) {
        this(images, images.size()+1);
    }

    public BatchImagesViewerFrame(ArrayList<IndexedImage> images, int limit) {
        this(images, limit, "Result");
    }

    public BatchImagesViewerFrame(ArrayList<IndexedImage> images, int limit, String windowName) {
        super(windowName);
        JPanel contentPane = new JPanel();
        this.setSize(Theme.WINDOW_WIDTH + 60, Theme.WINDOW_HEIGHT);

        JScrollPane scrollPane = new JScrollPane(contentPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        contentPane.setLayout(new GridLayout(limit / 3, 3, 30, 30));

        for (int i = 0; i < images.size() && i < limit; i++) {
            contentPane.add(new ImageViewerPanel(images.get(i).getImageIcon()));
        }

        this.setLocationRelativeTo(null);
        this.setContentPane(scrollPane);
    }
}
