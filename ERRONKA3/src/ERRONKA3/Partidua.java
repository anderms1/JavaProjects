package ERRONKA3;

import java.sql.Date;
import java.util.Objects;

public class Partidua {
	private	int partidua_kod;
	private Jardunaldia jardunaldia;
	private Denboraldia denboraldia;
	private Taldea etxeko_talde;
	private Taldea kanpoko_talde;
	private Date data;
	private int etxekoGolak;
	private int kanpokoGolak;
	
	public Partidua() {
		
	}

	public Partidua(int partidua_kod, Jardunaldia jardunaldia, Denboraldia denboraldia, Taldea etxeko_talde,
			Taldea kanpoko_talde, Date data, int etxekoGolak, int kanpokoGolak) {
		super();
		this.partidua_kod = partidua_kod;
		this.jardunaldia = jardunaldia;
		this.denboraldia = denboraldia;
		this.etxeko_talde = etxeko_talde;
		this.kanpoko_talde = kanpoko_talde;
		this.data = data;
		this.etxekoGolak = etxekoGolak;
		this.kanpokoGolak = kanpokoGolak;
	}

	public int getPartidua_kod() {
		return partidua_kod;
	}

	public void setPartidua_kod(int partidua_kod) {
		this.partidua_kod = partidua_kod;
	}

	public Jardunaldia getJardunaldia() {
		return jardunaldia;
	}

	public void setJardunaldia(Jardunaldia jardunaldia) {
		this.jardunaldia = jardunaldia;
	}

	public Denboraldia getDenboraldia() {
		return denboraldia;
	}

	public void setDenboraldia(Denboraldia denboraldia) {
		this.denboraldia = denboraldia;
	}

	public Taldea getEtxeko_talde() {
		return etxeko_talde;
	}

	public void setEtxeko_talde(Taldea etxeko_talde) {
		this.etxeko_talde = etxeko_talde;
	}

	public Taldea getKanpoko_talde() {
		return kanpoko_talde;
	}

	public void setKanpoko_talde(Taldea kanpoko_talde) {
		this.kanpoko_talde = kanpoko_talde;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getEtxekoGolak() {
		return etxekoGolak;
	}

	public void setEtxekoGolak(int etxekoGolak) {
		this.etxekoGolak = etxekoGolak;
	}

	public int getKanpokoGolak() {
		return kanpokoGolak;
	}

	public void setKanpokoGolak(int kanpokoGolak) {
		this.kanpokoGolak = kanpokoGolak;
	}

	@Override
	public String toString() {
		return "Partidua [partidua_kod=" + partidua_kod + ", jardunaldia=" + jardunaldia + ", denboraldia="
				+ denboraldia + ", etxeko_talde=" + etxeko_talde + ", kanpoko_talde=" + kanpoko_talde + ", data=" + data
				+ ", etxekoGolak=" + etxekoGolak + ", kanpokoGolak=" + kanpokoGolak + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, denboraldia, etxekoGolak, etxeko_talde, jardunaldia, kanpokoGolak, kanpoko_talde,
				partidua_kod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partidua other = (Partidua) obj;
		return Objects.equals(data, other.data) && Objects.equals(denboraldia, other.denboraldia)
				&& etxekoGolak == other.etxekoGolak && Objects.equals(etxeko_talde, other.etxeko_talde)
				&& Objects.equals(jardunaldia, other.jardunaldia) && kanpokoGolak == other.kanpokoGolak
				&& Objects.equals(kanpoko_talde, other.kanpoko_talde) && partidua_kod == other.partidua_kod;
	}
	
}
