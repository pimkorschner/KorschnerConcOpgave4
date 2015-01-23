package main;

import java.util.ArrayList;

import data.WerkpietKleur;
import messages.Messages;
import messages.WOMessage;
import akka.actor.UntypedActor;

public class Sinterklaas extends UntypedActor {
	
	private boolean overlegBezig = false;

	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof WOMessage) {
			WOMessage m = (WOMessage) message;
			overlegBezig = true;
			//het wordt een werkoverleg
			ArrayList<WerkpietKleur> wp = (m.getWerkpieten());
			System.out.println(wp);
			for (WerkpietKleur werkpietKleur : wp) {
				System.out.println("Sinterklaas " + werkpietKleur.getName());
				werkpietKleur.getWpActor().tell(Messages.INVITE, getSelf());
			}
		} else if(message instanceof Messages) {
			Messages m = (Messages) message;
			switch (m) {
			case JOIN:
				
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
			overlegBezig = false;
		} catch(InterruptedException e) {}
	}
	
	public void verzamelOverleg() {
		try {
			System.out.println("start een verzameloverleg");
			Thread.sleep((int)Math.random() * 10000);
		} catch(InterruptedException e) {}
	}

}
