package congress.visitors;

import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import java.awt.*;


public class AboutPanel extends SPanel
{
    private static final java.lang.String ABOUT_TEXT = "<html><center>" +
            "Vous inserez ici la présentation de votre congrès, de votre organisation : qui vous etes" +
            "ce que vous faites, et quels sont vos objectifs pour ce conges.</center><br>" +
            "Vous inserez des détails aussi, differents partenaires : <br>" +
            "<ul>" +
            "<li>Partenaire1</li>" +
            "<li>Partenaire2</li>" +
            "<li>Partenaire3</li>" +
            "</ul>" +
            "<html>";

    public AboutPanel()
    {
        setLayout(new BorderLayout());

        add(new SLabel("NOM CONGRES"), BorderLayout.NORTH);

        SLabel description = new SLabel(ABOUT_TEXT, Theme.FONT_DEFAULT_MEDIUM);

        add(description, BorderLayout.CENTER);
    }
}
