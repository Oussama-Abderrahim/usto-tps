package congress.visitors;

import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class GuidePanel extends SPanel
{
    public GuidePanel()
    {
        setLayout(new BorderLayout(0, 20));

        SPanel titlePanel = new SPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new SLabel("Guide"));

        add(titlePanel, BorderLayout.NORTH);

        JTabbedPane tabPane = new JTabbedPane();

        tabPane.setBorder(new EmptyBorder(20, 50, 20, 50));
        tabPane.setFont(Theme.FONT_DEFAULT);

        tabPane.add("A propos", new AboutPanel());
        tabPane.add("Conférenciers", new ConferenciersPanel());
        tabPane.add("Hebergement et voyage", new AboutPanel());

        add(tabPane, BorderLayout.CENTER);
    }
}
