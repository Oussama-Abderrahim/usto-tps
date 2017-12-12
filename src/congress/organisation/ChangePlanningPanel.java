package congress.organisation;

import congress.DatabaseManager;
import congress.theme.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ChangePlanningPanel extends SPanel
{
    private static final int NBR_JOURS = 4;

    public ChangePlanningPanel()
    {
        setLayout(new BorderLayout(0, 20));

        SPanel titlePanel = new SPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new SLabel(""));

        add(titlePanel, BorderLayout.NORTH);

        JTabbedPane tabPane = new JTabbedPane();

        tabPane.setBorder(new EmptyBorder(20, 50, 20, 50));
        tabPane.setFont(Theme.FONT_DEFAULT);

        for (int i = 1; i <= NBR_JOURS; i++)
        {
            tabPane.add("Jour " + i, new ChangeSchedulePlanningPanel(i));
        }

        add(tabPane, BorderLayout.CENTER);

        SPanel formPanel = new SPanel();

        formPanel.setLayout(new FlowLayout());

        SLabel addConfLabel = new SLabel("Ajouter conf : ", Theme.FONT_DEFAULT);
        STextField timeField = new STextField();
        timeField.setOpaque(true);
        timeField.setFont(Theme.FONT_DEFAULT);
        timeField.setForeground(Theme.FONT_INPUT_COLOR);
        timeField.setPlaceHolder("Heure");
        STextField titleField = new STextField();
        titleField.setOpaque(true);
        titleField.setFont(Theme.FONT_DEFAULT);
        titleField.setForeground(Theme.FONT_INPUT_COLOR);
        titleField.setPlaceHolder("Intitulé");
        JComboBox<String> speakerField = new JComboBox<String>();

        timeField.setColumns(10);
        timeField.setFont(Theme.FONT_DEFAULT);
        titleField.setColumns(30);
        titleField.setFont(Theme.FONT_DEFAULT);
        speakerField.setPreferredSize(new Dimension(200, 28));

        formPanel.add(addConfLabel);
        formPanel.add(timeField);
        formPanel.add(titleField);
        formPanel.add(speakerField);

        ResultSet speakers = DatabaseManager.getInstance().fetchSpeakers();

        try
        {
            while (speakers != null && speakers.next())
            {
                speakerField.addItem(speakers.getString("Name"));
            }
        } catch (SQLException ignored)
        {
        }

        SButton addButton = new SButton("+");
        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DatabaseManager.getInstance().insertConference(
                        titleField.getText(),
                        "" + tabPane.getSelectedIndex() + 1,
                        timeField.getText()
                );
            }
        });
        formPanel.add(addButton);
        add(formPanel, BorderLayout.SOUTH);
    }
}
