package messages;

import java.util.ArrayList;

import data.VerzamelpietData;
import data.WerkpietData;

public final class VOMessage {

	private ArrayList<VerzamelpietData> verzamelpieten;
	private WerkpietData werkpiet;
	
	public VOMessage(ArrayList<VerzamelpietData> verzamelpieten, WerkpietData werkpiet) {
		this.verzamelpieten = verzamelpieten;
		this.werkpiet = werkpiet;
	}

	public ArrayList<VerzamelpietData> getVerzamelpieten() {
		return verzamelpieten;
	}
	
	public WerkpietData getWerkpiet() {
		return werkpiet;
	}
}
