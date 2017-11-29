package visitors;

import theme.SButton;
import theme.SLabel;
import theme.SPanel;
import theme.Theme;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
        topPanel.add(new SLabel("Conference"));
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

        JLabel infoLabel = new JLabel("La confirmation et procédures de paiment vous seront fournies par mail");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setForeground(Theme.FONT_DEFAULT_COLOR);
        infoLabel.setFont(Theme.FONT_DEFAULT);
        formPanel.add(infoLabel, BorderLayout.NORTH);

        SPanel fieldsPanel = initFieldsPanel();


        /// TODO : add fields Panel

        formPanel.add(fieldsPanel, BorderLayout.CENTER);

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
        nomLbl.setFont(Theme.FONT_DEFAULT);

        SLabel prenomLbl = new SLabel("Prenom");
        prenomLbl.setFont(Theme.FONT_DEFAULT);

        SLabel mailLbl = new SLabel("Email");
        mailLbl.setFont(Theme.FONT_DEFAULT);

        SLabel occupationLbl = new SLabel("Occupation");
        occupationLbl.setFont(Theme.FONT_DEFAULT);

        nomTxt = new JTextField();
        nomTxt.setFont(Theme.FONT_DEFAULT);
        nomTxt.setForeground(Theme.FONT_INPUT_COLOR);
        prenomTxt = new JTextField();
        prenomTxt.setFont(Theme.FONT_DEFAULT);
        prenomTxt.setForeground(Theme.FONT_INPUT_COLOR);
        mailTxt = new JTextField();
        mailTxt.setFont(Theme.FONT_DEFAULT);
        mailTxt.setForeground(Theme.FONT_INPUT_COLOR);
        occupationTxt = new JComboBox();
        occupationTxt.setFont(Theme.FONT_DEFAULT);
        occupationTxt.setForeground(Theme.FONT_INPUT_COLOR);

        fieldsPanel.add(nomLbl);
        fieldsPanel.add(nomTxt);
        fieldsPanel.add(prenomLbl);
        fieldsPanel.add(prenomTxt);
        fieldsPanel.add(mailLbl);
        fieldsPanel.add(mailTxt);
        fieldsPanel.add(occupationLbl);
        fieldsPanel.add(occupationTxt);

        return fieldsPanel;
    }
}
