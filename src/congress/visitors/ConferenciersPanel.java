package congress.visitors;

import congress.DatabaseManager;
import congress.FileManager;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

class ConferenciersPanel extends SPanel
{

    public ConferenciersPanel()
    {
        setLayout(new BorderLayout());

        SPanel content = new SPanel();

        content.setLayout(new GridLayout(0, 1, 0, 5));

        ResultSet speakers = DatabaseManager.getInstance().fetchSpeakers();

        try
        {
            while (speakers != null && speakers.next())
            {
                SPanel speakerPanel = makeSpeaker(speakers);

                content.add(speakerPanel);
            }
        } catch (SQLException ignored){}

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    private SPanel makeSpeaker(ResultSet speaker) throws SQLException
    {
        SPanel speakerPanel = new SPanel();

        speakerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 25, 5, 25),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Theme.FONT_DEFAULT_COLOR),
                        BorderFactory.createEmptyBorder(5, 25, 5, 25)
                )
        ));

        speakerPanel.setLayout(new BorderLayout(25, 0));

        JLabel speakerPic = new JLabel(FileManager.loadImage("speaker", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));

        SPanel descPanel = new SPanel();

        descPanel.setLayout(new BorderLayout());

        SLabel nameLabel = new SLabel(speaker.getString("Name"), Theme.FONT_DEFAULT_MEDIUM);
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        SLabel bioLabel = new SLabel(speaker.getString("Description"), Theme.FONT_DEFAULT_MEDIUM);
        bioLabel.setHorizontalAlignment(JLabel.LEFT);

        descPanel.add(nameLabel, BorderLayout.NORTH);
        descPanel.add(bioLabel, BorderLayout.CENTER);

        speakerPanel.add(speakerPic, BorderLayout.WEST);
        speakerPanel.add(descPanel, BorderLayout.CENTER);

        return speakerPanel;
    }
}
