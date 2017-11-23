import theme.SButton;
import theme.SLabel;
import theme.SPanel;
import theme.Theme;
import visitors.GuidePanel;
import visitors.InscriptionPanel;
import visitors.PlanningPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PublicMainMenu extends SPanel
{
    static final String INFO_TEXT = "<html><center>" +
            "<h1>INFORMATION GENERALE</h1>\n" +
            "<h2>DATE ET LIEU</h2>\n" +
            "Some long text<br>" +
            "</center></html>";

    private SButton planningButton;
    private SButton guideButton;
    private SButton InscriptionButton;

    public PublicMainMenu()
    {
        setLayout(new BorderLayout(0,0));

        SButton organisationButton = new SButton("Espace Organisateur");
        organisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new OrganisationMainMenu());
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

        SLabel informationLabel = new SLabel();
        informationLabel.setText(INFO_TEXT);

        SPanel textPanel = new SPanel();
        textPanel.setLayout(new FlowLayout());
        textPanel.add(informationLabel);
        centerPanel.add(textPanel);

        initButtons();

        SPanel buttonsPanel = new SPanel();
        buttonsPanel.setBorder(new EmptyBorder(50, 20, 50, 20));
        buttonsPanel.setLayout(new GridLayout(1, 3, 100, 0));
        buttonsPanel.add(planningButton);
        buttonsPanel.add(guideButton);
        buttonsPanel.add(InscriptionButton);
        centerPanel.add(buttonsPanel);
    }

    private void initButtons()
    {
        planningButton = new SButton("Planning");
        guideButton = new SButton("Guide");
        InscriptionButton = new SButton("Inscription");

        planningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new PlanningPanel());
            }
        });
        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new GuidePanel());
            }
        });
        InscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new InscriptionPanel());
            }
        });

        planningButton.setIcon(FileManager.loadImage("Planning", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
        guideButton.setIcon(FileManager.loadImage("Guide", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
        InscriptionButton.setIcon(FileManager.loadImage("Inscription", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
    }
}
