import theme.SButton;
import theme.SLabel;
import theme.SPanel;
import theme.Theme;
import visitors.GuidePanel;
import visitors.InscriptionPanel;
import visitors.PlanningPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganisationMainMenu extends SPanel
{
    private static final String INFO_TEXT = "<html>" +
            "<h1>INFORMATION GENERALE</h1><br>" +
            "<h2>DATE ET LIEU</h2><br>" +
            "Some long text<br>" +
            "</html>";

    private SButton conferencierButton;
    private SButton planningButton;
    private SButton informationButton;

    public OrganisationMainMenu()
    {
        setLayout(new BorderLayout(0, 0));

        SButton organisationButton = new SButton("Espace public");
        organisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new PublicMainMenu());
            }
        });

        SPanel topPanel = new SPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        topPanel.add(organisationButton);
        add(topPanel, BorderLayout.NORTH);


        SPanel centerPanel = new SPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 50, 0));
        centerPanel.setBorder(new EmptyBorder(5, 50, 5, 50));
        add(centerPanel, BorderLayout.CENTER);

        SLabel informationLabel = new SLabel("");
        informationLabel.setText(INFO_TEXT);

        SPanel textPanel = new SPanel();
        textPanel.setLayout(new FlowLayout());
        textPanel.add(informationLabel);
        centerPanel.add(textPanel);

        initButtons();

        SPanel buttonsPanel = new SPanel();
        buttonsPanel.setBorder(new EmptyBorder(50, 20, 50, 20));
        buttonsPanel.setLayout(new GridLayout(1, 3, 100, 0));
        buttonsPanel.add(conferencierButton);
        buttonsPanel.add(planningButton);
        buttonsPanel.add(informationButton);
        centerPanel.add(buttonsPanel);
    }

    private void initButtons() {
        conferencierButton = new SButton("Introduire Conferencier");
        planningButton = new SButton("Modifier Planning");
        informationButton = new SButton("Modifier Informations");

        conferencierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainFrame.getInstance().switchToPanel(new GuidePanel());
            }
        });
        planningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainFrame.getInstance().switchToPanel(new PlanningPanel());
            }
        });
        informationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //MainFrame.getInstance().switchToPanel(new InscriptionPanel());
            }
        });

        conferencierButton.setIcon(FileManager.loadImage("speaker", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
        planningButton.setIcon(FileManager.loadImage("PlanningPlus", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
        informationButton.setIcon(FileManager.loadImage("Info", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
    }
}
