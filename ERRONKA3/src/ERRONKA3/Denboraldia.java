package ERRONKA3;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

import ERRONKA3.Jardunaldia;

public class Denboraldia {
	private int denboraldia_kod;
	private Date hasierako_data;
	private Date amierako_data;
	private ArrayList<Jardunaldia> jardunaldiak = new ArrayList<Jardunaldia>();
	
	public Denboraldia() {
		
	}

	public Denboraldia(int denboraldia_kod, Date hasierako_data, Date amierako_data,
			ArrayList<Jardunaldia> jardunaldiak) {
		super();
		this.denboraldia_kod = denboraldia_kod;
		this.hasierako_data = hasierako_data;
		this.amierako_data = amierako_data;
		this.jardunaldiak = jardunaldiak;
	}

	public int getDenboraldia_kod() {
		return denboraldia_kod;
	}

	public void setDenboraldia_kod(int denboraldia_kod) {
		this.denboraldia_kod = denboraldia_kod;
	}

	public Date getHasierako_data() {
		return hasierako_data;
	}

	public void setHasierako_data(Date hasierako_data) {
		this.hasierako_data = hasierako_data;
	}

	public Date getAmierako_data() {
		return amierako_data;
	}

	public void setAmierako_data(Date amierako_data) {
		this.amierako_data = amierako_data;
	}

	public ArrayList<Jardunaldia> getJardunaldiak() {
		return jardunaldiak;
	}

	public void setJardunaldiak(ArrayList<Jardunaldia> jardunaldiak) {
		this.jardunaldiak = jardunaldiak;
	}

	@Override
	public String toString() {
		return "Denboraldia [denboraldia_kod=" + denboraldia_kod + ", hasierako_data=" + hasierako_data
				+ ", amierako_data=" + amierako_data + ", jardunaldiak=" + jardunaldiak + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amierako_data, denboraldia_kod, hasierako_data, jardunaldiak);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Denboraldia other = (Denboraldia) obj;
		return Objects.equals(amierako_data, other.amierako_data) && denboraldia_kod == other.denboraldia_kod
				&& Objects.equals(hasierako_data, other.hasierako_data)
				&& Objects.equals(jardunaldiak, other.jardunaldiak);
	}
	
	public void gehituJardunaldiak(Jardunaldia jardunaldi) {
		this.jardunaldiak.add(jardunaldi);
	}
}
