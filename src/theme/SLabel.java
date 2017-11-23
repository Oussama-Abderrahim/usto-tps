package theme;

import javax.swing.*;

public class SLabel extends JLabel
{
    public SLabel()
    {
        this("");
    }

    public SLabel(String text) {
        super(text);
        setFont(Theme.FONT_DEFAULT_LARGE);
        setForeground(Theme.FONT_DEFAULT_COLOR);

    }
}
