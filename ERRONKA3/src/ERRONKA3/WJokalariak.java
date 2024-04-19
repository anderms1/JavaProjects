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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

public class WJokalariak extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textIzena;
	private JComboBox cmboxPosizioa;
	private JComboBox cmboxTaldea;
	private JSpinner spinner;
	private DefaultTableModel dtmTaula;
	//Hemen Jokalri guztien LISTA bat gorde egingo da
	private ArrayList<Jokalaria> jokalariakList = new ArrayList<Jokalaria>();
	private JTable table;
	private ArrayList<Taldea> taldeaList = new ArrayList<Taldea>();
	private Connection konexioa;
	private JCheckBox chckbx;
	private JComboBox cmboxFiltrar;
	

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
		cmboxPosizioa.setModel(new DefaultComboBoxModel(new String[] {"Pilier", "Talonador", "Second Row", "Octavo", "Flanker", "Medio Mele", "Apertura", "Centro", "Ala", "Zaguero"}));
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
		dtmTaula = new DefaultTableModel(null, zutabeIzenak) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//Taulari default model list ipini
		table.setModel(dtmTaula);
		//Funtzio honei deitzen zaie aplikazioa biaraztean jokalariak.dat berrezkuratzeko eta taulan jartzeko.
		
		
		table.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[]Indice = table.getSelectedRows();
				String izena = null;
				int dorsala = 0;
				String posizioa = null;
				String taldea = null;
				for (int i = Indice.length - 1; i >= 0; i--) {
                    izena = (String) table.getValueAt(Indice[i], 0);
                    dorsala = (int) table.getValueAt(Indice[i], 1);
                    posizioa = (String) table.getValueAt(Indice[i], 2);
                    taldea = (String) table.getValueAt(Indice[i], 3);
                }
				textIzena.setText(izena);
				spinner.setValue(dorsala);
                cmboxPosizioa.setSelectedIndex(seleccionarCBPosizioa(posizioa));
                cmboxTaldea.setSelectedIndex(seleccionarCBTaldea(taldea));
			}
		});
		scrollPane.setEnabled(false);
		scrollPane.setBounds(20, 175, 591, 200);
		add(scrollPane);
		
		JButton btnGorde = new JButton("Gorde");
		btnGorde.setBounds(124, 64, 105, 27);
		panel.add(btnGorde);
		btnGorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dorsala = (int) spinner.getValue();
				if (textIzena.getText().isEmpty() || dorsala == 0) {
					JOptionPane.showMessageDialog(null,"Datu guztiak sartu behar duzu.","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					String izena = textIzena.getText().toUpperCase();
					String posizioa = cmboxPosizioa.getSelectedItem().toString().toUpperCase();
					Taldea hautatutakoTaldea = IzenarekinTaldeaLortu(cmboxTaldea.getSelectedItem().toString());
					int talde_kod = hautatutakoTaldea.getTalde_kod();
					
					Statement st = konektatu();
					try {
						ResultSet row = st.executeQuery("SELECT * FROM jokalaria WHERE izena ='"+izena+"'");
						if(row.next()) {
							JOptionPane.showMessageDialog(null, "Jokalari hau datu-basean erregistratu dago.", "Error", JOptionPane.ERROR_MESSAGE);
						}else {
							try {
								st.executeUpdate("INSERT INTO jokalaria(izena, dorsala, posizioa, talde_kod) VALUES ('"+izena+"', '"+dorsala+"', '"+posizioa+"', '"+talde_kod+"')");
								textIzena.setText("");
								spinner.setValue(0);
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
				filtrarTabla();
			}
		});
		btnGorde.setFont(new Font("Arial", Font.BOLD, 13));
		
		JButton btnEzabatu = new JButton("Ezebatu");
		btnEzabatu.setBounds(251, 64, 96, 27);
		panel.add(btnEzabatu);
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int[]Indice = table.getSelectedRows();
					if (Indice.length == 0) {
						JOptionPane.showMessageDialog(null, "Jokalari bat ezabatzeko taulan hautatu behar duzu.","Error",JOptionPane.ERROR_MESSAGE);
					}else {
						int confirm = JOptionPane.showConfirmDialog(null, "Ziur hautatutako erregistroa(k) ezabatu nahi dituzula?", "Berretsi Ezabaketa", JOptionPane.YES_NO_OPTION);
						 if (confirm == JOptionPane.YES_OPTION) {
				                try {
				                    Statement st = konektatu();
				                    for (int i = Indice.length - 1; i >= 0; i--) {
				                        String izena = (String) table.getValueAt(Indice[i], 0);
				                        st.executeUpdate("DELETE FROM jokalaria WHERE izena = '" + izena + "'");
				                    }
				                    deskonektatu();
		
				                } catch (SQLException ex) {
				                    JOptionPane.showMessageDialog(null, "Errorea Taldea ezabatzean.", "Error", JOptionPane.ERROR_MESSAGE);
				                    ex.printStackTrace();
				                }
				            }
					}
					filtrarTabla();
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea Taldea ezabatzean.","Error",JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnEzabatu.setFont(new Font("Arial", Font.BOLD, 13));
		
		JButton btnBerriztatu = new JButton("Berriztatu");
		btnBerriztatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int dorsala = (int) spinner.getValue();
					if (textIzena.getText().isEmpty() || dorsala == 0) {
						JOptionPane.showMessageDialog(null, "Datu guztiak sartu behar dituzu.","Error",JOptionPane.ERROR_MESSAGE);
					}else {
		                try {
		                	String izena = textIzena.getText().toUpperCase();
							String posizioa = cmboxPosizioa.getSelectedItem().toString().toUpperCase();
							Taldea hautatutakoTaldea = IzenarekinTaldeaLortu(cmboxTaldea.getSelectedItem().toString());
							int talde_kod = hautatutakoTaldea.getTalde_kod();
							
		                    Statement st = konektatu();
		                    ResultSet totalRow = st.executeQuery("SELECT COUNT(*) AS total_row FROM jokalaria WHERE izena = '"+izena+"'");
		                    if (totalRow.next() && totalRow.getInt("total_row") == 0) {
		                    	JOptionPane.showMessageDialog(null, "Ez dago talderik izen honekin.","Error",JOptionPane.ERROR_MESSAGE);
		                    }else {
		                    	st.executeUpdate("UPDATE jokalaria SET izena = '"+izena+"', dorsala = '"+dorsala+"', posizioa = '"+posizioa+"', talde_kod = '"+talde_kod+"' WHERE izena = '"+izena+"'");
		                    	textIzena.setText("");
								spinner.setValue(0);
		                    }
		                    deskonektatu();
	
		                } catch (SQLException ex) {
		                    JOptionPane.showMessageDialog(null, "Errorea Jokalaria berriztatzean.", "Error", JOptionPane.ERROR_MESSAGE);
		                    ex.printStackTrace();
		                }
				          
					}
					filtrarTabla();
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea Jokalaria berriztatzean.","Error",JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnBerriztatu.setFont(new Font("Arial", Font.BOLD, 13));
		btnBerriztatu.setBounds(368, 64, 96, 27);
		panel.add(btnBerriztatu);
		
		chckbx = new JCheckBox("All");
		chckbx.setSelected(true);
		chckbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarTabla();
			}
		});
		chckbx.setFont(new Font("Arial", Font.PLAIN, 11));
		chckbx.setBounds(411, 151, 42, 21);
		add(chckbx);
		
		cmboxFiltrar = new JComboBox();
		cmboxFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarTabla();
			}
		});
		cmboxFiltrar.setBounds(459, 150, 152, 22);
		add(cmboxFiltrar);
		
		// Crear un TableCellRenderer para centrar el contenido
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Aplicar el renderer a todas las columnas
		for (int i = 0; i < table.getColumnCount(); i++) {
		    table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		taldeakArrayListGorde();
		filtrarTabla();
	}
	
	
	public void jokalariakArrayListGorde() {
		jokalariakList.clear();
		Statement st = konektatu();
		try {
			ResultSet datuak = st.executeQuery("SELECT * FROM jokalaria JOIN taldea ON taldea.talde_kod = jokalaria.talde_kod");
			while (datuak.next()) {
				Jokalaria jokalari = new Jokalaria();
				jokalari.setJokalaria_kod(datuak.getInt("jokalaria_kod"));
	            jokalari.setIzena(datuak.getString("izena"));
	            jokalari.setDorsala(datuak.getInt("dorsala"));
	            jokalari.setPosizioa(datuak.getString("posizioa"));
	            for(Taldea talde : taldeaList) {
	            	if(talde.getTalde_izena().equals(datuak.getString("talde_izena"))) {
	            		jokalari.setTaldea(talde);
	            	}
	            }
	            jokalariakList.add(jokalari);
			}
			deskonektatu();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void taldeakArrayListGorde() {
		taldeaList.clear();
		Statement st = konektatu();
		try {
			ResultSet datuak = st.executeQuery("SELECT * FROM taldea");
			while (datuak.next()) {
				Taldea talde = new Taldea();
				talde.setTalde_kod(datuak.getInt("talde_kod"));
	            talde.setTalde_izena(datuak.getString("talde_izena"));
	            talde.setHerria(datuak.getString("herria"));
	            talde.setZuzendaria(datuak.getString("zuzendaria"));
	            taldeaList.add(talde);
		   }
			deskonektatu();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Taldea taldea : taldeaList) {
			cmboxTaldea.addItem(taldea.getTalde_izena());
			cmboxFiltrar.addItem(taldea.getTalde_izena());
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
	
	public Taldea IzenarekinTaldeaLortu(String izena) {
		for(Taldea taldea : taldeaList) {
			if (taldea.getTalde_izena().equals(izena)) {
				return taldea;
			}
		}
		return null;
	}
	
	public int seleccionarCBPosizioa(String posizioa) {
		int total = cmboxPosizioa.getItemCount();
		for(int i = 0; i < total; i++) {
			String cmboxIzena = cmboxPosizioa.getItemAt(i).toString();
			if(posizioa.equals(cmboxIzena)) {
				return i;
			}
		}
		return 20;
	}
	public int seleccionarCBTaldea(String taldea) {
		int total = cmboxTaldea.getItemCount();
		for(int i = 0; i < total; i++) {
			String cmboxIzena = cmboxTaldea.getItemAt(i).toString();
			if(taldea.equals(cmboxIzena)) {
				return i;
			}
		}
		return 20;
	}
	
	public void filtrarTabla() {
		jokalariakArrayListGorde();
		boolean check = chckbx.isSelected();
		if(check == true) {
			table.setModel(dtmTaula);
			dtmTaula.setRowCount(0);
			for(Jokalaria jokalari : jokalariakList) {
				Object[] rowData = {jokalari.getIzena(), jokalari.getDorsala(), jokalari.getPosizioa(), jokalari.getTaldea().getTalde_izena()};
				dtmTaula.addRow(rowData);
			}
		}else {
			dtmTaula.setRowCount(0);
			table.setModel(dtmTaula);
			String izena = cmboxFiltrar.getSelectedItem().toString();	
			for(Jokalaria jokalari : jokalariakList) {
				Taldea taldea = jokalari.getTaldea();
				if(izena.equals(taldea.getTalde_izena())){
					Object[] rowData = {jokalari.getIzena(), jokalari.getDorsala(), jokalari.getPosizioa(), jokalari.getTaldea().getTalde_izena()};
					dtmTaula.addRow(rowData);
				}
			}
		}
	}
}
