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
	
	public void updatePartidua(Partidua partidua) {
		String sql = "UPDATE partidua SET emaitza = ? WHERE partidua_kod = ? AND jardunaldia_kod = ? AND denboraldia_kod = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	String emaitza = partidua.getEtxekoGolak()+" - "+partidua.getKanpokoGolak();
            statement.setString(1, emaitza);
            statement.setInt(2, partidua.getPartidua_kod());
            statement.setInt(3, partidua.getJardunaldia().getJardunaldia_kod());
            statement.setInt(4, partidua.getDenboraldia().getDenboraldia_kod());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				String emaitza = resultSet.getString("emaitza");
				if(emaitza != null) {
					String regex = "[-]";
					String[] myArray = emaitza.split(regex);
					int EGolak = Integer.parseInt(myArray[0].trim());
					int KGolak = Integer.parseInt(myArray[1].trim());
					partidua.setEtxekoGolak(EGolak);
					partidua.setKanpokoGolak(KGolak);
				}
				partiduak.add(partidua);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		taldeaDao.deskonektatu();
		return partiduak;
	}
	
	public void updateDenb_taldea(Taldea taldea, Denboraldia denboraldia) {
		String sql = "UPDATE denb_taldea SET puntuak = puntuak + ?, irabaziak = irabaziak + ?, porrotak = porrotak + ?, berdinketak = berdinketak + ? WHERE denboraldia_kod = ? AND	talde_kod = ?";
        try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
            statement.setInt(1, taldea.getPuntuak());
            statement.setInt(2, taldea.getWins());
            statement.setInt(3, taldea.getDefeats());
            statement.setInt(4, taldea.getTies());
            statement.setInt(5, denboraldia.getDenboraldia_kod());
            statement.setInt(6, taldea.getTalde_kod());
            statement.executeUpdate();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean partiduaAmaitutaGaldetu(Partidua partidua) {
		String sql = "SELECT 1 FROM partidua WHERE partidua_kod = ? AND jardunaldia_kod = ? AND denboraldia_kod = ? AND emaitza IS NOT NULL";
		try (PreparedStatement statement = konexioa.prepareStatement(sql)) {
        	statement.setInt(1, partidua.getPartidua_kod());
        	statement.setInt(2, partidua.getJardunaldia().getJardunaldia_kod());
        	statement.setInt(3, partidua.getDenboraldia().getDenboraldia_kod());
        	ResultSet resultSet = statement.executeQuery();

            // Si resultSet tiene al menos una fila, el grupo existe
            return resultSet.next();
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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
