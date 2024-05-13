package ERRONKA3.klaseak;


import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;

/**
 * Klase hau JFileChooser, JTextField eta botoia bat sortzen ditu.
 */
public class JRealFileChooser extends JPanel {
    private JTextField textField;
    private JButton button;
    private JFileChooser fileChooser;
    
    /**
     * 
     * @param fileRoot fitxategiaren hibilbidea.
     * @param btnText botoian zer ipitzen du.
     * @param x JRealFileChooser-aren x posizioa.
     * @param y JRealFileChooser-aren y posizioa.
     * @param width ze tamaina eukiko du hibildearen JTextField-a.
     * @param height ze tamaina eukiko du hibildearen JTextField-a.
     * @param btnWight ze tamaina eukiko du hibildearen JButton-a.
     * @param btnHeight ze tamaina eukiko du hibildearen JButton-a.
     */
    public JRealFileChooser(String fileRoot, String btnText, int x, int y, int width, int height, int btnWight, int btnHeight) {
        super();
        this.setLayout(null);
       
        button = new JButton(btnText);
        button.setBounds(width, y, btnWight, btnHeight);
        this.add(button);

        textField = new JTextField(fileRoot);
        textField.setSize(width, height);
        this.add(textField, BorderLayout.CENTER);


        fileChooser = new JFileChooser(fileRoot);
       
        /**
         * Botoiari klikatzerakoan JFileChooser normal bat irekitzen da eta fitxategia aukeratzeko.
         */
        button.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }
    
    /**
     * 
     * @return
     */
	public JTextField getTextField() {
		return textField;
	}
	
	/**
	 * 
	 * @param textField
	 */
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	/**
	 * 
	 * @return
	 */
	public JButton getButton() {
		return button;
	}

	/**
	 * 
	 * @param button
	 */
	public void setButton(JButton button) {
		this.button = button;
	}

	/**
	 * fitxategia itzuli
	 * @return
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	/**
	 * fitxategia autatu
	 * @param fileChooser
	 */
	public void setFileChooser(JFileChooser fileChooser) {
		this.fileChooser = fileChooser;
	}




}
