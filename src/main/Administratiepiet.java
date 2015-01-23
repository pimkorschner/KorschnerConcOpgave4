package main;

import java.util.ArrayList;

import data.WerkpietKleur;
import messages.Messages;
import messages.WOMessage;
import messages.WPNotify;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Administratiepiet extends UntypedActor {
	
	private ActorRef sinterklaas;
	private ArrayList<WerkpietKleur> werkpieten = new ArrayList<WerkpietKleur>();
	private ArrayList<Verzamelpiet> verzamelpieten;
	private boolean overlegBezig = false;
	
	public Administratiepiet(ActorRef sinterklaas) {
		this.sinterklaas = sinterklaas;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if ( message instanceof Messages) {
			Messages m = (Messages) message;
			switch (m) {
			case NOTIFY :
					
				System.out.println(getSender());
				
//				pongCount++;
//				if (pongCount % 1000 == 0){
//					System.out.println("Pong:ping"+ pongCount);
//				}
//				getSender().tell(Message.PONG, getSelf());
//				
				break;
			case INVITE :
				System.out.println("Should stop now");
				getContext().stop(getSelf());
			}
		} else if( message instanceof WPNotify) {
			WPNotify m = (WPNotify) message;
			//kijken of er al een overleg bezig is, anders werkpiet weer aan het werk zetten
			if(!overlegBezig) {
				//eerst de werkpiet met kleur aan de lijst van werkpieten toevoegen.
				werkpieten.add(new WerkpietKleur(getSender(), m.getName(), (m.getKleur())));
				
				//nu gaan kijken of er een overleg plaats kan vinden
				PlanOverleg();
			} else {
				//werkpiet weer aan het werk zetten
				getSender().tell(Messages.BUSY, getSelf());
			}
		}
		
	}
	
	public void PlanOverleg() {
		//eerst voor een verzameloverleg kijken maar nu eerst werkpieten integreren.
		if(werkpieten.size() == 3) {
			System.out.println("GENOEG WERKPIETEN VOOR WEKROVERLEG");
			//de eerste 3 werkpieten naar het overleg laten gaan, de rest weer aan het werk sturen
	/*		for (int i = 3; i < werkpieten.size(); i++) {
				werkpieten.get(i).getWpActor().tell(Messages.BUSY, getSelf());
				werkpieten.remove(i);
			}*/
			overlegBezig = true;
			sinterklaas.tell(new WOMessage((ArrayList<WerkpietKleur>) werkpieten.clone()), getSelf());
		}
	}

}
