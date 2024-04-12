package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class WTaldeak extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textIzena;
	private JTextField textHerria;
	private JTextField textZuzendaria;
	private DefaultTableModel dtmTaula;
	private JScrollPane scrollPane;
	private ArrayList<Taldea> taldeaList = new ArrayList<Taldea>();
	private JTable table;
	private Connection konexioa;
	private Vector<String> zutabeak;
	private Vector<Vector<String>> datuakTaula;

	/**
	 * Create the panel.
	 */
	public WTaldeak() {
		
		setLayout(null);
		setBounds(100, 100, 650, 450);
		
		JLabel lblNewLabel = new JLabel("TALDEAK KUDEATU");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setBounds(26, 11, 572, 22);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(26, 44, 572, 98);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Taldearen Izena");
		lblNewLabel_2.setBounds(40, 5, 126, 18);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
		
		textIzena = new JTextField();
		textIzena.setBounds(40, 22, 126, 29);
		panel.add(textIzena);
		textIzena.setFont(new Font("Arial", Font.PLAIN, 15));
		textIzena.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Herria");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_1.setBounds(220, 7, 98, 14);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 15));
		
		textHerria = new JTextField();
		textHerria.setBounds(220, 22, 126, 29);
		panel.add(textHerria);
		textHerria.setFont(new Font("Arial", Font.PLAIN, 15));
		textHerria.setColumns(10);
		
		textZuzendaria = new JTextField();
		textZuzendaria.setBounds(404, 22, 126, 29);
		panel.add(textZuzendaria);
		textZuzendaria.setFont(new Font("Arial", Font.PLAIN, 15));
		textZuzendaria.setColumns(10);
		
		JLabel lblNewLabel_2_2 = new JLabel("Zuzendaria");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_2.setBounds(404, 7, 104, 14);
		panel.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setFont(new Font("Arial", Font.BOLD, 15));
		
		scrollPane = new JScrollPane(table);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(26, 153, 572, 219);
		add(scrollPane);
		
		taldeTaulaEguneratu();
		
		JButton btnGorde = new JButton("Gorde");
		btnGorde.setBounds(168, 62, 98, 29);
		panel.add(btnGorde);
		btnGorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textIzena.getText().isEmpty() || textHerria.getText().isEmpty() || textZuzendaria.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Datu guztiak sartu behar duzu.","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					String Izena = textIzena.getText().toUpperCase();
					String Herria = textHerria.getText().toUpperCase();
					String Zuzendaria = textZuzendaria.getText().toUpperCase();
					//kjjkkjj
					Statement st = konektatu();
					try {
						ResultSet row = st.executeQuery("SELECT * FROM taldea WHERE talde_izena ='"+Izena+"'");
						if(row.next()) {
							JOptionPane.showMessageDialog(null, "Talde hau datu-basean erregistratu dago.", "Error", JOptionPane.ERROR_MESSAGE);
						}else {
							try {
								st.executeUpdate("INSERT INTO taldea(talde_izena, herria, zuzendaria) VALUES ('"+Izena+"', '"+Herria+"', '"+Zuzendaria+"')");
								textIzena.setText("");
								textHerria.setText("");
								textZuzendaria.setText("");
							}catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					deskonektatu();
				}
				taldeTaulaEguneratu();
			}
		});
		btnGorde.setFont(new Font("Arial", Font.BOLD, 13));
		
		JButton btnEzabatu = new JButton("Ezabatu");
		btnEzabatu.setBounds(304, 62, 98, 29);
		panel.add(btnEzabatu);
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnEzabatu.setFont(new Font("Arial", Font.BOLD, 13));
		
	}
	
	public void refreshTable() {
		
	}
	
	public Statement konektatu() {
		try {
			konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");
			Statement st = konexioa.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			return st;
		}catch(SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public void deskonektatu() {
		try {
			konexioa.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void taldeTaulaEguneratu() {
		Statement st = konektatu();
		try {
			ResultSet rows = st.executeQuery("SELECT * FROM taldea");
			zutabeak = new Vector<String>();
			ResultSetMetaData metaDatuak = rows.getMetaData();
			int zutabeKopurua = metaDatuak.getColumnCount();
			//Zutabeko izenak jartzeko
			for (int i = 1; i < zutabeKopurua; i++){
				zutabeak.add(metaDatuak.getColumnLabel(i + 1));
			}
			datuakTaula = new Vector<Vector<String>>();
			while (rows.next()) {
				Vector<String> row = new Vector<String>();
				row.add(rows.getString("talde_izena"));
				row.add(rows.getString("herria"));
				row.add(rows.getString("zuzendaria"));
				datuakTaula.add(row);
			}
			deskonektatu();
			dtmTaula = new DefaultTableModel(datuakTaula, zutabeak);
			table = new JTable(dtmTaula);
			table.setBounds(23, 10, 677, 286);
			scrollPane.setViewportView(table);
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void taldeakArrayListGorde() {
		
	}
}
