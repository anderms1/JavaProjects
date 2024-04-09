package ERRONKA3;

public class Taldea {
	private String talde_izena;
	private String herria;
	private String zuzendaria;
	private int puntuak;
	private int wins;
	private int defeats;
	private int ties;
	
	public Taldea() {
		
	}

	public Taldea(String talde_izena, String herria, String zuzendaria, int puntuak, int wins, int defeats, int ties) {
		super();
		this.talde_izena = talde_izena;
		this.herria = herria;
		this.zuzendaria = zuzendaria;
		this.puntuak = puntuak;
		this.wins = wins;
		this.defeats = defeats;
		this.ties = ties;
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

	@Override
	public String toString() {
		return "Taldea [talde_izena=" + talde_izena + ", herria=" + herria + ", zuzendaria=" + zuzendaria + ", puntuak="
				+ puntuak + ", wins=" + wins + ", defeats=" + defeats + ", ties=" + ties + "]";
	}
	
	
}
