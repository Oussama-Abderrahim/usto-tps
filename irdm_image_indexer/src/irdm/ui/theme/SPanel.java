package irdm.ui.theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 */
public class SPanel extends JPanel
{
    Image backgroundImage;


    public SPanel(LayoutManager layout, Color bgColor) {
        super(layout);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(layout);
        setBackground(bgColor);
        backgroundImage = null;
        this.setOpaque (true);
    }

    public SPanel(LayoutManager layout) {
        this(layout, Theme.BACKGROUND_COLOR);
    }

    public SPanel()
    {
        this(Theme.BACKGROUND_COLOR);
    }

    public SPanel(Color bgColor)
    {
        this(null, bgColor);
    }

    public SPanel(Image backgroundImage)
    {
        this();
        this.backgroundImage = backgroundImage;
    }

    public static SPanel createContainerPanel(Component component)
    {
        SPanel panel = new SPanel(new FlowLayout(), Theme.BACKGROUND_COLOR);


        panel.add(component);

        return panel;
    }

    public void setBackground(BufferedImage backgroundImage){
        this.backgroundImage = backgroundImage;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(backgroundImage == null) return;

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

    }
}
