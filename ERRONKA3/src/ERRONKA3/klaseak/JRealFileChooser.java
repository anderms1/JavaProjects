package ERRONKA3.klaseak;


import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;

public class JRealFileChooser extends JPanel {
    private JTextField textField;
    private JButton button;
    private JFileChooser fileChooser;

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
         * Whene the button is clicked the fileChooser will open and choose the file
         */
        button.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
    }

public JTextField getTextField() {
return textField;
}

public void setTextField(JTextField textField) {
this.textField = textField;
}

public JButton getButton() {
return button;
}

public void setButton(JButton button) {
this.button = button;
}

public JFileChooser getFileChooser() {
return fileChooser;
}

public void setFileChooser(JFileChooser fileChooser) {
this.fileChooser = fileChooser;
}



}
