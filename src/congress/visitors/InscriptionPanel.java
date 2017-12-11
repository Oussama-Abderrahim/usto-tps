package congress.visitors;

import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class InscriptionPanel extends SPanel
{
    private JTextField nomTxt;
    private JTextField prenomTxt;
    private JTextField mailTxt;
    private JComboBox occupationTxt;

    public InscriptionPanel()
    {
        setLayout(new BorderLayout());

        SPanel topPanel = new SPanel();
        topPanel.add(new SLabel("", Theme.FONT_DEFAULT_MEDIUM));
        add(topPanel, BorderLayout.NORTH);

        SPanel centerPanel = initCenterPanel();

        add(centerPanel, BorderLayout.CENTER);
    }

    private SPanel initCenterPanel()
    {
        SPanel panel = new SPanel();

        panel.setLayout(new BorderLayout());

        JLabel tarifLabel = new JLabel("<html>" +
                "Tarif ( Avant 01 Octobre ) :\t0.00Eu\n<br>" +
                "Tarif ( Entre 01 Octobre et 30 Novembre) :\t0.00Eu\n<br>" +
                "Tarif derniere minute :\t0.00Eu" +
                "</html>");
        tarifLabel.setFont(Theme.FONT_DEFAULT_MEDIUM);
        tarifLabel.setForeground(Theme.FONT_DEFAULT_COLOR);

        panel.add(tarifLabel, BorderLayout.NORTH);

        SPanel centerPanel = initFormPanel();

        panel.add(centerPanel, BorderLayout.CENTER);

        SPanel southPanel = new SPanel();
        southPanel.setLayout(new FlowLayout());
        SButton validateButton = new SButton("Valider");

        southPanel.add(validateButton);
        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    private SPanel initFormPanel()
    {
        SPanel formPanel = new SPanel();

        formPanel.setBorder(new CompoundBorder(
                new EmptyBorder(20, 150, 20, 150),
                BorderFactory.createLineBorder(Theme.FONT_DEFAULT_COLOR, 2, true)
        ));
        formPanel.setLayout(new BorderLayout());

        JLabel infoLabel = new JLabel("La confirmation et procédures de paiement vous seront fournies par mail");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setForeground(Theme.FONT_DEFAULT_COLOR);
        infoLabel.setFont(Theme.FONT_DEFAULT);
        formPanel.add(infoLabel, BorderLayout.NORTH);

        SPanel fieldsPanel = initFieldsPanel();
        JScrollPane scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setOpaque(false);
        formPanel.add(scrollPane, BorderLayout.CENTER);

        JCheckBox checkBox = new JCheckBox("J'ai lu le reglement interne du congrés");
        checkBox.setHorizontalAlignment(JCheckBox.CENTER);
        checkBox.setOpaque(false);
        checkBox.setFont(Theme.FONT_DEFAULT);
        checkBox.setForeground(Theme.FONT_DEFAULT_COLOR);

        formPanel.add(checkBox, BorderLayout.SOUTH);

        return formPanel;
    }

    private SPanel initFieldsPanel()
    {
        SPanel fieldsPanel = new SPanel();
        fieldsPanel.setLayout(new GridLayout(4, 2, -10, 30));

        fieldsPanel.setBorder(new CompoundBorder(
                new EmptyBorder(20, 50, 20, 50),
                new CompoundBorder(
                        BorderFactory.createLineBorder(Theme.FONT_DEFAULT_COLOR, 2, true),
                        new EmptyBorder(10, 20, 10, 20)
                )
        ));


        SLabel nomLbl = new SLabel("Nom");
        SLabel mailLbl = new SLabel("Email");
        SLabel prenomLbl = new SLabel("Prenom");
        SLabel occupationLbl = new SLabel("Occupation");

        nomLbl.setPreferredSize(new Dimension(150, Theme.BTN_DEFAULT_HEIGHT));
        mailLbl.setPreferredSize(new Dimension(150, Theme.BTN_DEFAULT_HEIGHT));
        prenomLbl.setPreferredSize(new Dimension(150, Theme.BTN_DEFAULT_HEIGHT));
        occupationLbl.setPreferredSize(new Dimension(150, Theme.BTN_DEFAULT_HEIGHT));
        nomLbl.setFont(Theme.FONT_DEFAULT);
        prenomLbl.setFont(Theme.FONT_DEFAULT);
        mailLbl.setFont(Theme.FONT_DEFAULT);
        occupationLbl.setFont(Theme.FONT_DEFAULT);


        nomTxt = new JTextField();
        prenomTxt = new JTextField();
        mailTxt = new JTextField();
        occupationTxt = new JComboBox();

        nomTxt.setFont(Theme.FONT_DEFAULT);
        prenomTxt.setFont(Theme.FONT_DEFAULT);
        mailTxt.setFont(Theme.FONT_DEFAULT);
        occupationTxt.setFont(Theme.FONT_DEFAULT);

        nomTxt.setForeground(Theme.FONT_INPUT_COLOR);
        prenomTxt.setForeground(Theme.FONT_INPUT_COLOR);
        mailTxt.setForeground(Theme.FONT_INPUT_COLOR);
        occupationTxt.setForeground(Theme.FONT_INPUT_COLOR);

        SPanel nomPanel = new SPanel();
        SPanel mailPanel = new SPanel();
        SPanel prenomPanel = new SPanel();
        SPanel occupationPanel = new SPanel();

        nomPanel.setLayout(new BorderLayout());
        mailPanel.setLayout(new BorderLayout());
        prenomPanel.setLayout(new BorderLayout());
        occupationPanel.setLayout(new BorderLayout());


        nomPanel.add(nomLbl, BorderLayout.WEST);
        mailPanel.add(mailLbl, BorderLayout.WEST);
        prenomPanel.add(prenomLbl, BorderLayout.WEST);
        occupationPanel.add(occupationLbl, BorderLayout.WEST);

        nomPanel.add(nomTxt, BorderLayout.CENTER);
        mailPanel.add(mailTxt, BorderLayout.CENTER);
        prenomPanel.add(prenomTxt, BorderLayout.CENTER);
        occupationPanel.add(occupationTxt, BorderLayout.CENTER);


        fieldsPanel.add(nomPanel);
        fieldsPanel.add(prenomPanel);
        fieldsPanel.add(mailPanel);
        fieldsPanel.add(occupationPanel);

        return fieldsPanel;
    }
}
