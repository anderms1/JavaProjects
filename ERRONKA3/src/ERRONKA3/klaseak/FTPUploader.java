package ERRONKA3.klaseak;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
/**
 * Zerbitzariaren MySQL-ren konexioa erabiliz(pingServer) ftp bidez igotzen ditu fitxategiak.
 */
public class FTPUploader {

    private int port = 21;
    private String user = "mrweb";
    private String pass = "I_113";
    /**
     * uploadFile artzen du nahi dugun fitxategia eta non kokatunai dugu zerbitzarian eta igotzen du.
     * @param Local
     * @param server
     */
    public void uploadFile(String Local, String server) {
        FTPClient ftpClient = new FTPClient();
        try {
        	MySQL mysql = new MySQL();
            ftpClient.connect(mysql.pingServer(), port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Fitxategiaren hibilbidea
            String firstLocalFile = Local;

            // Fitxategia sortu eta zerbitzeriaren hibilibidean sartu
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
        	// ftp-a deskonektatu
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
