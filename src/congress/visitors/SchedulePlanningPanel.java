package congress.visitors;

import congress.DatabaseManager;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SchedulePlanningPanel extends SPanel
{
    public SchedulePlanningPanel()
    {
        this(1);
    }

    public SchedulePlanningPanel(int jour)
    {

        SPanel content = new SPanel();

        content.setLayout(new GridLayout(0, 1, 0, 20));
        content.setBorder(new EmptyBorder(20, 50, 20, 50));

        try
        {
            ResultSet conferences = null;
            conferences = DatabaseManager.getInstance().fetchConferences(jour);

            while (conferences != null && conferences.next())
            {
                content.add(makeConferencePanel(conferences));
            }
        }catch (SQLException e){
            System.err.println("Errer fetching conferences " + e.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setOpaque(false);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    public SPanel makeConferencePanel(ResultSet conferences) throws SQLException
    {
        SPanel conferencePanel = new SPanel();

        JLabel horaireText = new JLabel(conferences.getString("Time"));
        JLabel confText = new JLabel(conferences.getString("title"));
        JLabel speakerText = new JLabel(conferences.getString("Name"));

        horaireText.setFont(Theme.FONT_DEFAULT_MEDIUM);
        confText.setFont(Theme.FONT_DEFAULT_MEDIUM);
        speakerText.setFont(Theme.FONT_DEFAULT_MEDIUM);

        confText.setHorizontalAlignment(JLabel.CENTER);

        horaireText.setForeground(Theme.FONT_DEFAULT_COLOR);
        confText.setForeground(Theme.FONT_DEFAULT_COLOR);
        speakerText.setForeground(Theme.FONT_DEFAULT_COLOR);

        conferencePanel.setLayout(new BorderLayout(20, 0));
        conferencePanel.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                new EmptyBorder(5, 10, 5, 10)
        ));

        conferencePanel.add(horaireText, BorderLayout.WEST);
        conferencePanel.add(confText, BorderLayout.CENTER);
        conferencePanel.add(speakerText, BorderLayout.EAST);

        return conferencePanel;
    }

}
