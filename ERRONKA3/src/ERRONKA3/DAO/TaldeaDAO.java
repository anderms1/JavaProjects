package ERRONKA3.DAO;

import ERRONKA3.DAO.*;
import ERRONKA3.klaseak.MySQL;
import ERRONKA3.klaseak.Taldea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/*
 * Datu-baseko Taldea taulari kontsultak egiteko.
 */
public class TaldeaDAO {
	private Connection konexioa;
	MySQL mysql = new MySQL();
	
	public TaldeaDAO() {
		/*konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");*/
		konexioa = mysql.sqlConnect();
	}
	/**
	 * Funtzio hau talde guztiak lortzeko da.
	 * @return ArrayList<Taldea>
	 */
	public ArrayList<Taldea> getAllTaldeak(){
		ArrayList<Taldea> taldeakList = new ArrayList<Taldea>();
        String sql = "SELECT * FROM taldea";
        try (Statement statement = konexioa.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
        	while (resultSet.next()) {
        		Taldea talde = new Taldea();
				talde.setTalde_kod(resultSet.getInt("talde_kod"));
	            talde.setTalde_izena(resultSet.getString("talde_izena").toUpperCase());
	            talde.setHerria(resultSet.getString("herria").toUpperCase());
	            talde.setZuzendaria(resultSet.getString("zuzendaria").toUpperCase());
	            taldeakList.add(talde);
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return taldeakList;
	}
	/**
	 * Funtzio hau datu-baseko talde taulan talde berri bat sartzeko da.
	 */
	public void insertTaldea(Taldea taldea){
        String sql ="INSERT INTO taldea(talde_izena, herria, zuzendaria) VALUES (?, ?, ?)";
        try {
        	PreparedStatement statement = konexioa.prepareStatement(sql);
            statement.setString(1, taldea.getTalde_izena());
            statement.setString(2, taldea.getHerria());
            statement.setString(3, taldea.getZuzendaria());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * Funtzio hau datu-baseko talde taulan talde berriztatzeko da.
	 */
	public void updateTaldea(Taldea taldea) {
		String sql = "UPDATE taldea SET talde_izena = ?, herria = ?, zuzendaria = ? WHERE talde_izena = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
            statement.setString(1, taldea.getTalde_izena());
            statement.setString(2, taldea.getHerria());
            statement.setString(3, taldea.getZuzendaria());
            statement.setString(4, taldea.getTalde_izena());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Funtzio hau datu-baseko talde taulan talde bat ezatzeko da.
	 */
	public void deleteTaldea(String izena){
        String sql = "DELETE FROM taldea WHERE talde_izena = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
            statement.setString(1, izena);
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * Funtzio hau izenaren bitartez taldea sartuta dagoen da..
	 * @return boolean
	 */
	public boolean TaldeDBGaldetu(String izena){
        String sql = "SELECT 1 FROM taldea WHERE talde_izena = ?";
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
	/**
	 * Funtzio hau kodearekin taldea lortzeko da.
	 * @return Taldea.
	 */
	public Taldea getTaldeaKodearekin(int kod) {
		Taldea taldea = new Taldea();
		String sql = "SELECT * FROM taldea WHERE talde_kod = ?";
		try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	statement.setInt(1, kod);
        	ResultSet resultSet = statement.executeQuery();
        	while (resultSet.next()) {
				taldea.setTalde_kod(resultSet.getInt("talde_kod"));
	            taldea.setTalde_izena(resultSet.getString("talde_izena"));
	            taldea.setHerria(resultSet.getString("herria"));
	            taldea.setZuzendaria(resultSet.getString("zuzendaria"));
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taldea;
	}
	/**
	 * Funtzio hau taldea objetua izenaren bitartez lortzeko da.
	 * @return Taldea
	 */
	public Taldea getTaldeaIzenarekin(String izena) {
		Taldea taldea = new Taldea();
		String sql = "SELECT * FROM taldea WHERE talde_izena = ?";
		try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	statement.setString(1, izena);
        	ResultSet resultSet = statement.executeQuery();
        	while (resultSet.next()) {
				taldea.setTalde_kod(resultSet.getInt("talde_kod"));
	            taldea.setTalde_izena(resultSet.getString("talde_izena"));
	            taldea.setHerria(resultSet.getString("herria"));
	            taldea.setZuzendaria(resultSet.getString("zuzendaria"));
            }
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taldea;
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
