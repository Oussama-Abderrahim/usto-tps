package theme;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author programmer
 */
public class SButton extends JButton{

    public Color bg = Theme.BTN_DEFAULT_COLOR;
    public Color hoverBg = null;
    public Color borderColor = null;
    public ArrayList<Component> validationComponents = new ArrayList<Component>();

    public SButton(String text) {
        super(text);

        this.setText(text);
        this.setBgColor(this.bg);
        setFont(Theme.BTN_DEFAULT_FONT);
        this.setBorder(new CompoundBorder(BorderFactory.createLineBorder(borderColor),
                new EmptyBorder(Theme.LABELED_MARGIN, 0, Theme.LABELED_MARGIN, 0)));

        this.setPreferredSize(new Dimension(
                Theme.BTN_DEFAULT_WIDTH, Theme.BTN_DEFAULT_HEIGHT)
        );
        this.setMinimumSize(new Dimension(
                Theme.BTN_DEFAULT_WIDTH, Theme.BTN_DEFAULT_HEIGHT)
        );
        this.setMaximumSize(new Dimension(
                Theme.BTN_DEFAULT_WIDTH*2, Theme.BTN_DEFAULT_HEIGHT)
        );

        this.setForeground(Theme.BTN_DEFAULT_TEXT_COLOR);

        SButton self = this;

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                self.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                self.setBackground(bg);
            }
        });
    }

    public void setBgColor(Color background) {
        this.bg = background;
        this.hoverBg = calcHoverBgColor(bg);
        this.borderColor = calcHoverBgColor(bg);
        setBackground(bg);
        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    public Color calcHoverBgColor(Color color) {
        return new Color(color.getRed() - Theme.HOVER_PLUS,color.getGreen() - Theme.HOVER_PLUS,color.getBlue() - Theme.HOVER_PLUS);
    }

    public Color calcBorderColor(Color color) {
        return new Color(color.getRed() - Theme.BORDER_PLUS,color.getGreen() - Theme.BORDER_PLUS,color.getBlue() - Theme.BORDER_PLUS);
    }

    public void addValidation(Component component) {
        validationComponents.add(component);
    }

    public ArrayList<Component> getValidations() {
        return validationComponents;
    }

    public void clearValidations() {
        validationComponents.clear();
    }

    public static class SLabel extends JLabel
    {
        public SLabel()
        {
            this("");
        }

        public SLabel(String text) {
            this(text, Theme.FONT_DEFAULT_BIG);
        }
        public SLabel(String text, Font font) {
            super(text);
            setFont(font);
            setForeground(Theme.FONT_DEFAULT_COLOR);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);

        }
    }
}
