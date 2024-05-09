package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import ERRONKA3.klaseak.Usuario;

public class WErregistratu extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtErabiltzailea;
	private WLogin loginLehioa;
	private JPasswordField passPasahitza;
	private JPasswordField passPasahitza2;

	/**
	 * Create the panel.
	 */
	public WErregistratu() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ERREGISTRATU");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel.setBounds(10, 10, 419, 43);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 63, 408, 215);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Erabiltzailea:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(59, 10, 105, 13);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Pasahitza:");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(59, 56, 105, 13);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Errepikatu pasahitza:");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(59, 103, 139, 13);
		panel.add(lblNewLabel_1_1_1);
		
		txtErabiltzailea = new JTextField();
		txtErabiltzailea.setFont(new Font("Arial", Font.PLAIN, 10));
		txtErabiltzailea.setColumns(10);
		txtErabiltzailea.setBounds(59, 27, 285, 20);
		panel.add(txtErabiltzailea);
		
		JButton btnErregistratu = new JButton("Erregistratu");
		btnErregistratu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] pasahitza = passPasahitza.getPassword();
				char[] pasahitza2 = passPasahitza2.getPassword();
				if(txtErabiltzailea.getText().isEmpty() || pasahitza.length == 0 || pasahitza2.length == 0) {
					JOptionPane.showMessageDialog(null,"Datu guztiak sartu behar duzu.","Error",JOptionPane.ERROR_MESSAGE);
				}else if(!Arrays.equals(pasahitza, pasahitza2)){
					JOptionPane.showMessageDialog(null,"Pasahitzak ez dira berdinak.","Error",JOptionPane.ERROR_MESSAGE);
				}else{
					String erabiltzailea = txtErabiltzailea.getText().toUpperCase();
					EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/Usuarios.odb");
					EntityManager em = emf.createEntityManager();
					if(!ErabiltzaileaExistitu(em)) {
						try {
							//Erabiltzailea sortzen dugu
							em.getTransaction().begin();
							Usuario usuario = new Usuario();
							usuario.setUsername(erabiltzailea);
							String password = new String(pasahitza);
							usuario.setPassword(password);
							em.persist(usuario);
							em.getTransaction().commit();
						}finally {
							em.close();
							emf.close();
							JOptionPane.showMessageDialog(null, "Erabiltzailea sortu da.", "Informazioa", JOptionPane.INFORMATION_MESSAGE);
							loginLehioa.mostrarLogin();
						}
					}else {
						JOptionPane.showMessageDialog(null, "Erabiltzailea hau erregistratuta dago.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					 
				}
				
			}
		});
		btnErregistratu.setBounds(145, 158, 115, 21);
		panel.add(btnErregistratu);
		
		passPasahitza = new JPasswordField();
		passPasahitza.setBounds(59, 74, 285, 20);
		panel.add(passPasahitza);
		
		passPasahitza2 = new JPasswordField();
		passPasahitza2.setBounds(59, 121, 285, 20);
		panel.add(passPasahitza2);

	}
	
	public void setLoginFrame(WLogin loginLehioa) {
        this.loginLehioa = loginLehioa;
    }
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
