package main;

import java.util.ArrayList;

import data.WerkpietKleur;
import messages.Messages;
import messages.WOMessage;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Sinterklaas extends UntypedActor {
	
	private ArrayList<ActorRef> overleg = new ArrayList<ActorRef>();
	private int overlegSize = 0;
	
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof WOMessage) {
			WOMessage m = (WOMessage) message;
			//het wordt een werkoverleg
			ArrayList<WerkpietKleur> wp = (m.getWerkpieten());
			System.out.println(wp);
			overlegSize = wp.size();
			for (WerkpietKleur werkpietKleur : wp) {
				System.out.println("Sinterklaas nodigt " + werkpietKleur.getName() + " uit voor een werkoverleg");
				werkpietKleur.getWpActor().tell(Messages.INVITE_WO, getSelf());
			}
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
			default:
				break;
			}
		}
	}
	
	public void werkOverleg() {
		try {
			System.out.println("Start een werkoverleg");
			Thread.sleep((int)Math.random() * 10000);
			for (int i = 0; i < overleg.size(); i++) {
				overleg.get(i).tell(Messages.DONE, getSelf());
			}
		} catch(InterruptedException e) {}
	}
	
	public void verzamelOverleg() {
		try {
			System.out.println("start een verzameloverleg");
			Thread.sleep((int)Math.random() * 10000);
		} catch(InterruptedException e) {}
	}

}
