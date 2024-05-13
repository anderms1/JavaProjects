package ERRONKA3.DAO;

import java.sql.Connection;
import java.util.Date;

import ERRONKA3.DAO.*;
import ERRONKA3.klaseak.Denboraldia;
import ERRONKA3.klaseak.Jardunaldia;
import ERRONKA3.klaseak.Jokalaria;
import ERRONKA3.klaseak.MySQL;
import ERRONKA3.klaseak.Partidua;
import ERRONKA3.klaseak.Taldea;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
/*
 * Datu-baseko denboraldi taulari kontsultak egiteko.
 */
public class DenboraldiaDAO {
	private Connection konexioa;
	MySQL mysql = new MySQL();
	
	public DenboraldiaDAO() {
		/*konexioa = DriverManager.getConnection("jdbc:mysql://localhost/rugby", "root", "");*/
		konexioa = mysql.sqlConnect();
	}
	/**
	 * Funtzioa hau Datu basean denboraldi berri bat sartzeko da.
	 */
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
	/**
	 * Funtzio hau datu basean denboraldia bat berristatzeko da.
	 */
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
	/**
	 * Funtzio hau denboraldi bat urte bat duen jakiteko da.
	 * @return boolean
	 */
	public boolean DenboraldiDataGaldetu(int  year){
        String sql = "SELECT 1 FROM denboraldia WHERE YEAR(hasierako_data) = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	statement.setInt(1, year);
        	ResultSet resultSet = statement.executeQuery();

            // Si resultSet tiene al menos una fila, si hay nulo
            return resultSet.next();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
	/**
	 * Funtzio hau denb_taldea-n denboraldi eta talde jokatzen hari diren sartzeko da.
	 */
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
	/**
	 * Funtzio hau da denboraldi bat dagoen amaierako_data null dena.
	 */
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
	/**
	 * Funtzio hau denboraldi guztiak lortzen ditu eta arrayList baten gorde egiten ditu.
	 * @retun ArrayList <Denboraldia>
	 */
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
	/**
	 * Funtzio hau jokatzen hari den denboraldia arrayList batean gordetzen du, jokatzen hari diren denboraldia
	 * amierako_dat null dena da.
	 * @return ArrayList<Denboraldia>
	 */
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
	/**
	 * Funtzio hau datu-basean jardunaldi berri bat sartuko du.
	 */
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
	/**
	 * Funtzio honen bitartez, data emanez, bere jardunaldia_kod lortuko du.
	 */
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
	/**
	 * Funtzio honek datu-basean, partidua taulan, partidu berri bat sartuko du.
	 */
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
	/**
	 * Funtzio honen bitartez partiduak jokatu diren edo ez galdetu da, emaitza null bada es da jokatu.
	 * @return boolean
	 */
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
	/**
	 * Funtzio honen azken jardunaldiaren data lortzeko da, horrela denboraldiaren amaiera_data jarriko zaio.
	 * @return java.sql.Date
	 */
	public java.sql.Date azkenDataLortu(Denboraldia denboraldia) {
		String sql = "SELECT amaiera_data FROM jardunaldia WHERE denboraldia_kod = ? ORDER BY jardunaldia_kod DESC LIMIT 1;";
		try(PreparedStatement statement = konexioa.prepareStatement(sql)) {
           statement.setInt(1, denboraldia.getDenboraldia_kod());
           ResultSet resultSet = statement.executeQuery();
           if(resultSet.next()) {
        	   return resultSet.getDate("amaiera_data");
           }else {
        	   return null;
           }
           
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Funtzio honen bitartez jokalari guztiak lortuko dira bere taldea ere jarriz eta ArrayList batean 
	 * gordeko dira.
	 * @return ArrayList<Jokalaria>
	 */
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
			if (mysql.sqlConnect() != null && !mysql.sqlConnect().isClosed()) {
				mysql.sqlConnect().close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
