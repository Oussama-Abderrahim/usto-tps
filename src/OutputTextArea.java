import theme.Theme;

import javax.swing.*;

public class OutputTextArea extends JTextArea
{
    public OutputTextArea()
    {
        super();
        setFont(Theme.FONT_DEFAULT);
        setEditable(false);
    }

    public void appendtext(String text)
    {
        setText(this.getText() + text);
    }
}
