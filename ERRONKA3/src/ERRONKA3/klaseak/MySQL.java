package ERRONKA3.klaseak;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Klase hau ping-a eta konexioa egiten du zerbitzariarekin.
 */
public class MySQL {
		private Connection konexioa;
		String ipa = "localhost";
		String dbname = "rugby";
		String user = "java";
		String pass = "";

		/**
		 * www.rugbyfederazioa.com egiten duenean ping hori izango zen host-a bezetela egingo du ping localhost-arekin eta hori izango da host-a
		 * @return
		 */
		@SuppressWarnings("unused")
		public String pingServer() {
			try {
				String[] host = new String[] {"www.rugbyfederazioa.com", "localhost"} ;
					for (int j = 0; j < host.length-1; j++){
						String comando = "ping -c 1 " + host[j];
		
						Process proceso = Runtime.getRuntime().exec(comando);
						BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
						/**
						 * Aurkitzen badu konexioa sartzen da eta posizioaren ping-a itzuli.
						 */
						if (!reader.readLine().contains("La solicitud de ping no pudo encontrar el host")) {
							for (int i = 0; reader.readLine() != null; i++) return ipa = host[i]; break;
						}
		            }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ipa;
		}
		
		/**
		 * Datu base-arekin konexioa egiten du pingServer-aren host-areki.		
		 * @return
		 */
		public Connection sqlConnect() {
			try {
				konexioa = DriverManager.getConnection("jdbc:mysql://" + pingServer() + "/" + dbname, user, pass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return konexioa;
		}

}


