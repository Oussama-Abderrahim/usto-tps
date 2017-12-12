package congress.organisation;

import congress.DatabaseManager;
import congress.FileManager;
import congress.theme.*;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class SpeakerFormPanel extends SPanel
{
    private JTextField textFieldBio;
    private JTextField textFieldNo;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;

    /**
     * Create the panel.
     */
    public SpeakerFormPanel()
    {
        setLayout(new BorderLayout(0, 0));

        SPanel paneConferenciers = new SPanel();
        paneConferenciers.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(paneConferenciers, BorderLayout.CENTER);
        paneConferenciers.setLayout(new GridLayout(0, 2, 0, 0));

        SPanel panePic = new SPanel();
        paneConferenciers.add(panePic);
        panePic.setLayout(new BorderLayout());

        SPanel paneShowPic = new SPanel();
        panePic.add(paneShowPic, BorderLayout.CENTER);
        paneShowPic.setLayout(new BorderLayout());

        SLabel lblShowPic = new SLabel("", Theme.FONT_DEFAULT);
        lblShowPic.setIcon(FileManager.loadImage("speaker", 256, 256));
        paneShowPic.add(lblShowPic);

        SPanel paneChoosePic = new SPanel();
        panePic.add(paneChoosePic, BorderLayout.SOUTH);

        SButton btnChoosePic = new SButton("Charger l'image");
        btnChoosePic.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                ImageIcon img = FileManager.loadImageFromChooser(lblShowPic.getWidth(), lblShowPic.getHeight());
                if(img != null)
                    lblShowPic.setIcon(img);
            }
        });
        paneChoosePic.add(btnChoosePic);

        SPanel paneTxt = new SPanel();
        paneTxt.setBorder(new EmptyBorder(10, 0, 10, 0));
        paneConferenciers.add(paneTxt);
        paneTxt.setLayout(new GridLayout(0, 1, 0, 20));

        SPanel paneNNP = new SPanel();
        paneNNP.setBorder(new EmptyBorder(10, 0, 0, 0));
        paneTxt.add(paneNNP);
        paneNNP.setLayout(new GridLayout(0, 1, 0, 20));

        SPanel paneNo = new SPanel();
        paneNNP.add(paneNo);
        paneNo.setLayout(new BorderLayout());


        SLabel lblNo = new SLabel("N\u00B0 conf\u00E9rencier:", Theme.FONT_DEFAULT);
        lblNo.setHorizontalAlignment(SwingConstants.LEADING);
        lblNo.setPreferredSize(new Dimension(150, 20));
        paneNo.add(lblNo, BorderLayout.WEST);

        textFieldNo = new JTextField();
        textFieldNo.setPreferredSize(new Dimension(400, 20));
        textFieldNo.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldNo.setEnabled(false);
        textFieldNo.setOpaque(false);
        paneNo.add(textFieldNo, BorderLayout.CENTER);
        textFieldNo.setColumns(4);

        SPanel paneNom = new SPanel();
        paneNNP.add(paneNom);
        paneNom.setLayout(new BorderLayout());

        SLabel lblNom = new SLabel("Nom", Theme.FONT_DEFAULT);
        lblNom.setHorizontalAlignment(SwingConstants.LEADING);
        lblNom.setPreferredSize(new Dimension(150, 20));
        paneNom.add(lblNom, BorderLayout.WEST);

        textFieldNom = new JTextField();
        textFieldNom.setPreferredSize(new Dimension(400, 20));
        textFieldNom.setHorizontalAlignment(SwingConstants.CENTER);
        paneNom.add(textFieldNom, BorderLayout.CENTER);
        textFieldNom.setColumns(10);

        SPanel panePrenom = new SPanel();
        paneNNP.add(panePrenom);
        panePrenom.setLayout(new BorderLayout());

        SLabel lblPrenom = new SLabel("Pr\u00E9nom", Theme.FONT_DEFAULT);
        lblPrenom.setHorizontalAlignment(SwingConstants.LEADING);
        lblPrenom.setPreferredSize(new Dimension(150, 20));
        panePrenom.add(lblPrenom, BorderLayout.WEST);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setPreferredSize(new Dimension(400, 20));
        textFieldPrenom.setHorizontalAlignment(SwingConstants.CENTER);
        panePrenom.add(textFieldPrenom, BorderLayout.CENTER);
        textFieldPrenom.setColumns(10);

        SPanel paneBio = new SPanel();
        paneBio.setBorder(new EmptyBorder(0, 0, 10, 0));
        paneTxt.add(paneBio);
        paneBio.setLayout(new BorderLayout());

        SLabel lblBio = new SLabel("Bio:", Theme.FONT_DEFAULT);
        lblBio.setHorizontalAlignment(SwingConstants.LEADING);
        lblBio.setPreferredSize(new Dimension(150, 20));
        paneBio.add(lblBio, BorderLayout.WEST);

        textFieldBio = new JTextField();
        textFieldBio.setPreferredSize(new Dimension(400, 100));
        textFieldBio.setHorizontalAlignment(SwingConstants.CENTER);
        paneBio.add(textFieldBio, BorderLayout.CENTER);
        textFieldBio.setColumns(10);

        SPanel paneValider = new SPanel();
        add(paneValider, BorderLayout.SOUTH);
        paneValider.setBorder(new EmptyBorder(10, 0, 10, 0));
        paneValider.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        SButton btnValider = new SButton("Valider");
        btnValider.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                DatabaseManager.getInstance().insertSpeakers(textFieldNom.getText(), textFieldPrenom.getText(), textFieldBio.getText());
            }
        });
        paneValider.add(btnValider);
    }
}