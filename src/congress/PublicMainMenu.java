package congress;

import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;
import congress.visitors.GuidePanel;
import congress.visitors.InscriptionPanel;
import congress.visitors.PlanningPanel;

import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicMainMenu extends SPanel
{
    static  String INFO_TEXT = "<html><center>" +
            "INFORMATION GENERALE\n" +
            "<h1>DATE ET LIEU\n</h1>" +
            "Some long text<br>" +
            "</center></html>";

    private SButton planningButton;
    private SButton guideButton;
    private SButton InscriptionButton;

    public PublicMainMenu()
    {
        try
        {
            ResultSet congres = DatabaseManager.getInstance().fetchCongressData();

            INFO_TEXT = "<html><center>" +
                    congres.getString("Nom") + "\n" +
                    "<h1>" + congres.getString("Date_Debut")+ "\n</h1>" +
                    "<h1>" + congres.getString("Lieu")+ "\n</h1>" +
                    "</center></html>";

        }catch (SQLException ignored) {}
        setLayout(new BorderLayout(0,0));

        SButton organisationButton = new SButton("Espace Organisateur");
        organisationButton.setSize(Theme.BTN_DEFAULT_WIDTH*3/2, Theme.BTN_DEFAULT_HEIGHT);
        organisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /// TODO: Add passwords
                PasswordDialog passwordDialog = new PasswordDialog(MainFrame.getInstance(), "Authentication", true);
                passwordDialog.showDialog();

                if(passwordDialog.isPasswordValid())
                {
                    MainFrame.getInstance().switchToPanel(new OrganisationMainMenu(), "Menu Organisateur");
                }
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
                MainFrame.getInstance().switchToPanel(new PlanningPanel(), "Planning Conferences");
            }
        });
        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new GuidePanel(), "Guide");
            }
        });
        InscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.getInstance().switchToPanel(new InscriptionPanel(), "Inscription");
            }
        });

        planningButton.setIcon(FileManager.loadImage("Planning", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
        guideButton.setIcon(FileManager.loadImage("Guide", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
        InscriptionButton.setIcon(FileManager.loadImage("Inscription", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));

        planningButton.setHoverIcon(FileManager.loadImage("Planning-black", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
        guideButton.setHoverIcon(FileManager.loadImage("Guide-black", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
        InscriptionButton.setHoverIcon(FileManager.loadImage("Inscription-black", Theme.BTN_ICON_SIZE,Theme.BTN_ICON_SIZE));
    }
}
