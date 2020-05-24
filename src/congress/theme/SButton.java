package congress.theme;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author programmer
 */
public class SButton extends JButton{

    private Color bg = Theme.BTN_DEFAULT_COLOR;
    private Color hoverBg = null;
    private Color borderColor = null;

    private Icon icon = null;
    private ImageIcon hoverIcon = null;

    public SButton(String text) {
        super(text);

        this.setText(text);
        setFocusPainted(false);
        setFont(Theme.BTN_DEFAULT_FONT);


        this.setBgColor(this.bg);

        this.setBorder(new CompoundBorder(BorderFactory.createLineBorder(borderColor),
                new EmptyBorder(Theme.LABELED_MARGIN, 0, Theme.LABELED_MARGIN, 0)));

        setSize(Theme.BTN_DEFAULT_WIDTH, Theme.BTN_DEFAULT_HEIGHT);

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
                if(hoverIcon != null)
                {
                    icon = getIcon();
                    self.setIcon(hoverIcon);
                }
                self.setBackground(hoverBg);
                self.setForeground(Theme.BTN_DEFAULT_TEXT_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(icon != null)
                    self.setIcon(icon);
                self.setBackground(bg);
                self.setForeground(Theme.BTN_DEFAULT_TEXT_COLOR);
            }
        });
    }

    public void makeIntoIconButton(ImageIcon icon)
    {
        if(icon == null)
            return;
        setIcon(icon);
        setOpaque(false);
        setBorder(null);
        addMouseListener(null);
        setSize(icon.getIconWidth(), icon.getIconHeight());
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.setPreferredSize(new Dimension(
                width, height
        ));
        this.setMinimumSize(new Dimension(
                width, height
        ));
        this.setMaximumSize(new Dimension(
                width*2, height
        ));
    }

    public void setIcon(ImageIcon img)
    {
        super.setIcon(img);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
    }

    public void setHoverIcon(ImageIcon img)
    {
        this.hoverIcon = img;
    }

    public void setBgColor(Color background) {
        this.bg = background;
        this.hoverBg = calcHoverBgColor(bg);
        this.borderColor = calcBorderColor(bg);
        setBackground(bg);
        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    private Color calcHoverBgColor(Color color) {
        return new Color(
                Math.max(color.getRed() - Theme.HOVER_PLUS,0),
                Math.max(color.getGreen() - Theme.HOVER_PLUS,0),
                Math.max(color.getBlue() - Theme.HOVER_PLUS,0)
        );
    }

    private Color calcBorderColor(Color color) {
        return new Color(
                Math.max(color.getRed() - Theme.BORDER_PLUS,0),
                Math.max(color.getGreen() - Theme.BORDER_PLUS,0),
                Math.max(color.getBlue() - Theme.BORDER_PLUS,0)
        );
    }

}
