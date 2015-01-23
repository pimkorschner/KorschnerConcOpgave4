package main;

import scala.reflect.internal.Trees.CaseDef;
import messages.Messages;
import messages.WPNotify;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.ReliableProxy.Message;

public class Werkpiet extends UntypedActor {

	private ActorRef adminPiet;
	private String name;
	private int kleur;
	
	public Werkpiet(ActorRef adminPiet, String name, int kleur) {
		this.name = name;
		this.adminPiet = adminPiet;
		this.kleur = kleur;
	}
	
	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
//		super.preStart();
		System.out.println(getName() + " is gestart en is: " + kleur);
		work();
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		// TODO Auto-generated method stub
		if(message instanceof Messages) {
			Messages m = (Messages) message;
			switch (m) {
			case BUSY:
//				System.out.println(getName() + " gaat weer aan het werk");
				work();
				break;
			case INVITE_WO:
				System.out.println(getName() + " is uitgenodigd door sinterklaas voor een werkoverleg");
				getSender().tell(Messages.JOIN_WO, getSelf());
				break;
			case INVITE_VO:
				System.out.println(getName() + " is uitgenodigd door sinterklaas voor een verzameloverleg");
				getSender().tell(Messages.JOIN_VO, getSelf());
				break;
			case DONE:
				System.out.println(getName() + " is klaar met het overleg en gaat weer werken");
				work();
				break;
			default:
				break;
			}
		}
	}
	
	public void work() {
		try {
			Thread.sleep((int)Math.random() * 10000);
//			System.out.println(getName() + " meld zich voor overleg");
			adminPiet.tell(new WPNotify(getName(), kleur), getSelf());
		} catch(InterruptedException e) {}
	}
	
	public String getName() {
		return name;
	}

}
