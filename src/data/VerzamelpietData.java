package data;

import akka.actor.ActorRef;

public class VerzamelpietData {
	
	private ActorRef wpActor;
	private String name;
	
	public VerzamelpietData(ActorRef actorRef, String name) {
		this.wpActor = actorRef;
		this.name = name;
	}

	public ActorRef getWpActor() {
		return wpActor;
	}

	public String getName() {
		return name;
	}
	
}
