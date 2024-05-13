package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ERRONKA3.DAO.DenboraldiaDAO;
import ERRONKA3.DAO.denb_taldeaDAO;
import ERRONKA3.klaseak.Denboraldia;
import ERRONKA3.klaseak.Taldea;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WSailkapena extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
	private ArrayList<Denboraldia> jokatzenDenb = new ArrayList<Denboraldia>();
	private JTable table;

	/**
	 * Create the panel.
	 */
	public WSailkapena() {
		setLayout(null);
		setBounds(100, 100, 650, 450);
		
		JLabel lblNewLabel = new JLabel("Sailkapena");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 25));
		lblNewLabel.setBounds(10, 10, 597, 27);
		add(lblNewLabel);
		
		table = new JTable();
		Object[] zutabeIzenak = {"Taldea", "Wins", "Defeats","Ties","Puntuak"};
		model = new DefaultTableModel(null, zutabeIzenak);
		table.setModel(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(12, 71, 597, 250);
		add(scrollPane);
		
		// Sortu TableCellRenderer bat edukia zentratzeko
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		//Renderer-ra zutabe guztiei aplikatzea
		for (int i = 0; i < table.getColumnCount(); i++) {
		    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		jokatzenDenboraldiaGorde();
		taulaEguneratu();
	}
	/**
	 * Funtzio hau ArrayList batean jokatzen ari den denboraldia gordetzeko erabiltzen da.
	 */
	public void jokatzenDenboraldiaGorde() {
		jokatzenDenb.clear();
		
		DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
		
		jokatzenDenb = denboraldiaDao.getJokatzenDenboraldia();
		
		denboraldiaDao.deskonektatu();
	}
	/**
	 * Funtzio hau jokatzen hari diren denboraldi taldeak ArrayList batean gordetzeko da.
	 */
	public void taldeakArrayListGorde() {
		taldeakList.clear();
		
		denb_taldeaDAO denb_taldeaDao = new denb_taldeaDAO();
		
		taldeakList = denb_taldeaDao.getJokatzenTaldeakList(jokatzenDenb.get(0));
		
		denb_taldeaDao.deskonektatu();
	}
	/**
	 * Funtzio hau deitzena taula eguneratu eta ordenatu egingo da puntuen arabaera.
	 */
	public void taulaEguneratu() {
		taldeakArrayListGorde();
		
		model.setRowCount(0);
		Taldea swap;
		boolean aldaketak = false;
		
		while(true) {
			aldaketak = false;
			for (int i = 1; i < taldeakList.size(); i++){
				if (taldeakList.get(i).getPuntuak() > taldeakList.get(i-1).getPuntuak())
				{
					swap = taldeakList.get(i);
					taldeakList.set(i,taldeakList.get(i-1));
					taldeakList.set(i-1,swap);
					
					aldaketak = true;
				}
			}
			if(aldaketak == false)
			{
				break;
			}
		}
		
		for(Taldea taldea : taldeakList) {
			// Verificar si ya existe una fila con el mismo valor en la primera columna
		    boolean sartuta = false;
		    for (int i = 0; i < model.getRowCount(); i++) {
		        if (taldea.getTalde_izena().equals(model.getValueAt(i, 0))) {
		            sartuta = true;
		            break;
		        }
		    }
		    
		 // Si no está añadido, añadir la fila
		    if (sartuta == false) {
		        Object[] rowData = {taldea.getTalde_izena(), taldea.getWins(), taldea.getDefeats(), taldea.getTies(), taldea.getPuntuak()};
		        model.addRow(rowData);
		    }   
		}
		for(int i = 0;i < taldeakList.size();i++) {
			table.setRowHeight(i, 38);
		}
	}
}
