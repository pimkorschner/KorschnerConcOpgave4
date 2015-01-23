package messages;

public final class WPNotify {

	private int kleur;
	private String name;
	
	public WPNotify(String name, int kleur) {
		this.name = name;
		this.kleur = kleur;
	}
	
	public int getKleur() {
		return kleur;
	}
	
	public String getName() {
		return name;
	}
	
}
