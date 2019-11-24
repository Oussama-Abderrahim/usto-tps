import theme.SButton;
import theme.SLabel;
import theme.SPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MainMenuPanel extends SPanel {

    private SPanel containerPanel;
    private SPanel rightPanel;
    private SPanel leftPanel;
    private SPanel bottomButtonsPanel;
    private SPanel importedImagePanel;

    public MainMenuPanel() {
        super(new BorderLayout());

        containerPanel = new SPanel(new GridLayout(1, 2));
        rightPanel = new SPanel(new GridLayout(2, 1));
        leftPanel = new SPanel(new BorderLayout());
        bottomButtonsPanel = new SPanel(new FlowLayout());
        importedImagePanel = new SPanel();

        FileManager.loadImage("placeholder", 400, 400);

        BufferedImage placeHolderImg = (BufferedImage) FileManager.loadImage("placeholder", 400, 400).getImage();
        importedImagePanel.setBackground(placeHolderImg);

        SPanel buttonsPanel = new SPanel();

        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 10));

        SButton importImageButton = new SButton("Import a new Image");
        importImageButton.addActionListener(e -> onUploadImageButtonClick());
        buttonsPanel.add(SPanel.createContainerPanel(importImageButton));
        buttonsPanel.add(SPanel.createContainerPanel(new SButton("Comparaison couleur")));
        buttonsPanel.add(SPanel.createContainerPanel(new SButton("Comparaison texture")));

        rightPanel.add(importedImagePanel, BorderLayout.NORTH);
        rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

        bottomButtonsPanel.add(new SButton("Import A new Image"));
        bottomButtonsPanel.add(new SButton("Visualise Database"));

        containerPanel.add(leftPanel);
        containerPanel.add(rightPanel);


        this.add(containerPanel, BorderLayout.CENTER);
        this.add(bottomButtonsPanel, BorderLayout.SOUTH);

    }

    private void onUploadImageButtonClick() {
        BufferedImage image = (BufferedImage) FileManager.loadImageFromChooser(400, 400).getImage();

        importedImagePanel.setBackground(image);

        leftPanel.add(IndexerEngine.getChartFromImage(image), BorderLayout.CENTER);
    }

}
