package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

public class WJokalariak extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textIzena;
	private JComboBox cmboxPosizioa;
	private JComboBox cmboxTaldea;
	private JSpinner spinner;
	DefaultTableModel model;
	//Hemen Jokalri guztien LISTA bat gorde egingo da
	//ArrayList<Jokalaria> jokalariList = new ArrayList<Jokalaria>();
	private JTable table;
	//private ArrayList<Taldea> taldeList;
	

	/**
	 * Create the panel.
	 */
	public WJokalariak() {
		setBounds(100, 100, 650, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("JOKALARIAK KUDEATU");
		lblNewLabel.setBounds(20, 11, 591, 22);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 44, 591, 103);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Izena");
		lblNewLabel_1.setBounds(36, 12, 60, 14);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
		
		textIzena = new JTextField();
		textIzena.setBounds(36, 27, 118, 27);
		panel.add(textIzena);
		textIzena.setFont(new Font("Arial", Font.PLAIN, 15));
		textIzena.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Posizioa");
		lblNewLabel_1_1.setBounds(263, 12, 84, 14);
		panel.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 15));
		
		cmboxPosizioa = new JComboBox();
		cmboxPosizioa.setBackground(new Color(255, 255, 255));
		cmboxPosizioa.setBounds(262, 27, 118, 27);
		panel.add(cmboxPosizioa);
		cmboxPosizioa.setModel(new DefaultComboBoxModel(new String[] {"Pilier", "Talonador", "Second Row", "Octavo", "Flanker", "Medio Mele", "Apertura", "Centros", "Alas", "Zaguero"}));
		cmboxPosizioa.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Taldea");
		lblNewLabel_1_1_1.setBounds(413, 12, 65, 14);
		panel.add(lblNewLabel_1_1_1);
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.BOLD, 15));
		
		cmboxTaldea = new JComboBox();
		cmboxTaldea.setBackground(new Color(255, 255, 255));
		cmboxTaldea.setBounds(415, 27, 147, 27);
		
		panel.add(cmboxTaldea);
		cmboxTaldea.setFont(new Font("Arial", Font.PLAIN, 15));
		
		spinner = new JSpinner();
		spinner.setBounds(179, 27, 60, 27);
		panel.add(spinner);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Dorsala");
		lblNewLabel_1_1_1_1.setBounds(179, 12, 60, 14);
		panel.add(lblNewLabel_1_1_1_1);
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.BOLD, 15));
		
		table = new JTable();
		Object[] zutabeIzenak = {"Izena", "Dorsala", "Posizioa","Taldea"};
		model = new DefaultTableModel(null, zutabeIzenak);
		//Taulari default model list ipini
		table.setModel(model);
		//Funtzio honei deitzen zaie aplikazioa biaraztean jokalariak.dat berrezkuratzeko eta taulan jartzeko.
		
		
		table.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(20, 158, 591, 217);
		add(scrollPane);
		
		JButton btnGorde = new JButton("Gorde");
		btnGorde.setBounds(179, 64, 96, 27);
		panel.add(btnGorde);
		btnGorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnGorde.setFont(new Font("Arial", Font.BOLD, 13));
		
		JButton btnEzabatu = new JButton("Ezebatu");
		btnEzabatu.setBounds(315, 64, 89, 27);
		panel.add(btnEzabatu);
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnEzabatu.setFont(new Font("Arial", Font.BOLD, 13));
		
		// Crear un TableCellRenderer para centrar el contenido
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Aplicar el renderer a todas las columnas
		for (int i = 0; i < table.getColumnCount(); i++) {
		    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
	}
	/**
	 * refreshTable FUNTZIOA
	 * Funztioa hau balioko du Taulan datuak sartzeko, funztio hau deitzean arraylist dauden datuak sartuko ditu taulan.
	 */
	public void refreshTable() {
		
	}
}
