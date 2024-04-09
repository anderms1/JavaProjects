package ERRONKA3;

public class Jokalaria {
	private int dorsala;
	private String posizioa;
	private Taldea taldea;
	
	public Jokalaria() {
		
	}

	public Jokalaria(int dorsala, String posizioa, Taldea taldea) {
		super();
		this.dorsala = dorsala;
		this.posizioa = posizioa;
		this.taldea = taldea;
	}

	public int getDorsala() {
		return dorsala;
	}

	public void setDorsala(int dorsala) {
		this.dorsala = dorsala;
	}

	public String getPosizioa() {
		return posizioa;
	}

	public void setPosizioa(String posizioa) {
		this.posizioa = posizioa;
	}

	public Taldea getTaldea() {
		return taldea;
	}

	public void setTaldea(Taldea taldea) {
		this.taldea = taldea;
	}

	@Override
	public String toString() {
		return "Jokalaria [dorsala=" + dorsala + ", posizioa=" + posizioa + ", taldea=" + taldea + "]";
	}
	
	
}
