package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import ERRONKA3.Taldea;
import ERRONKA3.TaldeaDAO;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
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
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2_2.setBounds(404, 7, 104, 14);
		panel.add(lblNewLabel_2_2);
		lblNewLabel_2_2.setFont(new Font("Arial", Font.BOLD, 15));
		
		table = new JTable();
		Object[] zutabeIzenak = {"Taldea", "Herria", "Zuzendaria"};
		
		dtmTaula = new DefaultTableModel(null, zutabeIzenak) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//Taulari default model list ipini
		table.setModel(dtmTaula);
		
		scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[]Indice = table.getSelectedRows();
				String izena = null;
				String zuzendaria = null;
				String herria = null;
				for (int i = Indice.length - 1; i >= 0; i--) {
                    izena = (String) table.getValueAt(Indice[i], 0);
                    herria = (String) table.getValueAt(Indice[i], 1);
                    zuzendaria = (String) table.getValueAt(Indice[i], 2);
                }
				textIzena.setText(izena);
				textHerria.setText(herria);
                textZuzendaria.setText(zuzendaria);
			}
		});
		scrollPane.setBounds(26, 153, 572, 219);
		add(scrollPane);
		
		taldeTaulaEguneratu();
		
		JButton btnGorde = new JButton("Gorde");
		btnGorde.setBounds(91, 61, 104, 29);
		panel.add(btnGorde);
		btnGorde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaldeaDAO taldeDao = new TaldeaDAO();
				if (textIzena.getText().isEmpty() || textHerria.getText().isEmpty() || textZuzendaria.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"Datu guztiak sartu behar duzu.","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					String Izena = textIzena.getText().toUpperCase();
					String Herria = textHerria.getText().toUpperCase();
					String Zuzendaria = textZuzendaria.getText().toUpperCase();
					
					boolean DBgaldetu = taldeDao.TaldeDBGaldetu(Izena);
					
						
					if(DBgaldetu == true) {
						JOptionPane.showMessageDialog(null, "Talde hau datu-basean erregistratu dago.", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						Taldea taldea = new Taldea();
						taldea.setTalde_izena(Izena);
						taldea.setHerria(Herria);
						taldea.setZuzendaria(Zuzendaria);
						taldeDao.insertTaldea(taldea);
						textIzena.setText("");
						textHerria.setText("");
						textZuzendaria.setText("");
					}
					taldeDao.deskonektatu();
				}
				taldeTaulaEguneratu();
			}
		});
		btnGorde.setFont(new Font("Arial", Font.BOLD, 13));
		
		JButton btnEzabatu = new JButton("Ezabatu");
		btnEzabatu.setBounds(230, 61, 104, 29);
		panel.add(btnEzabatu);
		btnEzabatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TaldeaDAO taldeDao = new TaldeaDAO();
					int[]Indice = table.getSelectedRows();
					if (Indice.length == 0) {
						JOptionPane.showMessageDialog(null, "Talde bat ezabatzeko taulan hautatu behar duzu.","Error",JOptionPane.ERROR_MESSAGE);
					}else {
						int confirm = JOptionPane.showConfirmDialog(null, "Ziur hautatutako erregistroa(k) ezabatu nahi dituzula?", "Berretsi Ezabaketa", JOptionPane.YES_NO_OPTION);
						 if (confirm == JOptionPane.YES_OPTION) {
			                    for (int i = Indice.length - 1; i >= 0; i--) {
			                        String izena = (String) table.getValueAt(Indice[i], 0);
			                        taldeDao.deleteTaldea(izena);
			                    }
				            }
					}
					taldeDao.deskonektatu();
					taldeTaulaEguneratu();
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
					if (textIzena.getText().isEmpty() || textZuzendaria.getText().isEmpty() || textHerria.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Datu guztiak sartu behar dituzu.","Error",JOptionPane.ERROR_MESSAGE);
					}else {
		                String izena = textIzena.getText().toUpperCase();
						String zuzendaria = textZuzendaria.getText().toUpperCase();
						String herria = textHerria.getText().toUpperCase();
						TaldeaDAO taldeDao = new TaldeaDAO();
						
						boolean DBgaldetu = taldeDao.TaldeDBGaldetu(izena);
						if (DBgaldetu == true) {
							JOptionPane.showMessageDialog(null, "Ez dago talderik izen honekin.","Error",JOptionPane.ERROR_MESSAGE);
						}else {
							Taldea taldea = new Taldea();
							taldea.setTalde_izena(izena);
							taldea.setHerria(herria);
							taldea.setZuzendaria(zuzendaria);
							taldeDao.updateTaldea(taldea);
							textIzena.setText("");
							textHerria.setText("");
							textZuzendaria.setText("");
						}
				       taldeDao.deskonektatu();   
					}
					taldeTaulaEguneratu();
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea Taldea berriztatzean.","Error",JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		btnBerriztatu.setFont(new Font("Arial", Font.BOLD, 13));
		btnBerriztatu.setBounds(366, 61, 104, 29);
		panel.add(btnBerriztatu);
		
	}
	
	public void taldeTaulaEguneratu() {
		taldeakArrayListGorde();
		table.setModel(dtmTaula);
		dtmTaula.setRowCount(0);
		for(Taldea taldea : taldeaList) {
			Object[] rowData = {taldea.getTalde_izena(), taldea.getHerria(), taldea.getZuzendaria()};
			dtmTaula.addRow(rowData);
		}
		
	}
	public void taldeakArrayListGorde() {
		taldeaList.clear();
		
		TaldeaDAO taldeDao = new TaldeaDAO();
		
		taldeaList = taldeDao.getAllTaldeak();
		
		taldeDao.deskonektatu();
	}
}
