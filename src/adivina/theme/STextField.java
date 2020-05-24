package adivina.theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by ${} on 04/12/2017.
 */
public class STextField extends JTextField
{
    private String placeHolder = "";
    private boolean edited = false;

    private Color fontColor = Theme.FONT_INPUT_COLOR;

    public STextField()
    {
        setOpaque(false);
        setForeground(fontColor);
        setFont(Theme.FONT_DEFAULT_SMALL);
    }



    public void setPlaceHolder(String placeHolder)
    {
        this.placeHolder = placeHolder;
        setForeground(Color.GRAY);
        setText(placeHolder);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!edited && getText().equals(placeHolder))
                {
                    edited = true;
                    setText("");
                    setForeground(fontColor);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty())
                {
                    edited = false;
                    setForeground(Color.GRAY);
                    setText(placeHolder);
                }
            }
        });
    }

    public void setFontColor(Color fontColor)
    {
        this.fontColor = fontColor;
        setForeground(fontColor);
    }
}
