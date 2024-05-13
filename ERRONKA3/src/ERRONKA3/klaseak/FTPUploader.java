package ERRONKA3.klaseak;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
/**
 * Klase hau erabiltzen da serbitzariari fitxategiak igotzeko.
 */
public class FTPUploader {

    private int port = 21;
    private String user = "mrweb";
    private String pass = "I_113";

    public void uploadFile(String Local, String server) {
        FTPClient ftpClient = new FTPClient();
        try {
        	MySQL mysql = new MySQL();
            ftpClient.connect(mysql.pingServer(), port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Ruta del archivo local
            String firstLocalFile = Local;

            try (FileInputStream input = new FileInputStream(firstLocalFile)) {
                boolean done = ftpClient.storeFile(server, input);
                if (done) {
                    System.out.println("Fitxategia zerbitzariara igo da.");
                } else {
                    System.out.println("Errorea fitxategia igotzean.");
                }
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
