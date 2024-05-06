package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import ERRONKA3.DAO.DenboraldiaDAO;
import ERRONKA3.DAO.TaldeaDAO;
import ERRONKA3.DAO.denb_taldeaDAO;
import ERRONKA3.klaseak.Denboraldia;
import ERRONKA3.klaseak.Taldea;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WSailkapenHistoriala extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private ArrayList<Denboraldia> denboraldiHistoriala = new ArrayList<Denboraldia>();
	private ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
	private JTable table;
	private JComboBox<Object> comboBox;

	/**
	 * Create the panel.
	 */
	public WSailkapenHistoriala() {
		
		setBounds(100, 100, 650, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Denboraldi Historiala");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 25));
		lblNewLabel.setEnabled(true);
		lblNewLabel.setBounds(26, 11, 566, 42);
		add(lblNewLabel);
		
		table = new JTable();
		Object[] zutabeIzenak = {"Taldea", "Wins", "Defeats","Ties","Puntuak"};
		model = new DefaultTableModel(null, zutabeIzenak);
		table.setModel(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(26, 100, 566, 250);
		add(scrollPane);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taulaEguneratu();
			}
		});
		comboBox.setBounds(26, 74, 129, 21);
		add(comboBox);
		
		taulaEguneratu();
	}
	
	public void denboraldiaHistorialaGorde() {
		denboraldiHistoriala.clear();
		
		DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
		
		denboraldiHistoriala = denboraldiaDao.getHistorialDenboraldia();
		
		denboraldiaDao.deskonektatu();
		
		for(Denboraldia denboraldia : denboraldiHistoriala){
			String data = denboraldia.getHasierako_data().toString();
			comboBox.addItem(data);
		}
	}
	
	public void taulaEguneratu() {
		denboraldiaHistorialaGorde();
		
		String data = comboBox.getSelectedItem().toString();
		
		for(Denboraldia denboraldia : denboraldiHistoriala) {
			if(denboraldia.getHasierako_data().toString().equals(data)) {
				taldeakListGorde(denboraldia);
				taulaOrdenatu();
			}
		}
	}
	
	public void taulaOrdenatu() {
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
	
	public void taldeakListGorde(Denboraldia denboraldia) {
		taldeakList.clear();
		
		denb_taldeaDAO denb_taldeaDao = new denb_taldeaDAO();
		
		taldeakList = denb_taldeaDao.getJokatzenTaldeakList(denboraldia);
		
		denb_taldeaDao.deskonektatu();
	}
}
