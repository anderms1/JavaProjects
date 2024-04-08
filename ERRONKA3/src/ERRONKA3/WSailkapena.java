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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WSailkapena extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	//private ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
	//private ArrayList<Denboraldia> denboraldiaList = new ArrayList<Denboraldia>();
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
	}
	
}
