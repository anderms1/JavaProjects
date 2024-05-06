package ERRONKA3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JardunaldiaDAO {
private Connection konexioa;
	
	public JardunaldiaDAO() {
		try {
			konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");
		}catch(SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public ArrayList<Jardunaldia> denboraldiJardunaldiakLortu(Denboraldia denboraldia){
		ArrayList<Jardunaldia> jardunaldiak = new ArrayList<Jardunaldia>();
		PartiduaDAO partiduaDao = new PartiduaDAO();
        String sql = "SELECT * FROM jardunaldia WHERE denboraldia_kod = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)){
        	statement.setInt(1, denboraldia.getDenboraldia_kod());
            ResultSet resultSet = statement.executeQuery();
        	while (resultSet.next()) {
        		Jardunaldia jardunaldia = new Jardunaldia();
				jardunaldia.setJardunaldia_kod(resultSet.getInt("jardunaldia_kod"));
				jardunaldia.setDenboraldia_kod(denboraldia);
				jardunaldia.setHasierako_data(resultSet.getDate("hasiera_data"));
				jardunaldia.setAmaierako_data(resultSet.getDate("amaiera_data"));
				jardunaldia.setPartiduak(partiduaDao.jardunaldiPartiduakLortu(jardunaldia));
				jardunaldiak.add(jardunaldia);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        partiduaDao.deskonektatu();
        return jardunaldiak;
	}
	
	public void deskonektatu(){
        try {
			if (konexioa != null && !konexioa.isClosed()) {
			    konexioa.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
