package ERRONKA3.klaseak;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

import ERRONKA3.klaseak.Partidua;
/**
 * Jardunaldi klasea jardunaldi taulatik hartzen ditu dtuak eta gordetzen dira gero erabil ahal izateko, denboraldiaren bitartez
 * hartzen dira jardunaldiak.
 */
public class Jardunaldia {
	private int jardunaldia_kod;
	private Denboraldia denboraldia_kod;
	private Date hasierako_data;
	private Date amaierako_data;
	private ArrayList<Partidua> partiduak;
	
	public Jardunaldia() {
		this.partiduak = new ArrayList<Partidua>();
	}

	
	
	public Jardunaldia(int jardunaldia_kod, Denboraldia denboraldia_kod, Date hasierako_data, Date amierako_data,
			ArrayList<Partidua> partiduak) {
		super();
		this.jardunaldia_kod = jardunaldia_kod;
		this.denboraldia_kod = denboraldia_kod;
		this.hasierako_data = hasierako_data;
		this.amaierako_data = amierako_data;
		this.partiduak = new ArrayList<>();
	}

	public int getJardunaldia_kod() {
		return jardunaldia_kod;
	}

	public void setJardunaldia_kod(int jardunaldia_kod) {
		this.jardunaldia_kod = jardunaldia_kod;
	}

	public Denboraldia getDenboraldia_kod() {
		return denboraldia_kod;
	}

	public void setDenboraldia_kod(Denboraldia denboraldia_kod) {
		this.denboraldia_kod = denboraldia_kod;
	}

	public Date getHasierako_data() {
		return hasierako_data;
	}

	public void setHasierako_data(Date hasierako_data) {
		this.hasierako_data = hasierako_data;
	}

	public Date getAmaierako_data() {
		return amaierako_data;
	}

	public void setAmaierako_data(Date amierako_data) {
		this.amaierako_data = amierako_data;
	}

	public ArrayList<Partidua> getPartiduak() {
		return partiduak;
	}

	public void setPartiduak(ArrayList<Partidua> partiduak) {
		this.partiduak = partiduak;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amaierako_data, denboraldia_kod, hasierako_data, jardunaldia_kod, partiduak);
	}
	
	@Override
	public String toString() {
		return "Jardunaldia [jardunaldia_kod=" + jardunaldia_kod + ", denboraldia_kod=" + denboraldia_kod
				+ ", hasierako_data=" + hasierako_data + ", amierako_data=" + amaierako_data + ", partiduak=" + partiduak
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jardunaldia other = (Jardunaldia) obj;
		return Objects.equals(amaierako_data, other.amaierako_data)
				&& Objects.equals(denboraldia_kod, other.denboraldia_kod)
				&& Objects.equals(hasierako_data, other.hasierako_data) && jardunaldia_kod == other.jardunaldia_kod
				&& Objects.equals(partiduak, other.partiduak);
	}

	public void gehituPartidua(Partidua partidua) {
		this.partiduak.add(partidua);
	}
}
