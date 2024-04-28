package ERRONKA3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JokalariaDAO {
private Connection konexioa;
private ArrayList<Taldea> taldeaList = new ArrayList<Taldea>();
	
	public JokalariaDAO() {
		try {
			konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");
		}catch(SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public ArrayList<Jokalaria> getAllJokalariak(){
		ArrayList<Jokalaria> taldeakList = new ArrayList<Jokalaria>();
        String sql = "SELECT * FROM jokalaria";
        try (Statement statement = konexioa.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
        	while (resultSet.next()) {
        		Jokalaria jokalaria = new Jokalaria();
				jokalaria.setJokalaria_kod(resultSet.getInt("jokolaria_kod"));
				jokalaria.setIzena(resultSet.getString("izena"));
				jokalaria.setDorsala(resultSet.getInt("dorsala"));
				jokalaria.setPosizioa(resultSet.getString("posizioa"));
				int talde_kod = resultSet.getInt("talde_kod");
				jokalaria.setTaldea(kodearekinTaldeaLortu(talde_kod));
	            taldeakList.add(jokalaria);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return taldeakList;
	}
	
	public void insertJokalaria(Jokalaria jokalaria){
        String sql ="INSERT INTO jokalaria(izena, dorsala, posizioa, talde_kod) VALUES (?, ?, ?, ?)";
        try {
        	PreparedStatement statement = konexioa.prepareStatement(sql);
            statement.setString(1, jokalaria.getIzena());
            statement.setInt(2, jokalaria.getDorsala());
            statement.setString(3, jokalaria.getPosizioa());
            statement.setInt(4, jokalaria.getTaldea().getTalde_kod());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void updateJokalaria(Jokalaria jokalaria) {
		String sql = "UPDATE jokalaria SET izena = ?, dorsala = ?, posizioa = ?, talde_kod = ? WHERE izena = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
            statement.setString(1, jokalaria.getIzena());
            statement.setInt(2, jokalaria.getDorsala());
            statement.setString(3, jokalaria.getPosizioa());
            statement.setInt(4, jokalaria.getTaldea().getTalde_kod());
            statement.setString(5, jokalaria.getIzena());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteJokalaria(String izena){
        String sql = "DELETE FROM jokalaria WHERE izena = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
            statement.setString(1, izena);
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public boolean JokalariaDBGaldetu(String izena){
        String sql = "SELECT 1 FROM jokalaria WHERE izena = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	
        	statement.setString(1, izena);
        	ResultSet resultSet = statement.executeQuery();

            // Si resultSet tiene al menos una fila, el grupo existe
            return resultSet.next();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
	private Taldea kodearekinTaldeaLortu(int talde_kod) {
		TaldeaDAO taldeDao = new TaldeaDAO();
		taldeaList = taldeDao.getAllTaldeak();
		for(Taldea taldea : taldeaList) {
			if (taldea.getTalde_kod() == talde_kod) {
				return taldea;
			}
		}
		return null;
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
