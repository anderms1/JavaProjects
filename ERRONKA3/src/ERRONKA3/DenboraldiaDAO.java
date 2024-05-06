package ERRONKA3;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.ArrayList;

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
	
	public void insertDenboraldia(Denboraldia denboraldia){
        String sql ="INSERT INTO denboraldia(hasierako_data) VALUES (?)";
        try {
        	PreparedStatement statement = konexioa.prepareStatement(sql);
            statement.setDate(1, denboraldia.getHasierako_data());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void updateDenboraldia(Denboraldia denboraldia) {
		String sql ="UPDATE denboraldia SET amaierako_data = ? WHERE denboraldia_kod = ?";
        try {
        	PreparedStatement statement = konexioa.prepareStatement(sql);
            statement.setDate(1, denboraldia.getAmaierako_data());
            statement.setInt(2, denboraldia.getDenboraldia_kod());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean DenboraldiDataGaldetu(Date data){
        String sql = "SELECT 1 FROM denboraldia WHERE YEAR(hasierako_data) = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	statement.setInt(1, data.getYear());
        	ResultSet resultSet = statement.executeQuery();

            // Si resultSet tiene al menos una fila, si hay nulo
            return resultSet.next();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
	
	public void insertDenb_Taldea(Denboraldia denboraldia, Taldea taldea) {
		String sql ="INSERT INTO denb_taldea(denboraldia_kod, talde_kod) VALUES (?, ?)";
        try {
        	PreparedStatement statement = konexioa.prepareStatement(sql);
            statement.setInt(1, denboraldia.getDenboraldia_kod());
            statement.setInt(2, taldea.getTalde_kod());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean DenboraldiaDBGaldetu(){
        String sql = "SELECT 1 FROM denboraldia WHERE amaierako_data is NULL";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	
        	ResultSet resultSet = statement.executeQuery();

            // Si resultSet tiene al menos una fila, si hay nulo
            return resultSet.next();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
	
	public ArrayList<Denboraldia> getHistorialDenboraldia(){
		ArrayList<Denboraldia> denboraldiHistoriala = new ArrayList<Denboraldia>();
		JardunaldiaDAO jardunaldiaDao = new JardunaldiaDAO();
        String sql = "SELECT * FROM denboraldia";
        try (Statement statement = konexioa.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
        	while (resultSet.next()) {
        		Denboraldia denboraldia = new Denboraldia();
				denboraldia.setDenboraldia_kod(resultSet.getInt("denboraldia_kod"));
				denboraldia.setHasierako_data(resultSet.getDate("hasierako_data"));
				denboraldia.setAmaierako_data(resultSet.getDate("amaierako_data"));
				denboraldia.setJardunaldiak(jardunaldiaDao.denboraldiJardunaldiakLortu(denboraldia));
				denboraldiHistoriala.add(denboraldia);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        jardunaldiaDao.deskonektatu();
        return denboraldiHistoriala;
	}
	
	public ArrayList<Denboraldia> getJokatzenDenboraldia(){
		ArrayList<Denboraldia> denboraldiHistoriala = new ArrayList<Denboraldia>();
        String sql = "SELECT * FROM denboraldia WHERE amaierako_data is null";
        try (Statement statement = konexioa.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
        	while (resultSet.next()) {
        		Denboraldia denboraldia = new Denboraldia();
				denboraldia.setDenboraldia_kod(resultSet.getInt("denboraldia_kod"));
				denboraldia.setHasierako_data(resultSet.getDate("hasierako_data"));
				denboraldiHistoriala.add(denboraldia);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return denboraldiHistoriala;
	}
	
	public void insertJardunaldia(Jardunaldia jardunaldia) {
		 String sql = "INSERT INTO jardunaldia(denboraldia_kod, hasiera_data, amaiera_data) VALUES(?, ?, ?)";
		 try {
	        	PreparedStatement statement = konexioa.prepareStatement(sql);
	            statement.setInt(1, jardunaldia.getDenboraldia_kod().getDenboraldia_kod());
	            statement.setDate(2, jardunaldia.getHasierako_data());
	            statement.setDate(3, jardunaldia.getAmaierako_data());
	            statement.executeUpdate();
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public int JardunaldiaKodLortu(Date data) {
		String sql = "SELECT jardunaldia_kod FROM jardunaldia WHERE hasiera_data = ?";
		 try(PreparedStatement statement = konexioa.prepareStatement(sql)) {
            statement.setDate(1, (java.sql.Date) data);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
            	return resultSet.getInt("jardunaldia_kod");
            }
            
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return -1;
	}
	
	public void insertPartidua(Partidua partidua) {
		String sql = "INSERT INTO partidua(jardunaldia_kod, denboraldia_kod, etxeko_talde_kod, kanpoko_talde_kod, data) VALUES(?, ?, ?, ?, ?)";
		 try {
	        	PreparedStatement statement = konexioa.prepareStatement(sql);
	            statement.setInt(1, partidua.getJardunaldia().getJardunaldia_kod());
	            statement.setInt(2, partidua.getDenboraldia().getDenboraldia_kod());
	            statement.setInt(3, partidua.getEtxeko_talde().getTalde_kod());
	            statement.setInt(4, partidua.getKanpoko_talde().getTalde_kod());
	            statement.setDate(5, partidua.getData());
	            statement.executeUpdate();
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean partiduakAmaituGaldetu(Denboraldia denboraldia) {
		String sql = "SELECT 1 FROM partidua WHERE emaitza IS NULL AND denboraldia_kod = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	statement.setInt(1, denboraldia.getDenboraldia_kod());
        	ResultSet resultSet = statement.executeQuery();

            // Si resultSet tiene al menos una fila, si hay nulo
            return resultSet.next();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public java.sql.Date azkenDataLortu(Denboraldia denboraldia) {
		String sql = "SELECT amaiera_data FROM jardunaldia WHERE denboraldia = ? ORDER BY jardunaldia_kod DESC LIMIT 1;";
		try(PreparedStatement statement = konexioa.prepareStatement(sql)) {
           statement.setInt(1, denboraldia.getDenboraldia_kod());
           ResultSet resultSet = statement.executeQuery();
           return resultSet.getDate("amaiera_data");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Jokalaria> getAllJokalariak(){
		ArrayList<Jokalaria> jokalariak = new ArrayList<Jokalaria>();
		TaldeaDAO taldeaDao = new TaldeaDAO();
        String sql = "SELECT * FROM jokalaria";
        try (Statement statement = konexioa.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
        	while (resultSet.next()) {
        		Jokalaria jokalaria = new Jokalaria();
        		jokalaria.setIzena(resultSet.getString("izena"));
        		jokalaria.setDorsala(resultSet.getInt("dorsala"));
        		jokalaria.setPosizioa(resultSet.getString("posizioa"));
        		jokalaria.setTaldea(taldeaDao.getTaldeaKodearekin(resultSet.getInt("talde_kod")));
        		jokalariak.add(jokalaria);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        taldeaDao.deskonektatu();
        return jokalariak;
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
