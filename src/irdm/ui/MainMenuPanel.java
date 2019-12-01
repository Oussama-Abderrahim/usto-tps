package irdm.ui;

import irdm.DatabaseManager;
import irdm.indexers.ColorIndexerEngine;
import irdm.indexers.IndexedImage;
import irdm.ui.theme.Theme;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import irdm.ui.theme.SButton;
import irdm.ui.theme.SLabel;
import irdm.ui.theme.SPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MainMenuPanel extends SPanel {

    private SPanel containerPanel;
    private SPanel rightPanel;
    private SPanel leftPanel;
    private SPanel bottomButtonsPanel;
    private ImageViewerPanel imageViewerPanel;

    private IndexedImage importedImage;

    public MainMenuPanel() {
        super(new BorderLayout());

        containerPanel = new SPanel(new GridLayout(1, 2));
        containerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        rightPanel = new SPanel(new GridLayout(2, 1));
        leftPanel = new SPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        bottomButtonsPanel = new SPanel(new FlowLayout());

        imageViewerPanel = new ImageViewerPanel();

        SPanel buttonsPanel = new SPanel();

        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        SButton saveImageButton = new SButton("Save Image to DB");
        SButton compareColorsButton = new SButton("Comparaison couleur");
        SButton compareTextureButton = new SButton("Comparaison texture");

        saveImageButton.addActionListener(e -> saveImage());
        compareColorsButton.addActionListener(e -> compareColors());
        compareTextureButton.addActionListener(e -> compareTexture());


        buttonsPanel.add(SPanel.createContainerPanel(saveImageButton));
        buttonsPanel.add(SPanel.createContainerPanel(compareColorsButton));
        buttonsPanel.add(SPanel.createContainerPanel(compareTextureButton));

        rightPanel.add(imageViewerPanel, BorderLayout.NORTH);
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        SButton importImageButton = new SButton("Import a new Image");
        importImageButton.addActionListener(e -> onUploadImageButtonClick());
        bottomButtonsPanel.add(importImageButton);
        bottomButtonsPanel.add(new SButton("Visualise Database"));

        containerPanel.add(leftPanel);
        containerPanel.add(rightPanel);


        this.add(containerPanel, BorderLayout.CENTER);
        this.add(bottomButtonsPanel, BorderLayout.SOUTH);

    }

    private void compareTexture() {

    }

    private void compareColors() {
        ResultSet resultSet = DatabaseManager.getInstance().fetchAllImages();

        ArrayList<IndexedImage> indexedImages = new ArrayList<>();

        try {
            while (resultSet.next()) {
                indexedImages.add(
                        new IndexedImage(
                                resultSet.getString("path"),
                                resultSet.getString("COLOR_DESCRIPTOR"),
                                "")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        indexedImages.sort(Comparator.comparingInt(o -> importedImage.getColorDescriptor().distance(o.getColorDescriptor())));

        JFrame outputFrame = new JFrame("result");

        JPanel contentPane = new JPanel();

        contentPane.setLayout(new GridLayout(2, 3, 30, 30));

        for (int i = 0; i < 6 && i < indexedImages.size(); i++) {
            contentPane.add(new ImageViewerPanel(indexedImages.get(i).getImageIcon()));
        }

        outputFrame.setSize(Theme.WINDOW_WIDTH, Theme.WINDOW_HEIGHT);
        outputFrame.setLocationRelativeTo(null);
        outputFrame.setContentPane(contentPane);
        outputFrame.setVisible(true);

    }

    private void saveImage() {
        if (importedImage.saveToDB()) {
            JOptionPane.showMessageDialog(this, "Image successfully saved");
        } else {
            JOptionPane.showMessageDialog(this, "Image existe déja ou erreur.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onUploadImageButtonClick() {
        importedImage = new IndexedImage();
        importedImage.loadImageFromChooser();

        System.out.println(importedImage.getFilePath());

        imageViewerPanel.showImage(importedImage.getImageIcon());

        JFreeChart chart = ColorIndexerEngine.createChart(ColorIndexerEngine.getChartFromImage(importedImage.getImage()));
        leftPanel.add(new ChartPanel(chart));
        leftPanel.repaint();
    }

}
