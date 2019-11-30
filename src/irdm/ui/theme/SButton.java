package irdm.ui.theme;


import mdlaf.animation.MaterialUIMovement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author programmer
 */
public class SButton extends JButton
{

    public Color bg = Theme.BTN_DEFAULT_COLOR;
    public Color hoverBg = null;
    private Color hoverFg = Theme.BTN_DEFAULT_TEXT_HOVER_COLOR;
    public Color borderColor = null;
    public ArrayList<Component> validationComponents = new ArrayList<Component>();

    public SButton(String text)
    {
        super(text);

        this.setText(text);
//        this.setFocusPainted(false);

        this.setMinimumSize(new Dimension(
                Theme.BTN_DEFAULT_WIDTH, Theme.BTN_DEFAULT_HEIGHT)
        );
        this.setMaximumSize(new Dimension(
                Theme.BTN_DEFAULT_WIDTH * 2, Theme.BTN_DEFAULT_HEIGHT)
        );

        setFont(Theme.BTN_DEFAULT_FONT);
        this.setForeground(Theme.BTN_DEFAULT_TEXT_COLOR);
        this.setBgColor(this.bg);
        MaterialUIMovement.add (this, this.hoverBg);

    }

    public void setBgColor(Color background)
    {
        this.bg = background;
        this.hoverBg = calcHoverBgColor(bg);
        this.borderColor = calcHoverBgColor(bg);
        setBackground(bg);
//        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    public Color calcHoverBgColor(Color color)
    {
        return new Color(
                Math.max(0, color.getRed() - Theme.HOVER_PLUS),
                Math.max(0, color.getGreen() - Theme.HOVER_PLUS),
                Math.max(0, color.getBlue() - Theme.HOVER_PLUS));
    }

    public Color calcBorderColor(Color color)
    {
        return new Color(color.getRed() - Theme.BORDER_PLUS, color.getGreen() - Theme.BORDER_PLUS, color.getBlue() - Theme.BORDER_PLUS);
    }

    public void addValidation(Component component)
    {
        validationComponents.add(component);
    }

    public ArrayList<Component> getValidations()
    {
        return validationComponents;
    }

    public void clearValidations()
    {
        validationComponents.clear();
    }

}
