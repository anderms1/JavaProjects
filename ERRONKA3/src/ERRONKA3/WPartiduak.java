package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import ERRONKA3.DAO.DenboraldiaDAO;
import ERRONKA3.DAO.JardunaldiaDAO;
import ERRONKA3.DAO.PartiduaDAO;
import ERRONKA3.DAO.TaldeaDAO;
import ERRONKA3.klaseak.Denboraldia;
import ERRONKA3.klaseak.Jardunaldia;
import ERRONKA3.klaseak.Partidua;
import ERRONKA3.klaseak.Taldea;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class WPartiduak extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtP2EtxekoGolak;
	private JTextField txtP2BisitariGolak;
	private ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
	private ArrayList<Denboraldia> denboraldia = new ArrayList<Denboraldia>();
	private ArrayList<Partidua> partiduak = new ArrayList<Partidua>();
	private JComboBox cmbJardunaldiak;
	private JTextField txtP1EtxekoGolak;
	private JTextField txtP1BisitariGolak;
	private JTextField txtP3EtxekoGolak;
	private JTextField txtP3BisitariGolak;
	private JLabel lblP1Etxeko;
	private JLabel lblP1Bisitari;
	private JLabel lblP2Etxeko;
	private JLabel lblP2Bisitari;
	private JLabel lblP3Etxeko;
	private JLabel lblP3Bisitari;
	

	/**
	 * Create the panel.
	 */
	public WPartiduak() {
		setLayout(null);
		setBounds(100, 100, 650, 450);
		
		
		JLabel lblNewLabel = new JLabel("PARTIDUAK");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 595, 29);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 49, 595, 311);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Etxeko Taldea");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel_1.setBounds(26, 67, 197, 17);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Bisitari Taldea");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(373, 67, 197, 17);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Vs");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_2.setBounds(279, 154, 35, 34);
		panel.add(lblNewLabel_2);
		
		JButton btnGorde = new JButton("Gorde");
		btnGorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(txtP1EtxekoGolak.getText().isEmpty() & txtP1BisitariGolak.getText().isEmpty() & txtP2EtxekoGolak.getText().isEmpty() & txtP2BisitariGolak.getText().isEmpty() & txtP3EtxekoGolak.getText().isEmpty() & txtP3BisitariGolak.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Partiduak sartu behar dituzu.","Error",JOptionPane.ERROR_MESSAGE);
					}else if((txtP1EtxekoGolak.getText().isEmpty() == true & txtP1BisitariGolak.getText().isEmpty() == false) || (txtP1BisitariGolak.getText().isEmpty() == true & txtP1EtxekoGolak.getText().isEmpty() == false)) {
						JOptionPane.showMessageDialog(null,"Lehenengo partiduen golak sartu behar dira.","Informazioa",JOptionPane.INFORMATION_MESSAGE);
					}
					else if((txtP2EtxekoGolak.getText().isEmpty() == true & txtP2BisitariGolak.getText().isEmpty() == false) || (txtP2BisitariGolak.getText().isEmpty() == true & txtP2EtxekoGolak.getText().isEmpty() == false)) {
						JOptionPane.showMessageDialog(null,"Bigarren partiduen golak sartu behar dira.","Informazioa",JOptionPane.INFORMATION_MESSAGE);
					}
					else if((txtP3EtxekoGolak.getText().isEmpty() == true & txtP3BisitariGolak.getText().isEmpty() == false) || (txtP3BisitariGolak.getText().isEmpty() == true & txtP3EtxekoGolak.getText().isEmpty() == false)) {
						JOptionPane.showMessageDialog(null,"Hirugarren partiduen golak sartu behar dira.","Informazioa",JOptionPane.INFORMATION_MESSAGE);
					}else {	
						int posizioa = cmbJardunaldiak.getSelectedIndex();
						Jardunaldia jardunaldia = denboraldia.get(0).getJardunaldiak().get(posizioa);
						puntuakEman(jardunaldia);
					}
				} catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea Partidua sartzean.","Error",JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
			}
		});
		btnGorde.setFont(new Font("Arial", Font.PLAIN, 15));
		btnGorde.setBounds(251, 276, 100, 25);
		panel.add(btnGorde);
		
		JLabel lblNewLabel_3 = new JLabel("Jardunaldia");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(26, 0, 544, 27);
		panel.add(lblNewLabel_3);
		
		cmbJardunaldiak = new JComboBox();
		cmbJardunaldiak.setBackground(new Color(255, 255, 255));
		cmbJardunaldiak.setFont(new Font("Arial", Font.PLAIN, 15));
		cmbJardunaldiak.setBounds(202, 37, 197, 34);
		panel.add(cmbJardunaldiak);
		
		txtP2EtxekoGolak = new JTextField();
		txtP2EtxekoGolak.setFont(new Font("Arial", Font.PLAIN, 15));
		txtP2EtxekoGolak.setHorizontalAlignment(SwingConstants.CENTER);
		txtP2EtxekoGolak.setBounds(233, 154, 48, 34);
		panel.add(txtP2EtxekoGolak);
		txtP2EtxekoGolak.setColumns(10);
		
		txtP2BisitariGolak = new JTextField();
		txtP2BisitariGolak.setHorizontalAlignment(SwingConstants.CENTER);
		txtP2BisitariGolak.setFont(new Font("Arial", Font.PLAIN, 15));
		txtP2BisitariGolak.setColumns(10);
		txtP2BisitariGolak.setBounds(315, 154, 48, 34);
		panel.add(txtP2BisitariGolak);
		
		txtP1EtxekoGolak = new JTextField();
		txtP1EtxekoGolak.setHorizontalAlignment(SwingConstants.CENTER);
		txtP1EtxekoGolak.setFont(new Font("Arial", Font.PLAIN, 15));
		txtP1EtxekoGolak.setColumns(10);
		txtP1EtxekoGolak.setBounds(233, 99, 48, 34);
		panel.add(txtP1EtxekoGolak);
		
		JLabel lblNewLabel_2_1 = new JLabel("Vs");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(279, 99, 35, 34);
		panel.add(lblNewLabel_2_1);
		
		txtP1BisitariGolak = new JTextField();
		txtP1BisitariGolak.setHorizontalAlignment(SwingConstants.CENTER);
		txtP1BisitariGolak.setFont(new Font("Arial", Font.PLAIN, 15));
		txtP1BisitariGolak.setColumns(10);
		txtP1BisitariGolak.setBounds(315, 99, 48, 34);
		panel.add(txtP1BisitariGolak);
		
		txtP3EtxekoGolak = new JTextField();
		txtP3EtxekoGolak.setHorizontalAlignment(SwingConstants.CENTER);
		txtP3EtxekoGolak.setFont(new Font("Arial", Font.PLAIN, 15));
		txtP3EtxekoGolak.setColumns(10);
		txtP3EtxekoGolak.setBounds(233, 212, 48, 34);
		panel.add(txtP3EtxekoGolak);
		
		JLabel lblNewLabel_2_2 = new JLabel("Vs");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel_2_2.setBounds(279, 212, 35, 34);
		panel.add(lblNewLabel_2_2);
		
		txtP3BisitariGolak = new JTextField();
		txtP3BisitariGolak.setHorizontalAlignment(SwingConstants.CENTER);
		txtP3BisitariGolak.setFont(new Font("Arial", Font.PLAIN, 15));
		txtP3BisitariGolak.setColumns(10);
		txtP3BisitariGolak.setBounds(315, 212, 48, 34);
		panel.add(txtP3BisitariGolak);
		
		lblP1Etxeko = new JLabel("Taldea");
		lblP1Etxeko.setBackground(new Color(255, 255, 255));
		lblP1Etxeko.setHorizontalAlignment(SwingConstants.LEFT);
		lblP1Etxeko.setFont(new Font("Arial", Font.PLAIN, 15));
		lblP1Etxeko.setBounds(26, 104, 182, 25);
		panel.add(lblP1Etxeko);
		
		lblP1Bisitari = new JLabel("Taldea");
		lblP1Bisitari.setHorizontalAlignment(SwingConstants.RIGHT);
		lblP1Bisitari.setFont(new Font("Arial", Font.PLAIN, 15));
		lblP1Bisitari.setBounds(388, 104, 182, 25);
		panel.add(lblP1Bisitari);
		
		lblP2Etxeko = new JLabel("Taldea");
		lblP2Etxeko.setHorizontalAlignment(SwingConstants.LEFT);
		lblP2Etxeko.setFont(new Font("Arial", Font.PLAIN, 15));
		lblP2Etxeko.setBackground(Color.WHITE);
		lblP2Etxeko.setBounds(26, 154, 182, 34);
		panel.add(lblP2Etxeko);
		
		lblP2Bisitari = new JLabel("Taldea");
		lblP2Bisitari.setHorizontalAlignment(SwingConstants.RIGHT);
		lblP2Bisitari.setFont(new Font("Arial", Font.PLAIN, 15));
		lblP2Bisitari.setBounds(388, 154, 182, 34);
		panel.add(lblP2Bisitari);
		
		lblP3Etxeko = new JLabel("Taldea");
		lblP3Etxeko.setHorizontalAlignment(SwingConstants.LEFT);
		lblP3Etxeko.setFont(new Font("Arial", Font.PLAIN, 15));
		lblP3Etxeko.setBackground(Color.WHITE);
		lblP3Etxeko.setBounds(26, 212, 182, 34);
		panel.add(lblP3Etxeko);
		
		lblP3Bisitari = new JLabel("Taldea");
		lblP3Bisitari.setHorizontalAlignment(SwingConstants.RIGHT);
		lblP3Bisitari.setFont(new Font("Arial", Font.PLAIN, 15));
		lblP3Bisitari.setBounds(388, 212, 182, 34);
		panel.add(lblP3Bisitari);
		
		taldeakArrayListGorde();
		jokatzenDenboraldiaArrayListGorde();
		partiduakJardunaldianGorde();
		updateComboxak();
		
		cmbJardunaldiak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partiduakIrakutsi();
			}
		});
	}
	
	public void taldeakArrayListGorde() {
		taldeakList.clear();
		
		TaldeaDAO taldeaDao = new TaldeaDAO();	
		
		taldeakList = taldeaDao.getAllTaldeak();
		
		taldeaDao.deskonektatu();
	}
	
	public void jokatzenDenboraldiaArrayListGorde() {
		denboraldia.clear();
		
		DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
		JardunaldiaDAO jardunaldiaDao = new JardunaldiaDAO();
		if(denboraldia.size() >= 0) {
			denboraldia = denboraldiaDao.getJokatzenDenboraldia();
			denboraldia.get(0).setJardunaldiak(jardunaldiaDao.denboraldiJardunaldiakLortu(denboraldia.get(0)));
		}
		denboraldiaDao.deskonektatu();
		jardunaldiaDao.deskonektatu();
	}
	
	public void partiduakJardunaldianGorde(){
		partiduak.clear();
		
		PartiduaDAO partiduaDao = new PartiduaDAO();
		int i = 0;
		if(!partiduak.isEmpty()) {
			for(Jardunaldia jardunaldia : denboraldia.get(0).getJardunaldiak()) {
				partiduak = partiduaDao.jardunaldiPartiduakLortu(jardunaldia);
				denboraldia.get(0).getJardunaldiak().get(i).setPartiduak(partiduak);
				i++;
			}
		}
		partiduaDao.deskonektatu();
	}
	
	public void updateComboxak() {
		Denboraldia denb =  new Denboraldia();
		if (denboraldia.size() > 0) {
			denb.setJardunaldiak(denboraldia.get(0).getJardunaldiak());
		}
		if(cmbJardunaldiak != null) {
			cmbJardunaldiak.removeAllItems();
		}
		for(Jardunaldia jardunaldia : denb.getJardunaldiak()) {
			cmbJardunaldiak.addItem("Jardunaldia: "+jardunaldia.getHasierako_data());	
		}
	}
	
	public void partiduakIrakutsi() {
		int i = cmbJardunaldiak.getSelectedIndex();
		PartiduaDAO partiduaDao = new PartiduaDAO();
		ArrayList<Partidua> partiduak = denboraldia.get(0).getJardunaldiak().get(i).getPartiduak();
		
		//Lehengo partidua
		lblP1Bisitari.setText(partiduak.get(0).getKanpoko_talde().getTalde_izena());
		lblP1Etxeko.setText(partiduak.get(0).getEtxeko_talde().getTalde_izena());
		partiduak.get(0).setDenboraldia(denboraldia.get(0));
		partiduak.get(0).setJardunaldia(denboraldia.get(0).getJardunaldiak().get(i));
		boolean jolastuta1 = partiduaDao.partiduaAmaitutaGaldetu(partiduak.get(0));
		if(jolastuta1 == true) {
			lblP1Bisitari.setForeground(Color.lightGray);
			lblP1Etxeko.setForeground(Color.lightGray);
			txtP1BisitariGolak.setEditable(false);
			txtP1EtxekoGolak.setEditable(false);
		}else {
			lblP1Bisitari.setForeground(Color.black);
			lblP1Etxeko.setForeground(Color.black);
			txtP1BisitariGolak.setEditable(true);
			txtP1EtxekoGolak.setEditable(true);
		}
		
		//Bigarren partidua
		lblP2Bisitari.setText(partiduak.get(1).getKanpoko_talde().getTalde_izena());
		lblP2Etxeko.setText(partiduak.get(1).getEtxeko_talde().getTalde_izena());
		partiduak.get(1).setDenboraldia(denboraldia.get(0));
		partiduak.get(1).setJardunaldia(denboraldia.get(0).getJardunaldiak().get(i));
		boolean jolastuta2 = partiduaDao.partiduaAmaitutaGaldetu(partiduak.get(1));
		if(jolastuta2 == true) {
			lblP2Bisitari.setForeground(Color.lightGray);
			lblP2Etxeko.setForeground(Color.lightGray);
			txtP2BisitariGolak.setEditable(false);
			txtP2EtxekoGolak.setEditable(false);
		}else {
			lblP2Bisitari.setForeground(Color.black);
			lblP2Etxeko.setForeground(Color.black);
			txtP2BisitariGolak.setEditable(true);
			txtP2EtxekoGolak.setEditable(true);
		}
		
		//Hirugarren partidua
		lblP3Bisitari.setText(partiduak.get(2).getKanpoko_talde().getTalde_izena());
		lblP3Etxeko.setText(partiduak.get(2).getEtxeko_talde().getTalde_izena());
		partiduak.get(2).setDenboraldia(denboraldia.get(0));
		partiduak.get(2).setJardunaldia(denboraldia.get(0).getJardunaldiak().get(i));
		boolean jolastuta3 = partiduaDao.partiduaAmaitutaGaldetu(partiduak.get(2));
		if(jolastuta3 == true) {
			lblP3Bisitari.setForeground(Color.lightGray);
			lblP3Etxeko.setForeground(Color.lightGray);
			txtP3BisitariGolak.setEditable(false);
			txtP3EtxekoGolak.setEditable(false);
		}else {
			lblP3Bisitari.setForeground(Color.black);
			lblP3Etxeko.setForeground(Color.black);
			txtP3BisitariGolak.setEditable(true);
			txtP3EtxekoGolak.setEditable(true);
		}
		partiduaDao.deskonektatu();
	}
	
	public void puntuakEman(Jardunaldia jardunaldia) {
		TaldeaDAO taldeaDao = new TaldeaDAO();
		PartiduaDAO partiduaDao = new PartiduaDAO();
		boolean gorde = false;
		
		int confirm = JOptionPane.showConfirmDialog(null, "Ziur zaude emaitza hau dela?", "Berretsi emaitza", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			//Lehengo partidua
			if(!txtP1EtxekoGolak.getText().isEmpty() && !txtP1BisitariGolak.getText().isEmpty()) {
				Partidua partidua1 = jardunaldia.getPartiduak().get(0);
				boolean amaituta1 = partiduaDao.partiduaAmaitutaGaldetu(partidua1);
				if(amaituta1 == true) {
					JOptionPane.showMessageDialog(null, "Lehen partiduen emaitza sartuta dago","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					//Taldeak lortu egiten ditugu
					Taldea etxekoTaldea = taldeaDao.getTaldeaIzenarekin(lblP1Etxeko.getText());
					Taldea kanpokoTaldea = taldeaDao.getTaldeaIzenarekin(lblP1Bisitari.getText());
					
					//Partiduen emaitza sartzen dugu
					int etxekoGolak = Integer.parseInt(txtP1EtxekoGolak.getText());
					int kanpokoGolak = Integer.parseInt(txtP1BisitariGolak.getText());
					partidua1.setEtxekoGolak(etxekoGolak);
					partidua1.setKanpokoGolak(kanpokoGolak);
					partiduaDao.updatePartidua(partidua1);
					
					//Puntuak eman
					if(etxekoGolak > kanpokoGolak) {
						etxekoTaldea.gehituWins();
						kanpokoTaldea.gehituDefeats();
					}else if(etxekoGolak < kanpokoGolak) {
						etxekoTaldea.gehituDefeats();
						kanpokoTaldea.gehituWins();
					}else {
						etxekoTaldea.gehituTies();
						kanpokoTaldea.gehituTies();
					}
					partiduaDao.updateDenb_taldea(etxekoTaldea, jardunaldia.getDenboraldia_kod());
					partiduaDao.updateDenb_taldea(kanpokoTaldea, jardunaldia.getDenboraldia_kod());
					lblP1Bisitari.setForeground(Color.lightGray);
					lblP1Etxeko.setForeground(Color.lightGray);
					txtP1BisitariGolak.setText("");
					txtP1EtxekoGolak.setText("");
					
					gorde = true;
				}
			}
			
			
			//Bigarren partidua
			if(!txtP2EtxekoGolak.getText().isEmpty() && !txtP2BisitariGolak.getText().isEmpty()) {
				Partidua partidua2 = jardunaldia.getPartiduak().get(1);
				boolean amaituta2 = partiduaDao.partiduaAmaitutaGaldetu(partidua2);
				if(amaituta2 == true) {
					JOptionPane.showMessageDialog(null, "Bigarren partiduen emaitza sartuta dago","Error",JOptionPane.ERROR_MESSAGE);
				}else{
					Taldea etxekoTaldea = taldeaDao.getTaldeaIzenarekin(lblP2Etxeko.getText());
					Taldea kanpokoTaldea = taldeaDao.getTaldeaIzenarekin(lblP2Bisitari.getText());
					
					//Partiduen emaitza sartzen dugu
					int etxekoGolak = Integer.parseInt(txtP2EtxekoGolak.getText());
					int kanpokoGolak = Integer.parseInt(txtP2BisitariGolak.getText());
					partidua2.setEtxekoGolak(etxekoGolak);
					partidua2.setKanpokoGolak(kanpokoGolak);
					partiduaDao.updatePartidua(partidua2);
					
					//Puntuak eman
					if(etxekoGolak > kanpokoGolak) {
						etxekoTaldea.gehituWins();
						kanpokoTaldea.gehituDefeats();
					}else if(etxekoGolak < kanpokoGolak) {
						etxekoTaldea.gehituDefeats();
						kanpokoTaldea.gehituWins();
					}else {
						etxekoTaldea.gehituTies();
						kanpokoTaldea.gehituTies();
					}
					partiduaDao.updateDenb_taldea(etxekoTaldea, jardunaldia.getDenboraldia_kod());
					partiduaDao.updateDenb_taldea(kanpokoTaldea, jardunaldia.getDenboraldia_kod());
					lblP2Bisitari.setForeground(Color.lightGray);
					lblP2Etxeko.setForeground(Color.lightGray);
					txtP2BisitariGolak.setText("");
					txtP2EtxekoGolak.setText("");
					
					gorde = true;
				}
			}
			
			
			//Hirugarren partidua
			if(!txtP3EtxekoGolak.getText().isEmpty() && !txtP3BisitariGolak.getText().isEmpty()) {
				Partidua partidua3 = jardunaldia.getPartiduak().get(2);
				boolean amaituta3 = partiduaDao.partiduaAmaitutaGaldetu(partidua3);
				if(amaituta3 == true) {
					JOptionPane.showMessageDialog(null, "Hirugarren partiduen emaitza sartuta dago","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					Taldea etxekoTaldea = taldeaDao.getTaldeaIzenarekin(lblP3Etxeko.getText());
					Taldea kanpokoTaldea = taldeaDao.getTaldeaIzenarekin(lblP3Bisitari.getText());
					
					//Partiduen emaitza sartzen dugu
					int etxekoGolak = Integer.parseInt(txtP3EtxekoGolak.getText());
					int kanpokoGolak = Integer.parseInt(txtP3BisitariGolak.getText());
					partidua3.setEtxekoGolak(etxekoGolak);
					partidua3.setKanpokoGolak(kanpokoGolak);
					partiduaDao.updatePartidua(partidua3);
					
					//Puntuak eman
					if(etxekoGolak > kanpokoGolak) {
						etxekoTaldea.gehituWins();
						kanpokoTaldea.gehituDefeats();
					}else if(etxekoGolak < kanpokoGolak) {
						etxekoTaldea.gehituDefeats();
						kanpokoTaldea.gehituWins();
					}else {
						etxekoTaldea.gehituTies();
						kanpokoTaldea.gehituTies();
					}
					partiduaDao.updateDenb_taldea(etxekoTaldea, jardunaldia.getDenboraldia_kod());
					partiduaDao.updateDenb_taldea(kanpokoTaldea, jardunaldia.getDenboraldia_kod());
					lblP3Bisitari.setForeground(Color.lightGray);
					lblP3Etxeko.setForeground(Color.lightGray);
					txtP3BisitariGolak.setText("");
					txtP3EtxekoGolak.setText("");
					
					gorde = true;
				}
			}
			taldeaDao.deskonektatu();
			partiduaDao.deskonektatu();
			
			if(gorde == true) {
				JOptionPane.showMessageDialog(null, "Partidua(k) gorde egin dira.","Informazioa",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
