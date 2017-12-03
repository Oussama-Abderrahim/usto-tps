package congress.organisation;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class FormulaireConferenciers extends JPanel {
	private JTextField textFieldBio;
	private JTextField textFieldNo;
	private JTextField textFieldNom;
	private JTextField textFieldPrenom;

	/**
	 * Create the panel.
	 */
	public FormulaireConferenciers() {
		setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 800, 400);
		
		JPanel paneTitre = new JPanel();
		add(paneTitre, BorderLayout.NORTH);
		paneTitre.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneTitre.setBackground(Color.WHITE);
		paneTitre.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JLabel lblTitre = new JLabel("Formulaire conf\u00E9renciers");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneTitre.add(lblTitre);
		
		JPanel paneConferenciers = new JPanel();
		paneConferenciers.setBorder(new EmptyBorder(10, 10, 10, 10));
		paneConferenciers.setBackground(Color.WHITE);
		add(paneConferenciers, BorderLayout.CENTER);
		paneConferenciers.setLayout(new BorderLayout(10, 0));
		
		JPanel panePic = new JPanel();
		panePic.setBackground(Color.WHITE);
		paneConferenciers.add(panePic, BorderLayout.WEST);
		panePic.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel paneShowPic = new JPanel();
		paneShowPic.setBackground(Color.WHITE);
		panePic.add(paneShowPic);
		
		JLabel lblShowPic = new JLabel("");
		lblShowPic.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowPic.setIcon(new ImageIcon("C:\\Users\\vision\\Desktop\\Pdp.png"));
		paneShowPic.add(lblShowPic);
		
		JPanel paneChoosePic = new JPanel();
		paneChoosePic.setBackground(Color.WHITE);
		panePic.add(paneChoosePic);
		
		JButton btnChoosePic = new JButton("Load pic");
		btnChoosePic.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneChoosePic.add(btnChoosePic);
		
		JPanel paneTxt = new JPanel();
		paneTxt.setBorder(new EmptyBorder(10, 10, 10, 10));
		paneTxt.setBackground(Color.WHITE);
		paneConferenciers.add(paneTxt, BorderLayout.CENTER);
		paneTxt.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel paneNNP = new JPanel();
		paneNNP.setBorder(new EmptyBorder(10, 10, 0, 10));
		paneNNP.setBackground(Color.WHITE);
		paneTxt.add(paneNNP);
		paneNNP.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel paneNo = new JPanel();
		paneNo.setBackground(Color.WHITE);
		paneNNP.add(paneNo);
		paneNo.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNo = new JLabel("N\u00B0 conf\u00E9rencier:");
		lblNo.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneNo.add(lblNo);
		
		textFieldNo = new JTextField();
		textFieldNo.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNo.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneNo.add(textFieldNo);
		textFieldNo.setColumns(10);
		
		JPanel paneNom = new JPanel();
		paneNom.setBackground(Color.WHITE);
		paneNNP.add(paneNom);
		paneNom.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneNom.add(lblNom);
		
		textFieldNom = new JTextField();
		textFieldNom.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNom.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneNom.add(textFieldNom);
		textFieldNom.setColumns(10);
		
		JPanel panePrenom = new JPanel();
		panePrenom.setBackground(Color.WHITE);
		paneNNP.add(panePrenom);
		panePrenom.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPrenom = new JLabel("Pr\u00E9nom");
		lblPrenom.setFont(new Font("Dialog", Font.PLAIN, 20));
		panePrenom.add(lblPrenom);
		
		textFieldPrenom = new JTextField();
		textFieldPrenom.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPrenom.setFont(new Font("Dialog", Font.PLAIN, 20));
		panePrenom.add(textFieldPrenom);
		textFieldPrenom.setColumns(10);
		
		JPanel paneBio = new JPanel();
		paneBio.setBorder(new EmptyBorder(0, 10, 10, 10));
		paneBio.setBackground(Color.WHITE);
		paneTxt.add(paneBio);
		paneBio.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblBio = new JLabel("Bio:");
		lblBio.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneBio.add(lblBio);
		
		textFieldBio = new JTextField();
		textFieldBio.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldBio.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneBio.add(textFieldBio);
		textFieldBio.setColumns(10);
		
		JPanel paneValider = new JPanel();
		add(paneValider, BorderLayout.SOUTH);
		paneValider.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneValider.setBackground(Color.WHITE);
		paneValider.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JButton btnValider = new JButton("Valider");
		btnValider.setFont(new Font("Dialog", Font.PLAIN, 20));
		paneValider.add(btnValider);

	}

}
