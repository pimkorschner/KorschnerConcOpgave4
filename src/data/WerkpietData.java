package data;

import akka.actor.ActorRef;

public class WerkpietData {
	
	private ActorRef wpActor;
	private int kleur;
	private String name;
	
	public WerkpietData(ActorRef actorRef, String name,  int kleur) {
		this.wpActor = actorRef;
		this.name = name;
		this.kleur = kleur;
	}

	public ActorRef getWpActor() {
		return wpActor;
	}

	public int getKleur() {
		return kleur;
	}
	
	public String getName() {
		return name;
	}
	
}
