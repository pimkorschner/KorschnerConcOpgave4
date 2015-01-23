package main;

import java.util.ArrayList;

import data.VerzamelpietData;
import data.WerkpietData;
import messages.Messages;
import messages.VOMessage;
import messages.WOMessage;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Sinterklaas extends UntypedActor {
	
	private ArrayList<ActorRef> overleg = new ArrayList<ActorRef>();
	private int overlegSize = 0;
	private ActorRef adminpiet;
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof WOMessage) {
			WOMessage m = (WOMessage) message;
			
			//kijk of adminpiet al bekend is
			if(adminpiet == null) {
				adminpiet = getSender();
			}
			
			//het wordt een werkoverleg
			ArrayList<WerkpietData> wp = (m.getWerkpieten());
			System.out.println(wp);
			overlegSize = wp.size();
			for (WerkpietData werkpietKleur : wp) {
				System.out.println("Sinterklaas nodigt " + werkpietKleur.getName() + " uit voor een werkoverleg");
				werkpietKleur.getWpActor().tell(Messages.INVITE_WO, getSelf());
			}
		} else if(message instanceof VOMessage) {
			VOMessage m = (VOMessage) message;
			
			//adminpiet check
			if(adminpiet == null) {
				adminpiet = getSender();
			}
			
			//het wordt een verzameloverleg
			ArrayList<VerzamelpietData> vp = m.getVerzamelpieten();
			WerkpietData wp = m.getWerkpiet();
			
			overlegSize = vp.size() + 1; //+1 voor de zwarte werkpiet
			for(VerzamelpietData vpd : vp) {
				System.out.println("Sinterklaas nodigt " + vpd.getName() + " uit voor een verzameloverleg");
				vpd.getWpActor().tell(Messages.INVITE_VO, getSelf());
			}
			System.out.println("Sinterklaas nodigt " + wp.getName() + " uit voor een verzameloverleg");
			wp.getWpActor().tell(Messages.INVITE_VO, getSelf());
			
			
		} else if(message instanceof Messages) {
			Messages m = (Messages) message;
			switch (m) {
			case JOIN_WO:
				//piet meld zich voor werk_overleg
				overleg.add(getSender());
				if(overleg.size() == overlegSize) {
					//iedere piet heeft zich gemeld, overleg kan beginnen
					werkOverleg();
				}
				break;
			case JOIN_VO:
				overleg.add(getSender());
				if(overleg.size() == overlegSize) {
					verzamelOverleg();
				}
				break;
			default:
				break;
			}
		}
	}
	
	public void werkOverleg() {
		try {
			System.out.println("Start een werkoverleg");
			Thread.sleep((int)Math.random() * 10000);
			//vertel de verzamelpieten dat het overleg klaar is
			for (int i = 0; i < overleg.size(); i++) {
				overleg.get(i).tell(Messages.DONE, getSelf());
			}
			overleg.clear();
			//vertel de adminPiet dat het overleg klaar is
			adminpiet.tell(Messages.DONE, getSelf());
		} catch(InterruptedException e) {}
	}
	
	public void verzamelOverleg() {
		try {
			System.out.println("start een verzameloverleg");
			Thread.sleep((int)Math.random() * 10000);
			for (int i = 0; i < overleg.size(); i++) {
				overleg.get(i).tell(Messages.DONE, getSelf());
			}
			overleg.clear();
			//vertel de adminPiet dat het overleg klaar is
			adminpiet.tell(Messages.DONE, getSelf());
		} catch(InterruptedException e) {}
	}

}
