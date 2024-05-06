package ERRONKA3.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ERRONKA3.klaseak.Denboraldia;
import ERRONKA3.klaseak.Taldea;

public class denb_taldeaDAO {
	private Connection konexioa;
	
	public denb_taldeaDAO(){
		try {
			konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");
		}catch(SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public ArrayList<Taldea> getJokatzenTaldeakList(Denboraldia denboraldia){
		ArrayList<Taldea> taldeak = new ArrayList<Taldea>();
		String sql = "SELECT * FROM denb_taldea JOIN taldea ON taldea.talde_kod = denb_taldea.talde_kod WHERE denb_taldea.denboraldia_kod = ?";
		try (PreparedStatement statement = konexioa.prepareStatement(sql)){
			statement.setInt(1, denboraldia.getDenboraldia_kod());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Taldea taldea = new Taldea();
				taldea.setTalde_kod(resultSet.getInt("talde_kod"));
				taldea.setTalde_izena(resultSet.getString("talde_izena"));
				taldea.setDefeats(resultSet.getInt("porrotak"));
				taldea.setPuntuak(resultSet.getInt("puntuak"));
				taldea.setTies(resultSet.getInt("berdinketak"));
				taldea.setWins(resultSet.getInt("irabaziak"));
				taldeak.add(taldea);
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taldeak;
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
