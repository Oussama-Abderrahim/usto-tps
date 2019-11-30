package irdm.ui.theme;

import javax.swing.*;
import java.awt.*;

public class SLabel extends JLabel
{
    public SLabel()
    {
        this("");
    }

    public SLabel(String text) {
        this(text, Theme.FONT_DEFAULT_LARGE);
    }
    public SLabel(String text, Font font) {
        super(text);
        setFont(font);
        //setForeground(Theme.FONT_DEFAULT_COLOR);
        setForeground(Color.WHITE);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setHorizontalTextPosition(SwingConstants.CENTER);
    }
}
