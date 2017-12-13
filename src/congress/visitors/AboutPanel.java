package congress.visitors;

import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import java.awt.*;


public class AboutPanel extends SPanel
{
    private static final java.lang.String ABOUT_TEXT = "<html><center>" +
            "Vous inserez ici la présentation de votre congrès, de votre organisation : qui vous etes, " +
            "ce que vous faites,<br>et quels sont vos objectifs pour ce congres.</center><br>" +
            "Vous inserez des détails aussi, differents partenaires : <br>" +
            "<center>" +
            "<h1>Co-président general</h1>" +
            "Nom Prenom1, université1, Ville<br>" +
            "Nom Prenom2, université2, Ville" +
            "</center>" +
            "<ul>" +
            "<li>Partenaire1</li>" +
            "<li>Partenaire2</li>" +
            "<li>Partenaire3</li>" +
            "</ul>" +
            "<h1>Comité du programme</h1>" +
            "<ul>" +
            "<li>Personne1, Université USTO, Algerie</li>" +
            "<li>Personne2, Université Mostaganem, Algerie</li>" +
            "<li>Personne3, University Of Montreal, Canada</li>" +
            "</ul>" +
            "<html>";

    public AboutPanel()
    {
        SPanel content = new SPanel();

        setLayout(new BorderLayout());
        content.setLayout(new BorderLayout());

        content.add(new SLabel("NOM CONGRES"), BorderLayout.NORTH);

        SLabel description = new SLabel(ABOUT_TEXT, Theme.FONT_DEFAULT_MEDIUM);

        content.add(description, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Theme.BACKGROUND_COLOR);
        add(scrollPane, BorderLayout.CENTER);
    }
}
