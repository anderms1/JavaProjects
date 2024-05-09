package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ERRONKA3.DAO.DenboraldiaDAO;
import ERRONKA3.DAO.TaldeaDAO;
import ERRONKA3.klaseak.Denboraldia;
import ERRONKA3.klaseak.FTPUploader;
import ERRONKA3.klaseak.Jardunaldia;
import ERRONKA3.klaseak.Jokalaria;
import ERRONKA3.klaseak.Partidua;
import ERRONKA3.klaseak.Taldea;

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
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

public class WDenboraldiak extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblJokatzen;
	private JLabel lblSortuta;
	private JTable table;
	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	private DefaultTableModel dtmTaula;
	private ArrayList<Denboraldia> denboraldiHistoriala = new ArrayList<Denboraldia>();
	private ArrayList<Denboraldia> jokatzendenboraldi = new ArrayList<Denboraldia>();
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
					if(taldeakList.size() == 6) {
						DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
						boolean jokatzen = denboraldiaDao.DenboraldiaDBGaldetu();
						if (jokatzen == true) {
	                    	JOptionPane.showMessageDialog(null, "Denboraldi bat jokatzen ari da.","Error",JOptionPane.ERROR_MESSAGE);
	                    }else {
                    		
                    		Date data = new Date(System.currentTimeMillis());
                    		Calendar calendar = Calendar.getInstance();
                            calendar.setTime(data);
                            int year = calendar.get(Calendar.YEAR);
                    		boolean exist = denboraldiaDao.DenboraldiDataGaldetu(year);
                            if (exist == true) {
                                // Si hay una denboraldia en el año actual, suma un año a la fecha
                                 calendar.add(Calendar.YEAR, 1);
                                 data = new Date(calendar.getTimeInMillis());
                            }
							Denboraldia denboraldia = new Denboraldia();
							java.sql.Date sqlDate = new java.sql.Date(data.getTime());
							denboraldia.setHasierako_data(sqlDate);
							denboraldiaDao.insertDenboraldia(denboraldia);
							denboraldiaDao.deskonektatu();
							jokatzenDenboraldiaArrayListGorde();
							denb_taldeGeneratu(jokatzendenboraldi.get(0));
							JardunaldiakGeneratu(jokatzendenboraldi.get(0));
							
							lblSortuta.setVisible(true);
							TaulaOsatu();
	                    }
					}else {
						JOptionPane.showMessageDialog(null, "Talde guztiak sartu behar dituzu.","Error",JOptionPane.ERROR_MESSAGE);
					}
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea Denboraldia hasieratzean.","Error",JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
			}
		});
		btnDBerria.setFont(new Font("Arial Black", Font.PLAIN, 10));
		btnDBerria.setBounds(69, 27, 169, 36);
		panel.add(btnDBerria);
		
		JButton btnDAmaitu = new JButton("Denboraldia Amaitu");
		btnDAmaitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					jokatzenDenboraldiaArrayListGorde();
					DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
					boolean partiduakAmaituta = denboraldiaDao.partiduakAmaituGaldetu(jokatzendenboraldi.get(0));
					if(partiduakAmaituta == true) {
						JOptionPane.showMessageDialog(null, "Denboraldia amaitzeko partidu guztiak jokatu behar dira.","Error",JOptionPane.ERROR_MESSAGE);
					}else {
						
						java.sql.Date sqlDate = denboraldiaDao.azkenDataLortu(jokatzendenboraldi.get(0));
						jokatzendenboraldi.get(0).setAmaierako_data(sqlDate);
						denboraldiaDao.updateDenboraldia(jokatzendenboraldi.get(0));
					}
					denboraldiaDao.deskonektatu();
				} catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea Denboraldia amaitzean.","Error",JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
				TaulaOsatu();
				comprobarEstadoDenboraldia();
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
		scrollPane.setBounds(92, 175, 418, 179);
		add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("Denboraldi Historiala");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(92, 146, 418, 27);
		add(lblNewLabel_1);
		
		JButton btnXMLGeneratu = new JButton("XML Sortu");
		btnXMLGeneratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DenboraldiHistorialaArrayListGorde();
					DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
					ArrayList<Jokalaria> jokalariak = new ArrayList<Jokalaria>();
					jokalariak = denboraldiaDao.getAllJokalariak();
					for(Taldea taldea : taldeakList) {
						for(Jokalaria jokalaria : jokalariak) {
							if(jokalaria.getTaldea().getTalde_izena().equals(taldea.getTalde_izena())) {
								taldea.gehituJokalaria(jokalaria);
							}
						}
					}	
					denboraldiaDao.deskonektatu();
					
					XML xml = new XML();
					
					xml.denboraldiaXMLGeneratu(denboraldiHistoriala,taldeakList);
					JOptionPane.showMessageDialog(null, "XML-a ongi sortuta.","Information",JOptionPane.INFORMATION_MESSAGE);
					/*int confirm = JOptionPane.showConfirmDialog(null,"Nahi duzu igo xml-a?","Konfimazioa",JOptionPane.YES_NO_OPTION);
					if(confirm == JOptionPane.YES_OPTION) {
						FTPUploader ftpuploader = new FTPUploader();

			            ftpuploader.uploadFile("federazioa.xml", "/var/www/RUGBY3/federazioa.xml");
					}*/
		            

				}catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Errorea xml sortzen.","Error",JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        }
			}
		});
		btnXMLGeneratu.setFont(new Font("Arial", Font.PLAIN, 11));
		btnXMLGeneratu.setBounds(254, 356, 95, 21);
		add(btnXMLGeneratu);
		
		TaulaOsatu();
		comprobarEstadoDenboraldia();
		taldeakArrayListGorde();
	}
	
	public void denb_taldeGeneratu(Denboraldia denboraldia) {
		DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
		
		for(Taldea taldea : taldeakList) {
			denboraldiaDao.insertDenb_Taldea(denboraldia, taldea);
		}
		denboraldiaDao.deskonektatu();
	}
	
	public void JardunaldiakGeneratu(Denboraldia denboraldia) {
		
		int numEquipos = taldeakList.size();
        int mitadEquipos = numEquipos / 2;
        
        Collections.shuffle(taldeakList);
        DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
        Date jarData = denboraldia.getHasierako_data();
        java.sql.Date sqldata = new java.sql.Date(jarData.getTime());
        for (int jardunaldia = 1; jardunaldia < numEquipos; jardunaldia++) {
        	Jardunaldia jardunaldi = new Jardunaldia();
        	jardunaldi.setDenboraldia_kod(denboraldia);
        	jardunaldi.setHasierako_data(sqldata);
        	Calendar calendar = Calendar.getInstance();
            calendar.setTime(sqldata);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            sqldata = new java.sql.Date(calendar.getTimeInMillis());
            jardunaldi.setAmaierako_data(sqldata);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            sqldata = new java.sql.Date(calendar.getTimeInMillis());
            denboraldiaDao.insertJardunaldia(jardunaldi);
            jardunaldi.setJardunaldia_kod(denboraldiaDao.JardunaldiaKodLortu(jardunaldi.getHasierako_data()));
        	
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
                partidu.setDenboraldia(denboraldia);
                partidu.setJardunaldia(jardunaldi);
                denboraldiaDao.insertPartidua(partidu);
        	}
        	denboraldia.gehituJardunaldiak(jardunaldi);
        	Collections.rotate(taldeakList.subList(1, numEquipos), 1);
        }
        
        for (int jardunaldia = 1; jardunaldia < numEquipos; jardunaldia++) {
        	Jardunaldia jardunaldi = new Jardunaldia();
        	jardunaldi.setDenboraldia_kod(denboraldia);
        	jardunaldi.setHasierako_data(sqldata);
        	Calendar calendar = Calendar.getInstance();
            calendar.setTime(sqldata);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            sqldata = new java.sql.Date(calendar.getTimeInMillis());
            jardunaldi.setAmaierako_data(sqldata);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            sqldata = new java.sql.Date(calendar.getTimeInMillis());
            denboraldiaDao.insertJardunaldia(jardunaldi);
            jardunaldi.setJardunaldia_kod(denboraldiaDao.JardunaldiaKodLortu(jardunaldi.getHasierako_data()));
        	
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
                partidu.setDenboraldia(denboraldia);
                partidu.setJardunaldia(jardunaldi);
                denboraldiaDao.insertPartidua(partidu);
        	}
        	denboraldia.gehituJardunaldiak(jardunaldi);
        	Collections.rotate(taldeakList.subList(1, numEquipos), 1);
        }
        denboraldiaDao.deskonektatu();
	}
	
	public void DenboraldiHistorialaArrayListGorde() {
		denboraldiHistoriala.clear();
		
		DenboraldiaDAO denboraldiaDao = new	DenboraldiaDAO();
		
		denboraldiHistoriala = denboraldiaDao.getHistorialDenboraldia();
		
		denboraldiaDao.deskonektatu();
	}
	
	public void TaulaOsatu() {
		DenboraldiHistorialaArrayListGorde();
		dtmTaula.setRowCount(0);
		table.setModel(dtmTaula);
		for(Denboraldia denboraldia : denboraldiHistoriala) {
			Object[] rowData = {denboraldia.getDenboraldia_kod()+".Denboraldia", denboraldia.getHasierako_data(), denboraldia.getAmaierako_data()};
			dtmTaula.addRow(rowData);
		}
		for(int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			table.getColumnModel().getColumn(i).setPreferredWidth(200);
		}
		for(int i = 0; i < table.getRowCount(); i++) {
			Object value = table.getValueAt(i,2);
			if(value == null || value.toString().trim().isEmpty()) {
				dtmTaula.setValueAt("jokatzen", i, 2);
			}
		}	
	}
	public void comprobarEstadoDenboraldia() {
		DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
		boolean estado = denboraldiaDao.DenboraldiaDBGaldetu();
		if (estado == true) {
        	lblJokatzen.setVisible(true);
        	lblSortuta.setVisible(false);
        }else {
        	lblJokatzen.setVisible(false);
        }
	}
	
	public void taldeakArrayListGorde() {
		taldeakList.clear();
		
		TaldeaDAO taldeaDao = new TaldeaDAO();	
		
		taldeakList = taldeaDao.getAllTaldeak();
		
		taldeaDao.deskonektatu();
	}
	
	public void jokatzenDenboraldiaArrayListGorde() {
		jokatzendenboraldi.clear();
		
		DenboraldiaDAO denboraldiaDao = new DenboraldiaDAO();
		jokatzendenboraldi = denboraldiaDao.getJokatzenDenboraldia();
		
		denboraldiaDao.deskonektatu();
	}
}
