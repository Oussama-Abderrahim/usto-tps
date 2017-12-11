package congress.organisation;

import congress.DatabaseManager;
import congress.FileManager;
import congress.theme.SButton;
import congress.theme.SPanel;
import congress.theme.Theme;
import congress.visitors.SchedulePlanningPanel;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ChangeSchedulePlanningPanel extends SchedulePlanningPanel
{
    public ChangeSchedulePlanningPanel(int jour)
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
                SPanel conferencePanel = makeConferencePanel(conferences);


                SPanel editButtonPanel = new SPanel();
                editButtonPanel.setLayout(new FlowLayout());
                editButtonPanel.setPreferredSize(new Dimension(50,24));

                SButton editButton = new SButton("");
                editButton.makeIntoIconButton(FileManager.loadImage("edit-icon", 24, 24));
                editButton.setVisible(false);
                editButtonPanel.add(editButton);

                SPanel editConferencePanel = new SPanel();
                editConferencePanel.setLayout(new BorderLayout());
                editConferencePanel.add(conferencePanel, BorderLayout.CENTER);
                editConferencePanel.add(editButtonPanel, BorderLayout.EAST);

                editConferencePanel.addMouseListener(new MouseListener()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {

                    }

                    @Override
                    public void mousePressed(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e)
                    {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e)
                    {
                        editButton.setVisible(true);
                        conferencePanel.setBorder(new CompoundBorder(
                                BorderFactory.createLineBorder(Color.WHITE, 4),
                                new EmptyBorder(5, 10, 5, 10)
                        ));
                    }

                    @Override
                    public void mouseExited(MouseEvent e)
                    {
                        editButton.setVisible(false);
                        conferencePanel.setBorder(new CompoundBorder(
                                BorderFactory.createLineBorder(Color.WHITE, 2),
                                new EmptyBorder(5, 10, 5, 10)
                        ));
                    }
                });

                content.add(editConferencePanel);
            }
        }catch (SQLException e){
            System.err.println("Errer fetching conferences " + e.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setOpaque(false);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
