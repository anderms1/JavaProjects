	package ERRONKA3;
	
	import java.awt.EventQueue;
	
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;

	import ERRONKA3.WTaldeak;
import ERRONKA3.WJokalariak;

	import javax.swing.JMenuBar;
	import javax.swing.JMenu;
	import javax.swing.JMenuItem;
	import javax.swing.JOptionPane;
	import javax.swing.SwingConstants;
	import javax.swing.ImageIcon;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowAdapter;
	import java.util.ArrayList;
	import java.awt.event.ActionEvent;
	import java.awt.CardLayout;
	import javax.swing.JLabel;
	import java.awt.Font;
	import java.awt.Toolkit;

	import javax.swing.JTextPane;
	import javax.swing.JScrollPane;
	import java.awt.Color;
	import java.awt.Desktop;
	import java.awt.event.WindowEvent;
	import java.io.File;
	import java.io.IOException;
	
	
	public class WMenu extends JFrame {
	
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		private JPanel panel;
		WTaldeak taldeakLehioa;
		WDenboraldiak denboraldiakLehioa;
		WJokalariak jokalariakLehioa;
		WPartiduak partiduakLehioa;
		WSailkapena sailkapenLehioa;
		WSailkapenHistoriala sailkapenHistorialaLehioa;
		
	
		/**
		 * Create the frame.
		 */
		public WMenu() {
			setTitle("RUGBY Federezio Softwarea");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 650, 450);
			
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			
			JMenu mnInicio = new JMenu("Inicio");
			menuBar.add(mnInicio);
			
			JMenuItem mntmInicio = new JMenuItem("Inicio");
			/**
			 * Inicio lehioa irekitzeko
			 */
			mntmInicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					contentPane.removeAll();
				    contentPane.add(panel, "name_15539826892700");
				    contentPane.repaint();
				    contentPane.revalidate();
				}
			});
			mnInicio.add(mntmInicio);
			
			JMenuItem mntmExit = new JMenuItem("Exit");
			/**
			 * PROGRAMA ITXI
			 * Ere XML fitxategiak sortzeko ahalmena ematen du.
			 */
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Programatik irtetzeko.
					 System.exit(0);
				}
			});
			mnInicio.add(mntmExit);
			
			JMenu mnKudeatu = new JMenu("Kudeatu");
			mnKudeatu.setHorizontalAlignment(SwingConstants.CENTER);
			menuBar.add(mnKudeatu);
			
			JMenuItem mntmTaldeak = new JMenuItem("Taldeak");
			//WTaldeak lehioa obejetua sortu.
			taldeakLehioa = new WTaldeak();
			/**
			 * Taldeak lehioa irekitzeko
			 */
			mntmTaldeak.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Funtzioari  deitu.
					verPanel(taldeakLehioa);
				}
			});
			mnKudeatu.add(mntmTaldeak);
			
			JMenuItem mntmJokalariak = new JMenuItem("Jokalariak");
			jokalariakLehioa = new WJokalariak();
			/**
			 * Jokalariak lehioa irekitzeko
			 */
			mntmJokalariak.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Funtzioari deitu.
					jokalariakLehioa.taldeakArrayListGorde();
					verPanel(jokalariakLehioa);
				}
			});
			mnKudeatu.add(mntmJokalariak);
			
			JMenuItem mntmDenboraldiak = new JMenuItem("Denboraldiak");
			denboraldiakLehioa = new WDenboraldiak();
			mntmDenboraldiak.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					denboraldiakLehioa.jokatzenDenboraldiaArrayListGorde();
					denboraldiakLehioa.TaulaOsatu();
					denboraldiakLehioa.comprobarEstadoDenboraldia();
					verPanel(denboraldiakLehioa);
				}
			});
			mnKudeatu.add(mntmDenboraldiak);
			
			JMenuItem mntmPartiduak = new JMenuItem("Partiduak");
			partiduakLehioa = new WPartiduak();
			/**
			 * Partiduak lehioa irekitzeko
			 */
			mntmPartiduak.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					partiduakLehioa.partiduakIrakutsi();
					partiduakLehioa.jokatzenDenboraldiaArrayListGorde();
					partiduakLehioa.partiduakJardunaldianGorde();
					
					verPanel(partiduakLehioa);
				}
			});  
			mnKudeatu.add(mntmPartiduak);
			
			JMenu mnIkusi = new JMenu("Ikusi");
			menuBar.add(mnIkusi);
			
			JMenuItem mntmSailkapena = new JMenuItem("Sailkapena");
			sailkapenLehioa = new WSailkapena();
			/**
			 * Sailkapena lehioa irekitzeko
			 */
			mntmSailkapena.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sailkapenLehioa.taulaEguneratu();
					sailkapenLehioa.jokatzenDenboraldiaGorde();
					verPanel(sailkapenLehioa);
				}
			});
			mnIkusi.add(mntmSailkapena);
			
			JMenuItem mntmFitxategiak = new JMenuItem("Fitxategiak");
			/**
			 * Fitxategiak karpeta irekitzeko
			 */
			mntmFitxategiak.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
				        //Erabiltzailearen direktorioa bilatu
				        String userDirectory = System.getProperty("user.dir");
				        //Erronka2 karpeta joango da eta file.separator batekin src eta fitxategiak
				        String url = userDirectory;
				        File carpeta = new File(url);
				        Desktop.getDesktop().open(carpeta);
				    } catch (IOException ex) {
				        ex.printStackTrace();
				    }
				}
			});
			
			JMenuItem mntmSailkapen_Historiala = new JMenuItem("Denboraldi Historiala");
			sailkapenHistorialaLehioa = new WSailkapenHistoriala();
			/**
			 * Sailkapen-Historiala lehioa irekitzeko
			 */
			mntmSailkapen_Historiala.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					sailkapenHistorialaLehioa.taulaEguneratu();
					sailkapenHistorialaLehioa.denboraldiaHistorialaGorde();
					verPanel(sailkapenHistorialaLehioa);
				}
			});
			mnIkusi.add(mntmSailkapen_Historiala);
			mnIkusi.add(mntmFitxategiak);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	
			setContentPane(contentPane);
			contentPane.setLayout(new CardLayout(0, 0));
			
			panel = new JPanel();
			contentPane.add(panel, "name_15539826892700");
			panel.setLayout(null);
			
			JLabel lblNewLabel_2 = new JLabel("Programa erabiltzeko argibideak:");
			lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 12));
			lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
			lblNewLabel_2.setBounds(25, 232, 575, 14);
			panel.add(lblNewLabel_2);
			
			JTextPane txtpnTestua = new JTextPane();
			txtpnTestua.setBackground(new Color(240, 240, 240));
			txtpnTestua.setEditable(false);
			txtpnTestua.setFont(new Font("Arial", Font.PLAIN, 12));
			txtpnTestua.setText("Programa honetan hainbat fitxa aurkituko dituzu, eta horietan BOLEIBOL FEDERAZIO bateko datuak sortu, bistaratu, editatu edo ezabatu ahal izango dituzu. Programan hiru menu daude:\r\n\r\n1. INICIO\r\n\t1.1 Inicio: Hasierako fitxara joateko.\r\n\t1.2 Exit: Programatik irtetzeko.\r\n\r\n2. KUDEATU\r\n\t2.1 Taldeak: Hemen taldeak kudeatzen dira.\r\n\t2.2 Jokalariak: Hemen jokalariak kudeatzen dira.\r\n\t2.3 Denboraldiak: Denboraldi berri bat generatu eta amaitu egiten ahal da.\r\n\t2.4 Partiduak: Jardunaldi bakoitzean jolasten diren partiduen emaitzak sartzeko.\r\n\r\n3. IKUSI\r\n\t3.1 Sailkapena: Sailkapena eguneratuta ikusteko ahalmena.\r\n\t3.2 Denboraldi Historiala: Denboraldi guztien sailkapena ikusteko ahalmena.\r\n\t3.3 Fitxategiak: Generatutako XML fitxategiak ikusteko.");
			txtpnTestua.setCaretPosition(0);
			
			JScrollPane scrollPane = new JScrollPane(txtpnTestua);
			scrollPane.setBounds(25, 247, 575, 109);
			panel.add(scrollPane);
			
			JLabel lblFederazioa = new JLabel("RUGBY FEDERAZIOA");
			lblFederazioa.setHorizontalAlignment(SwingConstants.CENTER);
			lblFederazioa.setFont(new Font("Arial Black", Font.PLAIN, 30));
			lblFederazioa.setBounds(25, 0, 590, 51);
			panel.add(lblFederazioa);
			
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setIcon(new ImageIcon(WMenu.class.getResource("/img/Rugby (1).png")));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBounds(197, 43, 239, 192);
			panel.add(lblNewLabel_1);
			
			
		}
		/**
		 * verPanel FUNTZIOA
		 * Funztio hau lehioaren objetua hartuko du eta Jmenu klikatutako JmenuItem ikusiko da.
		 */
		private void verPanel(JPanel panelActual) {
			contentPane.removeAll();
			contentPane.add(panelActual, "name_" + panelActual.hashCode());
			contentPane.repaint();
			contentPane.revalidate();
		}
	}
