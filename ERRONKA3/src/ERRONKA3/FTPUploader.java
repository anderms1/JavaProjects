package ERRONKA3;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPUploader {

    private String serverAddress = "ftp.rugbyfederazioa.com";
    private int port = 21;
    private String user = "mrweb";
    private String pass = "I_113";

    public void uploadFile(String Local, String server) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(serverAddress, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Ruta del archivo local
            String firstLocalFile = Local;

            try (FileInputStream input = new FileInputStream(firstLocalFile)) {
                boolean done = ftpClient.storeFile(server, input);
                if (done) {
                    System.out.println("El archivo se ha subido exitosamente.");
                } else {
                    System.out.println("Hubo un error al subir el archivo.");
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
