package main;

import messages.Messages;
import messages.VPNotify;
import messages.WPNotify;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Verzamelpiet extends UntypedActor {
	
	private ActorRef adminPiet;
	private String name;
	
	public Verzamelpiet(ActorRef adminPiet, String name) {
		this.adminPiet = adminPiet;
		this.name = name;
	}

	@Override
	public void preStart() throws Exception {
		// TODO Auto-generated method stub
//		super.preStart();
		System.out.println(getName() + " is gestart");
		collect();
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Messages) {
			Messages m = (Messages) message;
			switch (m) {
			case INVITE_VO:
				System.out.println(getName() + " is uitgenodigd door sinterklaas voor een verzameloverleg");
				getSender().tell(Messages.JOIN_VO, getSelf());
				break;
			case DONE:
				System.out.println(getName() + " is klaar met het overleg en gaat weer verzamelen");
				collect();
				break;
			default:
				break;
			}
		}
	}
	
	public void collect() {
		try {
			Thread.sleep((int)Math.random() * 1000);
			System.out.println(getName() + " meld zich voor overleg");
			adminPiet.tell(new VPNotify(getName()), getSelf());
		} catch(InterruptedException e) {}
	}
	
	public String getName() {
		return name;
	}
	
	

}
