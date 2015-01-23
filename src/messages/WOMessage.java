package messages;

import java.util.ArrayList;

import data.WerkpietKleur;
import main.Werkpiet;

public final class WOMessage {
	
	private ArrayList<WerkpietKleur> werkpieten;
	
	public WOMessage(ArrayList<WerkpietKleur> werkpieten) {
		this.werkpieten = werkpieten;
	}

	public ArrayList<WerkpietKleur> getWerkpieten() {
		return werkpieten;
	}
	

}
