package ERRONKA3;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ERRONKA3.klaseak.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Color;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class WLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtErabiltzailea;
	private WErregistratu erregistratuLehioa;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WLogin frame = new WLogin();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(22, 10, 380, 56);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel.setBounds(10, 10, 360, 36);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(22, 65, 380, 188);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblErabiltzailea = new JLabel("Erabiltzailea:");
		lblErabiltzailea.setFont(new Font("Arial", Font.PLAIN, 15));
		lblErabiltzailea.setBounds(51, 10, 85, 13);
		panel_1.add(lblErabiltzailea);
		
		txtErabiltzailea = new JTextField();
		txtErabiltzailea.setFont(new Font("Arial", Font.PLAIN, 10));
		txtErabiltzailea.setBounds(51, 29, 285, 20);
		panel_1.add(txtErabiltzailea);
		txtErabiltzailea.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPassword.setBounds(51, 68, 85, 13);
		panel_1.add(lblPassword);
		
		erregistratuLehioa = new WErregistratu();
		JLabel lblErregistratu = new JLabel("<html><u>¿Ez duzu konturik?</u></html>");
		lblErregistratu.addMouseListener(new MouseAdapter() {
			@Override
			/**
			 * Erregistratzeko lehioa ikusteko.
			 */
			public void mouseClicked(MouseEvent e) {
				erregistratuLehioa.setLoginFrame(WLogin.this);
				verPanel(erregistratuLehioa);
			}
		});
		lblErregistratu.setForeground(new Color(115, 115, 255));
		lblErregistratu.setHorizontalAlignment(SwingConstants.CENTER);
		lblErregistratu.setFont(new Font("Arial", Font.PLAIN, 10));
		lblErregistratu.setBounds(51, 128, 285, 13);
		panel_1.add(lblErregistratu);
		
		JButton btnLogin = new JButton("Login");
		/**
		 * Login botoiari ematean eta kontu bat badekozu programan sartuko zara.
		 */
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] pasahitza = passwordField.getPassword();
				if(txtErabiltzailea.getText().isEmpty() || pasahitza.length == 0) {
					JOptionPane.showMessageDialog(null,"Datu guztiak sartu behar duzu.","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					//Komentatu 4 beheko lerroa localHost erabili nahi baduzu
					Map<String, String> properties = new HashMap<>();
					properties.put("javax.persistence.jdbc.user", "admin");
					properties.put("javax.persistence.jdbc.password", "admin");
					EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb://www.rugbyfederazioa.com:6136/Usuarios.odb", properties);
					/*
					 * Ezabatu komentarioa hau eta ipini beheko komandoa ObjectDB localHost erabiltzeko
					 * EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/Usuarios.odb");
					 */
					EntityManager em = emf.createEntityManager();
					if(!ErabiltzaileaExistitu(em)) {
						JOptionPane.showMessageDialog(null, "Erabiltzailea hau ez da existitzen.", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						if(pasahitzaBaieztatu(em)) {
							em.close();
							emf.close();
							mostrarMenu();
						}else {
							JOptionPane.showMessageDialog(null, "Pasahitza okerra da.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
			}
		});
		btnLogin.setFont(new Font("Arial", Font.PLAIN, 10));
		btnLogin.setBounds(152, 150, 85, 21);
		panel_1.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(51, 86, 285, 20);
		panel_1.add(passwordField);
	}
	/**
	 * Lehio batetik bestera joateko erabiltzen den funtzioa da.
	 */
	private void verPanel(JPanel panelNuevo) {
		panelNuevo.setSize(contentPane.getSize());
		contentPane.removeAll();
		contentPane.add(panelNuevo);
		contentPane.repaint();
		contentPane.revalidate();
	}
	/**
	 * Login lehioa ikusteko
	 */
	public void mostrarLogin() {
		verPanel(new WLogin().contentPane);
	}
	/**
	 * Programan sartzeko erabiltzen da.
	 */
	private void mostrarMenu() {
		WMenu menu = new WMenu();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);

        // Cerrar la ventana de login
        dispose();
	}
	/**
	 * Datu-baseko pasahitza eta sartutako pasahitza compartuko egingo ea berdinak diren.
	 * @return boolean
	 */
	private boolean pasahitzaBaieztatu(EntityManager em) {
		//String password = String.valueOf(passwordField.getPassword());
		Query qErabiltzaile = em.createQuery("SELECT COUNT(l) FROM Usuario l WHERE l.username ='" +txtErabiltzailea.getText().toUpperCase()+"' AND l.password = '"+ new String(passwordField.getPassword())
				+"'", Usuario.class);
		long pasahitzaAurkituta = (long) qErabiltzaile.getSingleResult();
		if(pasahitzaAurkituta == 1) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Sartutako erabiltailea ea datu-basean existitzen den jakiteko da.
	 * @return boolean
	 */
	public boolean ErabiltzaileaExistitu(EntityManager em) {
		Query qErabiltzaile = em.createQuery("SELECT COUNT(Usuario) FROM Usuario  WHERE username='" +txtErabiltzailea.getText().toUpperCase()+"'", Usuario.class);
		long usuarioEncontrado = (long) qErabiltzaile.getSingleResult();
		if(usuarioEncontrado == 0) {
			return false;
		}else {
			return true;
		}
	}
}