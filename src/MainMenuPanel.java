import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import theme.SButton;
import theme.SLabel;
import theme.SPanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenuPanel extends SPanel {

    private SPanel containerPanel;
    private SPanel rightPanel;
    private SPanel leftPanel;
    private SPanel bottomButtonsPanel;
    private SLabel importedImageLabel;

    private IndexedImage importedImage;

    public MainMenuPanel() {
        super(new BorderLayout());

        containerPanel = new SPanel(new GridLayout(1, 2));
        containerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        rightPanel = new SPanel(new GridLayout(2, 1));
        leftPanel = new SPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        bottomButtonsPanel = new SPanel(new FlowLayout());
        SPanel importedImagePanel = new SPanel(new BorderLayout());
        importedImagePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        importedImageLabel = new SLabel("Put Your Image Here");
        importedImageLabel.setOpaque(false);
//        importedImageLabel.setIcon(FileManager.loadImage("placeholder", 400, 400));
        importedImagePanel.add(importedImageLabel, BorderLayout.CENTER);

        importedImagePanel.repaint();

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

        rightPanel.add(importedImagePanel, BorderLayout.NORTH);
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

    }

    private void saveImage() {
        if(importedImage.saveToDB()) {
            JOptionPane.showMessageDialog(this, "Image successfully saved");
        } else {
            JOptionPane.showMessageDialog(this, "Image existe déja ou erreur.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void onUploadImageButtonClick() {
        importedImage = new IndexedImage();
        importedImage.loadImageFromChooser();

        System.out.println(importedImage.getFilePath());

        importedImageLabel.setText("");
        importedImageLabel.setIcon(importedImage.getImageIcon());

        JFreeChart chart = ColorIndexerEngine.createChart(ColorIndexerEngine.getChartFromImage(importedImage.getImage()));
        leftPanel.add(new ChartPanel(chart));
        leftPanel.repaint();
    }

}
