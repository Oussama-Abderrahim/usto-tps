package congress;

import congress.organisation.ChangePlanningPanel;
import congress.organisation.InfoModifPanel;
import congress.organisation.SpeakerFormPanel;
import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganisationMainMenu extends SPanel
{
    private SButton conferencierButton;
    private SButton planningButton;
    private SButton informationButton;

    public OrganisationMainMenu()
    {
        setLayout(new BorderLayout(0, 0));

        SButton publicButton = new SButton("Espace public");
        publicButton.setSize(Theme.BTN_DEFAULT_WIDTH*3/2, Theme.BTN_DEFAULT_HEIGHT);
        publicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new PublicMainMenu(), "Menu Principal");
            }
        });

        SPanel topPanel = new SPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        topPanel.add(publicButton);
        add(topPanel, BorderLayout.NORTH);


        SPanel centerPanel = new SPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 50, 0));
        centerPanel.setBorder(new EmptyBorder(5, 50, 5, 50));
        add(centerPanel, BorderLayout.CENTER);

        SLabel informationLabel = new SLabel("");
        informationLabel.setText(PublicMainMenu.INFO_TEXT);

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
                MainFrame.getInstance().switchToPanel(new SpeakerFormPanel(), "Introduire Conferencier");
            }
        });
        planningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new ChangePlanningPanel(), "Modifier Planning");
            }
        });
        informationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new InfoModifPanel(), "Modifier Inforamtions");
            }
        });

        conferencierButton.setIcon(FileManager.loadImage("speaker", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
        conferencierButton.setHoverIcon(FileManager.loadImage("speaker-black", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
        planningButton.setIcon(FileManager.loadImage("PlanningPlus", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
        planningButton.setHoverIcon(FileManager.loadImage("PlanningPlus-black", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
        informationButton.setIcon(FileManager.loadImage("Info", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
        informationButton.setHoverIcon(FileManager.loadImage("Info-black", Theme.BTN_ICON_SIZE, Theme.BTN_ICON_SIZE));
    }
}
