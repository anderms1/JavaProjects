package ERRONKA3;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
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
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

public class WDenboraldiak extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String List = null;
	private JList list;
	private DefaultListModel<String> dlm;
	//private ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
	private JTextField textIzena;
	private JComboBox comboBox;
	//private ArrayList<Denboraldia> denboraldiJokatzenList = new ArrayList<Denboraldia>();
	//private ArrayList<Denboraldia> denboraldiHistorialaList = new ArrayList<Denboraldia>();
	/**
	 * Create the panel.
	 */
	public WDenboraldiak() {
		
		dlm = new DefaultListModel<String>();

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
		
		JButton btnDBerria = new JButton("Denboraldi Berria Sortu");
		btnDBerria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnDBerria.setFont(new Font("Arial Black", Font.PLAIN, 10));
		btnDBerria.setBounds(86, 52, 169, 36);
		panel.add(btnDBerria);
		
		comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setFont(new Font("Arial", Font.PLAIN, 15));
		comboBox.setBounds(322, 10, 153, 32);
		panel.add(comboBox);
		
		JButton btnDAmaitu = new JButton("Denboraldia Amaitu");
		btnDAmaitu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnDAmaitu.setFont(new Font("Arial Black", Font.PLAIN, 11));
		btnDAmaitu.setBounds(322, 52, 153, 36);
		panel.add(btnDAmaitu);
		
		textIzena = new JTextField();
		textIzena.setBounds(87, 10, 169, 32);
		panel.add(textIzena);
		textIzena.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(201, 183, 208, 183);
		add(scrollPane);
		
		
		list = new JList();
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		scrollPane.setViewportView(list);
		list.setModel(dlm);
		
		JLabel lblNewLabel_1 = new JLabel("Denboraldi Historiala");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 17));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(201, 146, 208, 27);
		add(lblNewLabel_1);
		
	}
}
