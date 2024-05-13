package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.ftp.FTP;

import ERRONKA3.DAO.TaldeaDAO;
import ERRONKA3.klaseak.FTPUploader;
import ERRONKA3.klaseak.JRealFileChooser;
import ERRONKA3.klaseak.Taldea;

import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
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

public class WArgazkiak extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtmTaula;
	private ArrayList<Taldea> taldeaList = new ArrayList<Taldea>();
	private JTextField txtTaldea;
	private JTextField txtJokalaria;

	/**
	 * Create the panel.
	 */
	public WArgazkiak() {
		
		setLayout(null);
		setBounds(100, 100, 650, 450);
		
		JLabel lblNewLabel = new JLabel("ARGAZKIAK KUDEATU");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNewLabel.setBounds(26, 11, 572, 22);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(26, 44, 572, 319);
		add(panel);
		panel.setLayout(null);
		
		
		JRealFileChooser jrfcJokalariak = new JRealFileChooser(null, "Autatu", 0, 0, 250, 20, 100, 20);
		jrfcJokalariak.getButton().setText("Hautatu");
		jrfcJokalariak.setLocation(116, 114);
		jrfcJokalariak.setSize(354, 20);
		panel.add(jrfcJokalariak);
		
		JRealFileChooser jrfcTaldea = new JRealFileChooser((String) null, "Autatu", 0, 0, 250, 20, 100, 20);
		jrfcTaldea.getButton().setText("Hautatu");
		jrfcTaldea.setBounds(116, 193, 354, 20);
		panel.add(jrfcTaldea);
		
		JLabel lblNewLabel_1 = new JLabel("Jokalaria");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_1.setBounds(116, 67, 68, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Taldea");
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_2.setBounds(116, 150, 62, 13);
		panel.add(lblNewLabel_2);
		
		txtTaldea = new JTextField();
		txtTaldea.setBounds(200, 167, 96, 19);
		panel.add(txtTaldea);
		txtTaldea.setColumns(10);
		
		txtJokalaria = new JTextField();
		txtJokalaria.setColumns(10);
		txtJokalaria.setBounds(200, 88, 96, 19);
		panel.add(txtJokalaria);
		
		
		/**
		 * Botoia igotzarakoan konprobatzen du fitxategiaren ruta absolutua eta
		 */
		JButton btnIgo = new JButton("Igo Zerbitzarira");
		btnIgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
                    // Obtener los archivos seleccionados
                    File fIzenaSaharraJoka = (!jrfcJokalariak.getTextField().getText().isEmpty()) ? new File(jrfcJokalariak.getTextField().getText()) : null;
                    File fIzenaSaharraTalde = (!jrfcTaldea.getTextField().getText().isEmpty()) ? new File(jrfcTaldea.getTextField().getText()) : null;

                    // Crear un objeto FTPUploader para subir archivos
                    FTPUploader ftpUploader = new FTPUploader();

                    // Subir archivo del jugador
                    if (fIzenaSaharraJoka != null) {
                        String nombreArchivoJokalaria = txtJokalaria.getText();
                        if (!nombreArchivoJokalaria.toLowerCase().endsWith(".png") && !nombreArchivoJokalaria.toLowerCase().endsWith(".jpg")) {
                            nombreArchivoJokalaria += ".png";
                        }
                        File archivoCopiaJokalaria = new File(fIzenaSaharraJoka.getParent(), nombreArchivoJokalaria);
                        copiarArchivo(fIzenaSaharraJoka, archivoCopiaJokalaria); // Copiar el archivo
                        ftpUploader.uploadFile(archivoCopiaJokalaria.getAbsolutePath(), "/var/www/RUGBY3/jokalari-argazkiak/" + nombreArchivoJokalaria);
                    }

                    // Subir archivo del equipo
                    if (fIzenaSaharraTalde != null) {
                        String nombreArchivoTaldea = txtTaldea.getText();
                        if (!nombreArchivoTaldea.toLowerCase().endsWith(".png") && !nombreArchivoTaldea.toLowerCase().endsWith(".jpg")) {
                            nombreArchivoTaldea += ".png";
                        }
                        File archivoCopiaTaldea = new File(fIzenaSaharraTalde.getParent(), nombreArchivoTaldea);
                        copiarArchivo(fIzenaSaharraTalde, archivoCopiaTaldea); // Copiar el archivo
                        ftpUploader.uploadFile(archivoCopiaTaldea.getAbsolutePath(), "/var/www/RUGBY3/argazkiak/" + nombreArchivoTaldea);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
		});
		btnIgo.setBounds(225, 252, 124, 21);
		panel.add(btnIgo);
		
		JLabel lblNewLabel_3 = new JLabel("Fitxategi Izena:");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(116, 91, 85, 13);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Fitxategi Izena:");
		lblNewLabel_3_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_3_1.setBounds(116, 171, 85, 13);
		panel.add(lblNewLabel_3_1);
		
	}
	  private void copiarArchivo(File archivoOrigen, File archivoDestino) throws IOException {
	        FileInputStream fis = null;
	        FileOutputStream fos = null;
	        try {
	            fis = new FileInputStream(archivoOrigen);
	            fos = new FileOutputStream(archivoDestino);
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = fis.read(buffer)) > 0) {
	                fos.write(buffer, 0, length);
	            }
	        } finally {
	            if (fis != null) {
	                fis.close();
	            }
	            if (fos != null) {
	                fos.close();
	            }
	        }
	    }
}
