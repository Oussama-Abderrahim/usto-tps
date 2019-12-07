package irdm.ui;

import irdm.indexers.ColorIndexerEngine;
import irdm.indexers.IndexedImage;
import irdm.indexers.TextureIndexerEngine;
import irdm.ui.theme.SButton;
import irdm.ui.theme.SPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuPanel extends SPanel {

    private SPanel leftPanel;
    private ImageViewerPanel imageViewerPanel;
    private IndexedImage importedImage;

    private SButton saveImageButton;
    private SButton compareColorsButton;
    private SButton compareTextureButton;


    private SPanel initRightPanel() {
        SPanel rightPanel = new SPanel(new GridLayout(2, 1));

        imageViewerPanel = new ImageViewerPanel();

        SPanel buttonsPanel = new SPanel();

        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        saveImageButton = new SButton("Save Image to DB", e -> saveImage());
        compareColorsButton = new SButton("Comparaison couleur", e -> compareColors());
        compareTextureButton = new SButton("Comparaison texture", e -> compareTexture());

        saveImageButton.setEnabled(false);
        compareColorsButton.setEnabled(false);
        compareTextureButton.setEnabled(false);

        buttonsPanel.add(SPanel.createContainerPanel(saveImageButton));
        buttonsPanel.add(SPanel.createContainerPanel(compareColorsButton));
        buttonsPanel.add(SPanel.createContainerPanel(compareTextureButton));

        rightPanel.add(imageViewerPanel, BorderLayout.NORTH);
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    private SPanel initLeftPanel() {
        SPanel leftPanel = new SPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        return leftPanel;
    }

    public MainMenuPanel() {
        super(new BorderLayout());

        SPanel containerPanel = new SPanel(new GridLayout(1, 2));
        containerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        this.leftPanel = initLeftPanel();
        SPanel rightPanel = initRightPanel();
        SPanel bottomButtonsPanel = new SPanel(new FlowLayout());

        bottomButtonsPanel.add(new SButton("Import a new Image", e -> onUploadImageButtonClick()));
        bottomButtonsPanel.add(new SButton("Visualise Database", e -> visualizeDatabase()));

        containerPanel.add(leftPanel);
        containerPanel.add(rightPanel);

        this.add(containerPanel, BorderLayout.CENTER);
        this.add(bottomButtonsPanel, BorderLayout.SOUTH);
    }

    private void visualizeDatabase() {
        ArrayList<IndexedImage> indexedImages = IndexedImage.fetchAllImages();

        (new BatchImagesViewerFrame(indexedImages)).setVisible(true);
    }

    private void compareTexture() {
        ArrayList<IndexedImage> indexedImages = TextureIndexerEngine.getInstance().fetchImagesBySimilarity(this.importedImage);

        (new BatchImagesViewerFrame(indexedImages)).setVisible(true);
    }


    private void compareColors() {

        ArrayList<IndexedImage> indexedImages = ColorIndexerEngine.getInstance().fetchImagesBySimilarity(this.importedImage);

        (new BatchImagesViewerFrame(indexedImages)).setVisible(true);
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

        saveImageButton.setEnabled(true);
        compareColorsButton.setEnabled(true);
        compareTextureButton.setEnabled(true);

        JFreeChart chart = ColorIndexerEngine.createChart(ColorIndexerEngine.getChartFromImage(importedImage.getImage()));
        leftPanel.add(new ChartPanel(chart));
        leftPanel.repaint();

    }

}
