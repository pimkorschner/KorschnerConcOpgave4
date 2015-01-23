package messages;

import java.util.ArrayList;

import data.WerkpietData;
import main.Werkpiet;

public final class WOMessage {
	
	private ArrayList<WerkpietData> werkpieten;
	
	public WOMessage(ArrayList<WerkpietData> werkpieten) {
		this.werkpieten = werkpieten;
	}

	public ArrayList<WerkpietData> getWerkpieten() {
		return werkpieten;
	}

}
