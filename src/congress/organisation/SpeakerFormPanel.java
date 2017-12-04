package congress.organisation;

import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;


public class SpeakerFormPanel extends SPanel
{
	/*
	int returnVal = chooser.showOpenDialog(getParent());
	if(returnVal == JFileChooser.APPROVE_OPTION)
		image.setText(chooser.getSelectedFile().getName());
	 */
	private JTextField textFieldBio;
	private JTextField textFieldNo;
	private JTextField textFieldNom;
	private JTextField textFieldPrenom;

	/**
	 * Create the panel.
	 */
	public SpeakerFormPanel() {
		setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 800, 400);
		
		SPanel paneTitre = new SPanel();
		add(paneTitre, BorderLayout.NORTH);
		paneTitre.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		SLabel lblTitre = new SLabel("Formulaire conf\u00E9renciers", Theme.FONT_DEFAULT);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		paneTitre.add(lblTitre);
		
		SPanel paneConferenciers = new SPanel();
		paneConferenciers.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(paneConferenciers, BorderLayout.CENTER);
		paneConferenciers.setLayout(new BorderLayout(10, 0));
		
		SPanel panePic = new SPanel();
		paneConferenciers.add(panePic, BorderLayout.WEST);
		panePic.setLayout(new GridLayout(0, 1, 0, 0));
		
		SPanel paneShowPic = new SPanel();
		panePic.add(paneShowPic);
		
		SLabel lblShowPic = new SLabel("", Theme.FONT_DEFAULT);
		lblShowPic.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowPic.setIcon(new ImageIcon("C:\\Users\\vision\\Desktop\\Pdp.png"));
		paneShowPic.add(lblShowPic);
		
		SPanel paneChoosePic = new SPanel();
		panePic.add(paneChoosePic);
		
		SButton btnChoosePic = new SButton("Charger l'image");
		paneChoosePic.add(btnChoosePic);
		
		SPanel paneTxt = new SPanel();
		paneTxt.setBorder(new EmptyBorder(10, 10, 10, 10));
		paneConferenciers.add(paneTxt, BorderLayout.CENTER);
		paneTxt.setLayout(new GridLayout(0, 1, 0, 0));
		
		SPanel paneNNP = new SPanel();
		paneNNP.setBorder(new EmptyBorder(10, 10, 0, 10));
		paneTxt.add(paneNNP);
		paneNNP.setLayout(new GridLayout(0, 1, 0, 0));
		
		SPanel paneNo = new SPanel();
		paneNNP.add(paneNo);
		paneNo.setLayout(new GridLayout(0, 2, 0, 0));
		
		SLabel lblNo = new SLabel("N\u00B0 conf\u00E9rencier:", Theme.FONT_DEFAULT);
		paneNo.add(lblNo);
		
		textFieldNo = new JTextField();
		textFieldNo.setHorizontalAlignment(SwingConstants.CENTER);
		paneNo.add(textFieldNo);
		textFieldNo.setColumns(10);
		
		SPanel paneNom = new SPanel();
		paneNNP.add(paneNom);
		paneNom.setLayout(new GridLayout(0, 2, 0, 0));
		
		SLabel lblNom = new SLabel("Nom", Theme.FONT_DEFAULT);
		paneNom.add(lblNom);
		
		textFieldNom = new JTextField();
		textFieldNom.setHorizontalAlignment(SwingConstants.CENTER);
		paneNom.add(textFieldNom);
		textFieldNom.setColumns(10);
		
		SPanel panePrenom = new SPanel();
		paneNNP.add(panePrenom);
		panePrenom.setLayout(new GridLayout(0, 2, 0, 0));
		
		SLabel lblPrenom = new SLabel("Pr\u00E9nom", Theme.FONT_DEFAULT);
		panePrenom.add(lblPrenom);
		
		textFieldPrenom = new JTextField();
		textFieldPrenom.setHorizontalAlignment(SwingConstants.CENTER);
		panePrenom.add(textFieldPrenom);
		textFieldPrenom.setColumns(10);
		
		SPanel paneBio = new SPanel();
		paneBio.setBorder(new EmptyBorder(0, 10, 10, 10));
		paneTxt.add(paneBio);
		paneBio.setLayout(new GridLayout(0, 2, 0, 0));
		
		SLabel lblBio = new SLabel("Bio:", Theme.FONT_DEFAULT);
		paneBio.add(lblBio);
		
		textFieldBio = new JTextField();
		textFieldBio.setHorizontalAlignment(SwingConstants.CENTER);
		paneBio.add(textFieldBio);
		textFieldBio.setColumns(10);
		
		SPanel paneValider = new SPanel();
		add(paneValider, BorderLayout.SOUTH);
		paneValider.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneValider.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		SButton btnValider = new SButton("Valider");
		paneValider.add(btnValider);

	}

}
