package congress.organisation;

import congress.DatabaseManager;
import congress.theme.SButton;
import congress.theme.SLabel;
import congress.theme.SPanel;
import congress.theme.Theme;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SpeakerFormPanel extends SPanel
{
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
		paneShowPic.setLayout(null);

		SLabel lblShowPic = new SLabel("", Theme.FONT_DEFAULT);
		lblShowPic.setBounds(70, 70, 100, 100);
		lblShowPic.setIcon(new ImageIcon("C:\\Users\\vision\\Desktop\\Pdp.png"));
		paneShowPic.add(lblShowPic);

		SPanel paneChoosePic = new SPanel();
		panePic.add(paneChoosePic);

		JFileChooser chooser = new JFileChooser();

		SButton btnChoosePic = new SButton("Charger l'image");
		btnChoosePic.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int returnVal = chooser.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION)
					lblShowPic.setText(chooser.getSelectedFile().getName());
			}
		});
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
		paneNo.setLayout(null);


		SLabel lblNo = new SLabel("N\u00B0 conf\u00E9rencier:", Theme.FONT_DEFAULT);
		lblNo.setHorizontalAlignment(SwingConstants.LEADING);
		lblNo.setBounds(457, 10, 150, 20);
		paneNo.add(lblNo);

		textFieldNo = new JTextField();
		textFieldNo.setBounds(625, 10, 400, 20);
		textFieldNo.setHorizontalAlignment(SwingConstants.CENTER);
		paneNo.add(textFieldNo);
		textFieldNo.setColumns(4);

		SPanel paneNom = new SPanel();
		paneNNP.add(paneNom);
		paneNom.setLayout(null);

		SLabel lblNom = new SLabel("Nom", Theme.FONT_DEFAULT);
		lblNom.setHorizontalAlignment(SwingConstants.LEADING);
		lblNom.setBounds(457, 10, 150, 20);
		paneNom.add(lblNom);

		textFieldNom = new JTextField();
		textFieldNom.setBounds(625, 10, 400, 20);
		textFieldNom.setHorizontalAlignment(SwingConstants.CENTER);
		paneNom.add(textFieldNom);
		textFieldNom.setColumns(10);

		SPanel panePrenom = new SPanel();
		paneNNP.add(panePrenom);
		panePrenom.setLayout(null);

		SLabel lblPrenom = new SLabel("Pr\u00E9nom", Theme.FONT_DEFAULT);
		lblPrenom.setHorizontalAlignment(SwingConstants.LEADING);
		lblPrenom.setBounds(457, 10, 150, 20);
		panePrenom.add(lblPrenom);

		textFieldPrenom = new JTextField();
		textFieldPrenom.setBounds(625, 10, 400, 20);
		textFieldPrenom.setHorizontalAlignment(SwingConstants.CENTER);
		panePrenom.add(textFieldPrenom);
		textFieldPrenom.setColumns(10);

		SPanel paneBio = new SPanel();
		paneBio.setBorder(new EmptyBorder(0, 10, 10, 10));
		paneTxt.add(paneBio);
		paneBio.setLayout(null);

		SLabel lblBio = new SLabel("Bio:", Theme.FONT_DEFAULT);
		lblBio.setHorizontalAlignment(SwingConstants.LEADING);
		lblBio.setBounds(467, 58, 150, 20);
		paneBio.add(lblBio);

		textFieldBio = new JTextField();
		textFieldBio.setBounds(635, 55, 400, 100);
		textFieldBio.setHorizontalAlignment(SwingConstants.CENTER);
		paneBio.add(textFieldBio);
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
				DatabaseManager.getInstance().insertSpeakers(textFieldNo.getText(), textFieldNom.getText(), textFieldPrenom.getText(), textFieldBio.getText());
			}
		});
		paneValider.add(btnValider);
	}
}