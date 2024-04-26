package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ERRONKA3.Denboraldia;
import ERRONKA3.Jardunaldia;
import ERRONKA3.Partidua;
import ERRONKA3.Taldea;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

public class WDenboraldiak extends JPanel {

	private static final long serialVersionUID = 1L;
	private Connection konexioa;
	private JLabel lblJokatzen;
	private JLabel lblSortuta;
	private JTable table;
	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private DefaultTableModel dtmTaula;
	private ArrayList<Denboraldia> denboraldiHistorialaList = new ArrayList<Denboraldia>();
	private ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
	/**
	 * Create the panel.
	 */
	public WDenboraldiak() {
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		centerRenderer.setBorder(new EmptyBorder(0, 0, 0, 0));
		setBounds(100, 100, 650, 450);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DENBORALDIAK");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setBounds(24, 10, 565, 30);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(24, 37, 565, 99);
		add(panel);
		panel.setLayout(null);
		
		JButton btnDBerria = new JButton("Denboraldi Berria Hasi");
		btnDBerria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(taldeakList.size() >= 6) {
						Statement st = konektatu();
						ResultSet totalRow = st.executeQuery("SELECT COUNT(*) AS total_row FROM denboraldia WHERE amaierako_data is NULL");
						if (totalRow.next() && totalRow.getInt("total_row") > 0) {
	                    	JOptionPane.showMessageDialog(null, "Denboraldi bat jokatzen ari da.","Error",JOptionPane.ERROR_MESSAGE);
	                    }else {
	                    	try {
	                    		LocalDate data = LocalDate.now();
	                    		ResultSet rs = st.executeQuery("SELECT COUNT(*) AS count FROM denboraldia WHERE YEAR(hasierako_data) = " + data.getYear());
	                            if (rs.next() && rs.getInt("count") > 0) {
	                                // Si hay una denboraldia en el año actual, suma un año a la fecha
	                                data = data.plusYears(1);
	                            }
								st.executeUpdate("INSERT INTO denboraldia(hasierako_data) VALUES ('"+data+"')");
								lblSortuta.setVisible(true);
								
								JardunaldiakGeneratu(denboraldiaSortu(st));
								deskonektatu();
								TaulaOsatu();
								
							}catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                    }
					}
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea Denboraldia hasieratzean.","Error",JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnDBerria.setFont(new Font("Arial Black", Font.PLAIN, 10));
		btnDBerria.setBounds(69, 27, 169, 36);
		panel.add(btnDBerria);
		
		JButton btnDAmaitu = new JButton("Denboraldia Amaitu");
		btnDAmaitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnDAmaitu.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnDAmaitu.setBounds(318, 26, 169, 36);
		panel.add(btnDAmaitu);
		
		lblJokatzen = new JLabel("Denboraldia jokatzen!");
		lblJokatzen.setBackground(new Color(0, 128, 0));
		lblJokatzen.setForeground(new Color(0, 128, 64));
		lblJokatzen.setHorizontalAlignment(SwingConstants.CENTER);
		lblJokatzen.setFont(new Font("Arial", Font.BOLD, 14));
		lblJokatzen.setBounds(0, 80, 565, 13);
		lblJokatzen.setVisible(false);
		panel.add(lblJokatzen);
		
		lblSortuta = new JLabel("Denboraldia sortuta!");
		lblSortuta.setForeground(new Color(0, 128, 0));
		lblSortuta.setHorizontalAlignment(SwingConstants.CENTER);
		lblSortuta.setFont(new Font("Arial", Font.BOLD, 14));
		lblSortuta.setBounds(0, 81, 565, 13);
		lblSortuta.setVisible(false);
		panel.add(lblSortuta);
		
		table = new JTable();
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		Object[] zutabeIzenak = {"Nº", "Hasiera Data", "Amaiera Data"};
		dtmTaula = new DefaultTableModel(null,zutabeIzenak) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//Taulari default model list ipini
		table.setModel(dtmTaula);
		table.setShowVerticalLines(false);
		table.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(92, 175, 418, 200);
		add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("Denboraldi Historiala");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(92, 146, 418, 27);
		add(lblNewLabel_1);
		
		TaulaOsatu();
		comprobarEstadoDenboraldia();
		taldeakArrayListGorde();
	}
	
public void JardunaldiakGeneratu(Denboraldia denboraldia) {
		
		int numEquipos = taldeakList.size();
        int mitadEquipos = numEquipos / 2;
        
        Collections.shuffle(taldeakList);
        for (int jardunaldia = 1; jardunaldia < numEquipos; jardunaldia++) {
        	Jardunaldia jardunaldi = new Jardunaldia();
        	
        	for (int j = 0; j < mitadEquipos; j++) {
        		Partidua partidu = new Partidua();
                int etxekoTaldea = j;
                int bisitariTaldea = numEquipos - 1 - j;
              
                if (j % 2 == 0) {
                    int temp = etxekoTaldea;
                    etxekoTaldea = bisitariTaldea;
                    bisitariTaldea = temp;
                }
                Taldea etxekoTaldeaIzena = taldeakList.get(etxekoTaldea);
	            Taldea bisitariTaldeaIzena = taldeakList.get(bisitariTaldea);
                partidu.setEtxeko_talde(etxekoTaldeaIzena);
                partidu.setKanpoko_talde(bisitariTaldeaIzena);
                jardunaldi.gehituPartidua(partidu);
        	}
        	denboraldia.gehituJardunaldiak(jardunaldi);
        	Collections.rotate(taldeakList.subList(1, numEquipos), 1);
        }
        
        for (int jardunaldia = 1; jardunaldia < numEquipos; jardunaldia++) {
        	Jardunaldia jardunaldi = new Jardunaldia();
        	
        	for (int j = 0; j < mitadEquipos; j++) {
        		Partidua partidu = new Partidua();
                int etxekoTaldea = j;
                int bisitariTaldea = numEquipos - 1 - j;
              
                if (j % 2 == 0) {
                    int temp = etxekoTaldea;
                    etxekoTaldea = bisitariTaldea;
                    bisitariTaldea = temp;
                }
                
                Taldea etxekoTaldeaIzena = taldeakList.get(bisitariTaldea);
	            Taldea bisitariTaldeaIzena = taldeakList.get(etxekoTaldea);
	            
                partidu.setEtxeko_talde(etxekoTaldeaIzena);
                partidu.setKanpoko_talde(bisitariTaldeaIzena);
                jardunaldi.gehituPartidua(partidu);
        	}
        	denboraldia.gehituJardunaldiak(jardunaldi);
        	Collections.rotate(taldeakList.subList(1, numEquipos), 1);
        }
	}
	
	public void DenboraldiHistorialaArrayListGorde() {
		denboraldiHistorialaList.clear();
		Statement st = konektatu();
		try {
			ResultSet datuak = st.executeQuery("SELECT * FROM denboraldia");
			while (datuak.next()) {
				Denboraldia denboraldia = new Denboraldia();
				denboraldia.setDenboraldia_kod(datuak.getInt("denboraldia_kod"));
				denboraldia.setHasierako_data(datuak.getDate("hasierako_data"));
				denboraldia.setAmierako_data(datuak.getDate("amaierako_data"));
	            denboraldiHistorialaList.add(denboraldia);
		   }
			deskonektatu();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public void TaulaOsatu() {
		DenboraldiHistorialaArrayListGorde();
		dtmTaula.setRowCount(0);
		table.setModel(dtmTaula);
		for(Denboraldia denboraldia : denboraldiHistorialaList) {
			Object[] rowData = {denboraldia.getDenboraldia_kod()+".Denboraldia", denboraldia.getHasierako_data(), denboraldia.getAmierako_data()};
			dtmTaula.addRow(rowData);
		}
		for(int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		for(int i = 0; i < table.getRowCount(); i++) {
			Object value = table.getValueAt(i,2);
			if(value == null || value.toString().trim().isEmpty()) {
				dtmTaula.setValueAt("Jokatzen", i, 2);
			}
		}	
	}
	public void comprobarEstadoDenboraldia() {
		Statement st = konektatu();
		ResultSet totalRow;
		try {
			totalRow = st.executeQuery("SELECT COUNT(*) AS total_row FROM denboraldia WHERE amaierako_data is NULL");
			if (totalRow.next() && totalRow.getInt("total_row") > 0) {
	        	lblJokatzen.setVisible(true);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void taldeakArrayListGorde() {
		taldeakList.clear();
		
		Statement st = konektatu();
		try {
			ResultSet datuak = st.executeQuery("SELECT * FROM taldea");
			while (datuak.next()) {
				Taldea talde = new Taldea();
				talde.setTalde_kod(datuak.getInt("talde_kod"));
	            talde.setTalde_izena(datuak.getString("talde_izena"));
	            talde.setHerria(datuak.getString("herria"));
	            talde.setZuzendaria(datuak.getString("zuzendaria"));
	            taldeakList.add(talde);
		   }
			deskonektatu();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Denboraldia denboraldiaSortu(Statement st) {
		
		ResultSet denboraldiadb = st.executeQuery("SELECT * FROM denboraldia WHERE amaierako_data is NULL");
		Denboraldia denboraldia = new Denboraldia();
		
		while(denboraldiadb.next()) {
			denboraldia.setDenboraldia_kod(denboraldiadb.getInt("denboraldia_kod"));
		}
		return denboraldia;
	}
}
