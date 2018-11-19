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

    public STextField()
    {
        setOpaque(false);
        setForeground(Theme.FONT_DEFAULT_COLOR);
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
                    setForeground(Theme.FONT_INPUT_COLOR);
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
}
