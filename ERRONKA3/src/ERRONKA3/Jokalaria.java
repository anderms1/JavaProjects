package ERRONKA3;

public class Jokalaria extends Pertsona{
	private static final long serialVersionUID = 1L;
	private int jokalaria_kod;
	private int dorsala;
	private String posizioa;
	private Taldea taldea;
	
	public Jokalaria() {
		
	}

	public Jokalaria(int jokalaria_kod, int dorsala, String posizioa, Taldea taldea) {
		super();
		this.jokalaria_kod = jokalaria_kod;
		this.dorsala = dorsala;
		this.posizioa = posizioa;
		this.taldea = taldea;
	}

	public int getJokalaria_kod() {
		return jokalaria_kod;
	}
	
	public void setJokalaria_kod(int jokalaria_kod) {
		this.jokalaria_kod = jokalaria_kod;
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
		return "Jokalaria [jokalaria_kod="+ jokalaria_kod + ", dorsala=" + dorsala + ", posizioa=" + posizioa + ", taldea=" + taldea + "]";
	}
	
	
}
