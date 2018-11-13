package theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 */
public class SPanel extends JPanel
{
    Image backgroundImage;

    public SPanel()
    {
        this(Theme.BACKGROUND_COLOR);
    }

    public SPanel(Color bgColor)
    {
        super();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(bgColor);
        backgroundImage = null;
        this.setOpaque (false);
    }
    public SPanel(Image backgroundImage)
    {
        super();
        this.backgroundImage = backgroundImage;
    }

    public void setBackground(BufferedImage backgroundImage){
        this.backgroundImage = backgroundImage;

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(backgroundImage == null) return;

        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

    }
}
