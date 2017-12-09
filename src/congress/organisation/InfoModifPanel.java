package congress.organisation;

import congress.DatabaseManager;
import congress.FileManager;
import congress.theme.*;

import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoModifPanel extends SPanel
{
	private STextField nomCongres;
	private STextField citationCongress;
	private STextField jourDu;
	private STextField moisDu;
	private STextField anneeDu;
	private STextField jourAu;
	private STextField moisAu;
	private STextField anneeAu;
	private STextField jourInscription;
	private STextField moisInscription;
	private STextField anneeInscription;
	private STextField adresse;

	/**
	 * Create the panel.
	 */
	public InfoModifPanel() {
		setLayout(new BorderLayout(0, 0));
		setBounds(100, 100, 800, 400);
		
		SPanel paneTitre = new SPanel();
		add(paneTitre, BorderLayout.NORTH);
		
		SLabel lblTitre = new SLabel("Modifier informations du congr\u00E8s", Theme.FONT_DEFAULT_LARGE);
		paneTitre.add(lblTitre);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);

		SPanel paneInfo = new SPanel();
		add(paneInfo, BorderLayout.CENTER);
		paneInfo.setLayout(new GridLayout(0, 1, 0, 0));
		
		SPanel paneNom = new SPanel();
		paneInfo.add(paneNom);
		paneNom.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneNom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		nomCongres = new STextField();
		nomCongres.setHorizontalAlignment(SwingConstants.CENTER);
		nomCongres.setColumns(20);
		nomCongres.setFont(Theme.FONT_DEFAULT_LARGE);
		paneNom.add(nomCongres);

		SPanel paneCitation = new SPanel();
		paneInfo.add(paneCitation);
		paneCitation.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneCitation.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		citationCongress = new STextField();
		citationCongress.setHorizontalAlignment(SwingConstants.CENTER);
		citationCongress.setColumns(20);
		citationCongress.setFont(Theme.FONT_DEFAULT_LARGE);
		paneCitation.add(citationCongress);
		
		SPanel paneDateDu = new SPanel();
		paneInfo.add(paneDateDu);
		paneDateDu.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneDateDu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		SLabel label_1 = new SLabel("Du", Theme.FONT_DEFAULT_LARGE);
		paneDateDu.add(label_1);
		
		jourDu = new STextField();
		jourDu.setOpaque(false);
		jourDu.setHorizontalAlignment(SwingConstants.CENTER);
		jourDu.setColumns(2);
		paneDateDu.add(jourDu);
		
		SLabel label_2 = new SLabel("/", Theme.FONT_DEFAULT_LARGE);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		paneDateDu.add(label_2);
		
		moisDu = new STextField();
		moisDu.setHorizontalAlignment(SwingConstants.CENTER);
		moisDu.setColumns(2);
		paneDateDu.add(moisDu);
		
		SLabel label_3 = new SLabel("/", Theme.FONT_DEFAULT_LARGE);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		paneDateDu.add(label_3);
		
		anneeDu = new STextField();
		anneeDu.setHorizontalAlignment(SwingConstants.CENTER);
		anneeDu.setColumns(4);
		paneDateDu.add(anneeDu);
		
		SPanel paneDateAu = new SPanel();
		paneDateAu.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneInfo.add(paneDateAu);
		
		SLabel label_4 = new SLabel("Au", Theme.FONT_DEFAULT_LARGE);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		paneDateAu.add(label_4);
		
		jourAu = new STextField();
		jourAu.setHorizontalAlignment(SwingConstants.CENTER);
		jourAu.setColumns(2);
		paneDateAu.add(jourAu);
		
		SLabel label_5 = new SLabel("/", Theme.FONT_DEFAULT_LARGE);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		paneDateAu.add(label_5);
		
		moisAu = new STextField();
		moisAu.setHorizontalAlignment(SwingConstants.CENTER);
		moisAu.setColumns(2);
		paneDateAu.add(moisAu);
		
		SLabel label_6 = new SLabel("/", Theme.FONT_DEFAULT_LARGE);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		paneDateAu.add(label_6);
		
		anneeAu = new STextField();
		anneeAu.setHorizontalAlignment(SwingConstants.CENTER);
		anneeAu.setColumns(4);
		paneDateAu.add(anneeAu);
		
		SPanel paneInscription = new SPanel();
		paneInfo.add(paneInscription);
		paneInscription.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneInscription.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		SLabel label_7 = new SLabel("Inscription ouverte au", Theme.FONT_DEFAULT_LARGE);
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		paneInscription.add(label_7);
		
		jourInscription = new STextField();
		jourInscription.setHorizontalAlignment(SwingConstants.CENTER);
		jourInscription.setColumns(2);
		paneInscription.add(jourInscription);
		
		SLabel label_8 = new SLabel("/", Theme.FONT_DEFAULT_LARGE);
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		paneInscription.add(label_8);
		
		moisInscription = new STextField();
		moisInscription.setHorizontalAlignment(SwingConstants.CENTER);
		moisInscription.setColumns(2);
		paneInscription.add(moisInscription);
		
		SLabel label_9 = new SLabel("/", Theme.FONT_DEFAULT_LARGE);
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		paneInscription.add(label_9);
		
		anneeInscription = new STextField();
		anneeInscription.setHorizontalAlignment(SwingConstants.CENTER);
		anneeInscription.setColumns(4);
		paneInscription.add(anneeInscription);
		
		SPanel paneAdresse = new SPanel();
		paneInfo.add(paneAdresse);
		paneAdresse.setBorder(new EmptyBorder(10, 0, 10, 0));
		paneAdresse.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		adresse = new STextField();
		adresse.setHorizontalAlignment(SwingConstants.CENTER);
		adresse.setColumns(30);
		paneAdresse.add(adresse);
		
		SButton button = new SButton("");
		button.setIcon(FileManager.loadImage("local", 28, 28));
		button.setSize(28, 28);
		paneAdresse.add(button);
		
		SPanel paneValider = new SPanel();
		add(paneValider, BorderLayout.SOUTH);
		
		SButton btnValider = new SButton("Valider");
		btnValider.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				DatabaseManager.getInstance().updateCongressData(nomCongres.getText(), jourDu.getText(), moisDu.getText(), anneeDu.getText(), jourAu.getText(), moisAu.getText(), anneeAu.getText(), jourInscription.getText(), moisInscription.getText(), anneeInscription.getText(), adresse.getText(), citationCongress.getText());
			}
		});
		paneValider.add(btnValider);

	}

}
