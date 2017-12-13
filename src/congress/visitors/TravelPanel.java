package congress.visitors;

import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import java.awt.*;


public class TravelPanel extends SPanel
{
    private static final java.lang.String ABOUT_TEXT = "<html><center>" +
            "Informations à propos de l'hebergement, reservations, transport et cartes de route (TBA)." +
            "</center>" +
            "<html>";

    public TravelPanel()
    {
        setLayout(new BorderLayout());

        SLabel description = new SLabel(ABOUT_TEXT, Theme.FONT_DEFAULT_MEDIUM);

        add(description, BorderLayout.CENTER);
    }
}
