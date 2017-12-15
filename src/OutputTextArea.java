import theme.Theme;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Arrays;

public class OutputTextArea extends JTextPane
{
    private StyledDocument styledDocument;
    private Style style;

    public OutputTextArea()
    {
        super();
        setFont(Theme.FONT_DEFAULT);
        setEditable(false);
        setText("");
        styledDocument = this.getStyledDocument();
        style = this.addStyle("Standard style", null);
    }

    public void highlight()
    {
        final String[] KEYWORDS = {
                "Start_Program",
                "End_Program",
                "ShowMes",
                "ShowVal",
                "Give",
                "Affect",
                "If",
                "Else",
                ";;",
                "to",
                "Start",
                "Finish"
        };

        final String[] VARTYPES = {
                "Int_Number",
                "Real_Number"
        };

        final String[] SYMBOLS = {
                ":",
                "--",
                ","
        };

        String text = getText();

        clear();

        for(String line : text.split("\n"))
        {
            for (String word : line.split("\\s"))
            {
                if (Arrays.asList(KEYWORDS).contains(word))
                    appendText(word+" ", Color.RED, true);
                else if (Arrays.asList(VARTYPES).contains(word))
                    appendText(word+" ", Color.GREEN, false);
                else if (Arrays.asList(SYMBOLS).contains(word))
                    appendText(word+" ", Color.RED, false);
                else
                    appendText(word+" ");
            }
            appendText("\n");
        }
    }

    private void clear()
    {
        setText("");
        setForeground(Theme.FONT_INPUT_COLOR);
    }

    public void appendText(String text)
    {
        appendText(text, getForeground(), false);
    }

    public void appendText(String text, Color color, boolean bold)
    {
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, bold);
        try
        {
            styledDocument.insertString(
                    styledDocument.getLength(),
                    text,
                    style);
        } catch (BadLocationException e)
        {
            System.err.println("Erreur appending text");
        }
    }

}
