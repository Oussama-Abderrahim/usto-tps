package visitors;

import theme.SPanel;
import theme.Theme;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class SchedulePlanningPanel extends SPanel {
    public SchedulePlanningPanel() {
        SPanel content = new SPanel();

        content.setLayout(new GridLayout(0, 1, 0, 20));
        content.setBorder(new EmptyBorder(20, 50, 20, 50));

        for (int i = 0; i < 10; i++)
        {
            SPanel conferencePanel = new SPanel();

            JLabel horaireText = new JLabel("10h00-10h30");
            horaireText.setFont(Theme.FONT_DEFAULT_MEDIUM);
            horaireText.setForeground(Theme.FONT_DEFAULT_COLOR);

            JLabel confText = new JLabel("Intitulé conference");
            confText.setHorizontalAlignment(JLabel.CENTER);
            confText.setFont(Theme.FONT_DEFAULT_MEDIUM);
            confText.setForeground(Theme.FONT_DEFAULT_COLOR);

            JLabel speakerText = new JLabel("Nom conferencier");
            speakerText.setFont(Theme.FONT_DEFAULT_MEDIUM);
            speakerText.setForeground(Theme.FONT_DEFAULT_COLOR);

            conferencePanel.setLayout(new BorderLayout(20, 0));
            conferencePanel.setBorder(new CompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    new EmptyBorder(5, 10, 5, 10)
            ));

            conferencePanel.add(horaireText, BorderLayout.WEST);
            conferencePanel.add(confText, BorderLayout.CENTER);
            conferencePanel.add(speakerText, BorderLayout.EAST);

            content.add(conferencePanel);
        }

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setOpaque(false);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
