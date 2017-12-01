package visitors;

import theme.SLabel;
import theme.SPanel;
import theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class PlanningPanel extends SPanel
{
    public PlanningPanel()
    {
        setLayout(new BorderLayout(0, 20));

        SPanel titlePanel = new SPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new SLabel("Planning Conferences"));

        add(titlePanel, BorderLayout.NORTH);

        JTabbedPane tabPane = new JTabbedPane();

        tabPane.setBorder(new EmptyBorder(20, 50, 20, 50));
        tabPane.setFont(Theme.FONT_DEFAULT);

        tabPane.add("Jour 1", new SchedulePlanningPanel());
        tabPane.add("Jour 2", new SchedulePlanningPanel());
        tabPane.add("Jour 3", new SchedulePlanningPanel());

        add(tabPane, BorderLayout.CENTER);
    }
}
