import theme.Theme;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class OutputTextArea extends JTextPane
{
    public OutputTextArea()
    {
        super();
        setFont(Theme.FONT_DEFAULT);
        setEditable(false);
    }

    public void appendtext(String text)
    {
        setText(getText()+text);
    }


}
