package main;

import java.util.ArrayList;

import data.VerzamelpietData;
import data.WerkpietData;
import messages.Messages;
import messages.VOMessage;
import messages.VPNotify;
import messages.WOMessage;
import messages.WPNotify;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Administratiepiet extends UntypedActor {
	
	private ActorRef sinterklaas;
	private ArrayList<WerkpietData> werkpieten = new ArrayList<WerkpietData>();
	private ArrayList<VerzamelpietData> verzamelpieten = new ArrayList<VerzamelpietData>();
	private boolean overlegBezig = false;
	
	public Administratiepiet(ActorRef sinterklaas) {
		this.sinterklaas = sinterklaas;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if ( message instanceof Messages) {
			Messages m = (Messages) message;
			switch (m) {
			case DONE :
				//van de sint het bericht gekregen dat hij klaar is met overleg
				System.out.println("Sinterklaas meld zich bij adminPiet dat het overleg klaar is");
				overlegBezig = false;	
				
				break;
			case INVITE_WO :
				break;
//				System.out.println("Should stop now");
//				getContext().stop(getSelf());
			}
		} else if( message instanceof WPNotify) {
			WPNotify m = (WPNotify) message;
			//kijken of er al een overleg bezig is, anders werkpiet weer aan het werk zetten
			if(!overlegBezig) {
				System.out.println(m.getName() + " heeft zich gemeld voor overleg");
				//eerst de werkpiet met kleur aan de lijst van werkpieten toevoegen.
				werkpieten.add(new WerkpietData(getSender(), m.getName(), (m.getKleur())));
				
				//nu gaan kijken of er een overleg plaats kan vinden
				PlanOverleg();
			} else {
				//werkpiet weer aan het werk zetten
				getSender().tell(Messages.BUSY, getSelf());
			}
		} else if( message instanceof VPNotify) {
			VPNotify m = (VPNotify) message;
			verzamelpieten.add(new VerzamelpietData(getSender(), m.getName()));
			PlanOverleg();
		}
		
	}
	
	public void PlanOverleg() {
		//eerst voor een verzameloverleg kijken maar nu eerst werkpieten integreren.
		if(verzamelpieten.size() >= 3) {
			//er zijn genoeg verzamelpieten, nu kijken of er een zwarte piet is. 
			if(!werkpieten.isEmpty()) {
				WerkpietData wp = null;
				for (WerkpietData temp : werkpieten) {
					if(temp.getKleur() == 0) {
						//deze piet is zwart, er kan dus een verzameloverleg beginnen
						System.out.println("GENOEG PIETEN VOOR VERZAMELOVERLEG");
						overlegBezig = true;
						wp = temp;
						werkpieten.remove(temp);
						break;
					}
				}
				if(wp != null) {
					VOMessage message = new VOMessage((ArrayList<VerzamelpietData>)verzamelpieten.clone(), wp);
					sinterklaas.tell(message, getSelf());
					verzamelpieten.clear();
					
					//overige werkpieten terug aan het werk
					for (int i = 0; i < werkpieten.size(); i++) {
						werkpieten.get(i).getWpActor().tell(Messages.BUSY, getSelf());
					}
					werkpieten.clear();
				}
			}
		}
		
		if(werkpieten.size() == 3) {
			System.out.println("GENOEG WERKPIETEN VOOR WEKROVERLEG");
			//de eerste 3 werkpieten naar het overleg laten gaan, de rest weer aan het werk sturen
			overlegBezig = true;
			sinterklaas.tell(new WOMessage((ArrayList<WerkpietData>) werkpieten.clone()), getSelf());
			werkpieten.clear();
		}
	}

}
