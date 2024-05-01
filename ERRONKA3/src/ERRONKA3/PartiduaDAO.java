package ERRONKA3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PartiduaDAO {
	private Connection konexioa;
	
	public PartiduaDAO() {
		try {
			konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");
		}catch(SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public ArrayList<Partidua> jardunaldiPartiduakLortu(Jardunaldia jardunaldia){
		ArrayList<Partidua> partiduak = new ArrayList<Partidua>();
		TaldeaDAO taldeaDao = new TaldeaDAO();
		String sql = "SELECT * FROM partidua WHERE jardunaldia_kod = ?";
		try (PreparedStatement statement = konexioa.prepareStatement(sql)){
			statement.setInt(1, jardunaldia.getJardunaldia_kod());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Partidua partidua = new Partidua();
				partidua.setPartidua_kod(resultSet.getInt("partidua_kod"));
				partidua.setJardunaldia(jardunaldia);
				partidua.setDenboraldia(jardunaldia.getDenboraldia_kod());
				partidua.setEtxeko_talde(taldeaDao.getTaldeaKodearekin(resultSet.getInt("etxeko_talde_kod")));
				partidua.setKanpoko_talde(taldeaDao.getTaldeaKodearekin(resultSet.getInt("kanpoko_talde_kod")));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		taldeaDao.deskonektatu();
		return partiduak;
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
