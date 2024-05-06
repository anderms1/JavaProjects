package ERRONKA3;

import java.util.ArrayList;
import java.util.Objects;

public class Taldea {
	private int talde_kod;
	private String talde_izena;
	private String herria;
	private String zuzendaria;
	private int puntuak;
	private int wins;
	private int defeats;
	private int ties;
	private ArrayList<Jokalaria> jokalariak = new ArrayList<Jokalaria>();
	
	public Taldea() {
		
	}

	public Taldea(int talde_kod, String talde_izena, String herria, String zuzendaria, int puntuak, int wins, int defeats, int ties, ArrayList<Jokalaria> jokalariak) {
		super();
		this.talde_kod = talde_kod;
		this.talde_izena = talde_izena;
		this.herria = herria;
		this.zuzendaria = zuzendaria;
		this.puntuak = puntuak;
		this.wins = wins;
		this.defeats = defeats;
		this.ties = ties;
		this.jokalariak = jokalariak;
	}
	
	public int getTalde_kod() {
		return talde_kod;
	}

	public void setTalde_kod(int talde_kod) {
		this.talde_kod = talde_kod;
	}
	
	public String getTalde_izena() {
		return talde_izena;
	}

	public void setTalde_izena(String talde_izena) {
		this.talde_izena = talde_izena;
	}

	public String getHerria() {
		return herria;
	}

	public void setHerria(String herria) {
		this.herria = herria;
	}

	public String getZuzendaria() {
		return zuzendaria;
	}

	public void setZuzendaria(String zuzendaria) {
		this.zuzendaria = zuzendaria;
	}

	public int getPuntuak() {
		return puntuak;
	}

	public void setPuntuak(int puntuak) {
		this.puntuak = puntuak;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getDefeats() {
		return defeats;
	}

	public void setDefeats(int defeats) {
		this.defeats = defeats;
	}

	public int getTies() {
		return ties;
	}

	public void setTies(int ties) {
		this.ties = ties;
	}
	
	public ArrayList<Jokalaria> getJokalariak(){
		return jokalariak;
	}
	
	public void setJokalariak(ArrayList<Jokalaria> jokalariak) {
		this.jokalariak = jokalariak;
	}

	public void gehituWins(){
		this.wins += 1;
		this.puntuak += 4;
	}
	
	public void gehituDefeats() {
		this.defeats += 1;
	}
	
	public void gehituTies() {
		this.ties += 1;
		this.puntuak += 2;
	}
	
	@Override
	public String toString() {
		return "Taldea [talde_kod=" + talde_kod + ",talde_izena=" + talde_izena + ", herria=" + herria + ", zuzendaria=" + zuzendaria + ", puntuak="
				+ puntuak + ", wins=" + wins + ", defeats=" + defeats + ", ties=" + ties + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(defeats, herria, puntuak, talde_izena, talde_kod, ties, wins, zuzendaria);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Taldea other = (Taldea) obj;
		return defeats == other.defeats && Objects.equals(herria, other.herria) && puntuak == other.puntuak
				&& Objects.equals(talde_izena, other.talde_izena) && talde_kod == other.talde_kod && ties == other.ties
				&& wins == other.wins && Objects.equals(zuzendaria, other.zuzendaria);
	}
	
	public void gehituJokalaria(Jokalaria jokalaria) {
		this.jokalariak.add(jokalaria);
	}
}
