package ERRONKA3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DenboraldiaDAO {
	private Connection konexioa;
	
	public DenboraldiaDAO() {
		try {
			konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");
		}catch(SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
