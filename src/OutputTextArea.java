import theme.Theme;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class OutputTextArea extends JTextPane
{
    private StyledDocument styledDocument;
    private Style style;
    private boolean changed = true;

    public OutputTextArea(boolean editable)
    {
        super();
        setFont(Theme.FONT_DEFAULT);
        setForeground(Theme.FONT_INPUT_COLOR);
        setEditable(editable);
        setText("");
        setBackground(Theme.INPUT_BACKGROUND_COLOR);
        setCaretColor(Theme.FONT_INPUT_COLOR);
        styledDocument = this.getStyledDocument();
        style = this.addStyle("Standard style", null);

        if(editable)
        {
            addKeyListener(new KeyListener()
            {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    changed = true;
                }

                @Override
                public void keyPressed(KeyEvent e)
                {

                }

                @Override
                public void keyReleased(KeyEvent e)
                {

                }
            });
            getDocument().addDocumentListener(new DocumentListener()
            {
                @Override
                public void insertUpdate(DocumentEvent e)
                {
                    EventQueue.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (changed)
                            {
                                int pos = getCaretPosition();
                                highlight();
                                requestFocus();
                                setCaretPosition(pos);
                            }
                        }
                    });
                }

                @Override
                public void removeUpdate(DocumentEvent e)
                {
                    EventQueue.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (changed)
                            {
                                int pos = getCaretPosition();
                                highlight();
                                requestFocus();
                                setCaretPosition(pos);
                            }
                        }
                    });
                }

                @Override
                public void changedUpdate(DocumentEvent e)
                {

                }
            });
        }
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
                "Real_Number",
                "String"
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
            if(line.startsWith("//."))
                appendText(line, Color.GRAY, false);
            else
                for (String word : line.split("\\s"))
                {
                    if (Arrays.asList(KEYWORDS).contains(word))
                        appendText(word+" ", Theme.FONT_KEYWORD_COLOR, true);
                    else if (Arrays.asList(VARTYPES).contains(word))
                        appendText(word+" ", Theme.FONT_TYPE_COLOR, true);
                    else if (Arrays.asList(SYMBOLS).contains(word))
                        appendText(word+" ", Theme.FONT_SYMBOL_COLOR, false);
                    else
                        appendText(word+" ");
                }
            appendText("\n");
        }
        changed = false;
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
