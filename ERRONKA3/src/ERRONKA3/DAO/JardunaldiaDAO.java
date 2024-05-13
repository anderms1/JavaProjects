package ERRONKA3.DAO;

import ERRONKA3.DAO.*;
import ERRONKA3.klaseak.Denboraldia;
import ERRONKA3.klaseak.Jardunaldia;
import ERRONKA3.klaseak.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/*
 * Datu-baseko jardunaldi taulari kontsultak egiteko.
 */
public class JardunaldiaDAO {
private Connection konexioa;
MySQL mysql = new MySQL();
	
	public JardunaldiaDAO() {
		/*konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");*/
		konexioa = mysql.sqlConnect();
	}
	/**
	 * Funtzio hau denboraldi baten jardunaldiak lortzeko erabiltzen da, ere partiduak lortzen dira.
	 * @return ArrayList<Jardunaldia>
	 */
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
			if (mysql.sqlConnect() != null && !mysql.sqlConnect().isClosed()) {
			    mysql.sqlConnect().close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
