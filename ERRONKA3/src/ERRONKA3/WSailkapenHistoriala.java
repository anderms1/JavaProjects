package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WSailkapenHistoriala extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	//private ArrayList<Denboraldia> denboraldiHistoriala = new ArrayList<Denboraldia>();
	//private ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
	private JTable table;

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
		
	}

}
