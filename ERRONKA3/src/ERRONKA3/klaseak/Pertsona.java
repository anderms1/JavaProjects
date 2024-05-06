package ERRONKA3.klaseak;

import java.io.Serializable;

public class Pertsona implements Serializable{
	private String izena;
	private static final long serialVersionUID = 1L;
	
	public Pertsona() {
		
	}

	public Pertsona(String izena) {
		super();
		this.izena = izena;
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	@Override
	public String toString() {
		return "Pertsona [izena=" + izena + "]";
	}
	
	
}
