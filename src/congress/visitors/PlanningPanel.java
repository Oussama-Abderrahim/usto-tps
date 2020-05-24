package congress.visitors;

import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class PlanningPanel extends SPanel
{
    private static final int NBR_JOURS = 4;

    public PlanningPanel()
    {
        setLayout(new BorderLayout(0, 20));

        SPanel titlePanel = new SPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new SLabel(""), Theme.FONT_DEFAULT_MEDIUM);

        add(titlePanel, BorderLayout.NORTH);

        JTabbedPane tabPane = new JTabbedPane();

        tabPane.setBorder(new EmptyBorder(20, 50, 20, 50));
        tabPane.setFont(Theme.FONT_DEFAULT);

        for(int i = 1; i <= NBR_JOURS; i++)
        {
            tabPane.add("Jour "+i, new SchedulePlanningPanel(i));
        }

        add(tabPane, BorderLayout.CENTER);
    }
}
