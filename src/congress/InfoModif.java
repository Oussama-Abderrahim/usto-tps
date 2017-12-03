package congress;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class InfoModif extends JPanel {
	private JTextField nomCongres;
	private JTextField jourDu;
	private JTextField moisDu;
	private JTextField anneeDu;
	private JTextField jourAu;
	private JTextField moisAu;
	private JTextField anneeAu;
	private JTextField jourInscription;
	private JTextField moisInscription;
	private JTextField anneeInscription;
	private JTextField adresse;

	/**
	 * Create the panel.
	 */
	public InfoModif() {
		setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 800, 400);
		
		JPanel paneTitre = new JPanel();
		paneTitre.setBackground(Color.WHITE);
		add(paneTitre, BorderLayout.NORTH);
		
		JLabel lblTitre = new JLabel("Modifier informations du congr\u00E8s");
		paneTitre.add(lblTitre);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JPanel paneInfo = new JPanel();
		paneInfo.setBackground(Color.WHITE);
		add(paneInfo, BorderLayout.CENTER);
		paneInfo.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel paneNom = new JPanel();
		paneInfo.add(paneNom);
		paneNom.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneNom.setBackground(Color.WHITE);
		paneNom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		nomCongres = new JTextField();
		nomCongres.setHorizontalAlignment(SwingConstants.CENTER);
		nomCongres.setFont(new Font("Tahoma", Font.PLAIN, 20));
		nomCongres.setColumns(10);
		paneNom.add(nomCongres);
		
		JPanel paneDateDu = new JPanel();
		paneInfo.add(paneDateDu);
		paneDateDu.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneDateDu.setBackground(Color.WHITE);
		paneDateDu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label_1 = new JLabel("Du");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneDateDu.add(label_1);
		
		jourDu = new JTextField();
		jourDu.setHorizontalAlignment(SwingConstants.CENTER);
		jourDu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jourDu.setColumns(10);
		paneDateDu.add(jourDu);
		
		JLabel label_2 = new JLabel("/");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneDateDu.add(label_2);
		
		moisDu = new JTextField();
		moisDu.setHorizontalAlignment(SwingConstants.CENTER);
		moisDu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		moisDu.setColumns(10);
		paneDateDu.add(moisDu);
		
		JLabel label_3 = new JLabel("/");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneDateDu.add(label_3);
		
		anneeDu = new JTextField();
		anneeDu.setHorizontalAlignment(SwingConstants.CENTER);
		anneeDu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		anneeDu.setColumns(10);
		paneDateDu.add(anneeDu);
		
		JPanel paneDateAu = new JPanel();
		paneDateAu.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneInfo.add(paneDateAu);
		paneDateAu.setBackground(Color.WHITE);
		
		JLabel label_4 = new JLabel("Au");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneDateAu.add(label_4);
		
		jourAu = new JTextField();
		jourAu.setHorizontalAlignment(SwingConstants.CENTER);
		jourAu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jourAu.setColumns(10);
		paneDateAu.add(jourAu);
		
		JLabel label_5 = new JLabel("/");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneDateAu.add(label_5);
		
		moisAu = new JTextField();
		moisAu.setHorizontalAlignment(SwingConstants.CENTER);
		moisAu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		moisAu.setColumns(10);
		paneDateAu.add(moisAu);
		
		JLabel label_6 = new JLabel("/");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneDateAu.add(label_6);
		
		anneeAu = new JTextField();
		anneeAu.setHorizontalAlignment(SwingConstants.CENTER);
		anneeAu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		anneeAu.setColumns(10);
		paneDateAu.add(anneeAu);
		
		JPanel paneInscription = new JPanel();
		paneInfo.add(paneInscription);
		paneInscription.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneInscription.setBackground(Color.WHITE);
		paneInscription.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label_7 = new JLabel("Inscription ouverte au");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneInscription.add(label_7);
		
		jourInscription = new JTextField();
		jourInscription.setHorizontalAlignment(SwingConstants.CENTER);
		jourInscription.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jourInscription.setColumns(10);
		paneInscription.add(jourInscription);
		
		JLabel label_8 = new JLabel("/");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneInscription.add(label_8);
		
		moisInscription = new JTextField();
		moisInscription.setHorizontalAlignment(SwingConstants.CENTER);
		moisInscription.setFont(new Font("Tahoma", Font.PLAIN, 20));
		moisInscription.setColumns(10);
		paneInscription.add(moisInscription);
		
		JLabel label_9 = new JLabel("/");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneInscription.add(label_9);
		
		anneeInscription = new JTextField();
		anneeInscription.setHorizontalAlignment(SwingConstants.CENTER);
		anneeInscription.setFont(new Font("Tahoma", Font.PLAIN, 20));
		anneeInscription.setColumns(10);
		paneInscription.add(anneeInscription);
		
		JPanel paneAdresse = new JPanel();
		paneInfo.add(paneAdresse);
		paneAdresse.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneAdresse.setBackground(Color.WHITE);
		paneAdresse.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		adresse = new JTextField();
		adresse.setHorizontalAlignment(SwingConstants.CENTER);
		adresse.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adresse.setColumns(10);
		paneAdresse.add(adresse);
		
		JButton button = new JButton("local");
		button.setFont(new Font("Tahoma", Font.PLAIN, 20));
		paneAdresse.add(button);
		
		JPanel paneValider = new JPanel();
		paneValider.setBackground(Color.WHITE);
		add(paneValider, BorderLayout.SOUTH);
		
		JButton btnValider = new JButton("Valider");
		paneValider.add(btnValider);
		btnValider.setFont(new Font("Tahoma", Font.PLAIN, 20));

	}

}
