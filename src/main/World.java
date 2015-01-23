package main;

import org.jboss.netty.handler.codec.compression.ZlibWrapper;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class World {
	
	private static final int NR_WP = 10;
	private static final int NR_VP = 10;

	public static final int ZWART = 0, WIT = 1, ROOD = 2, BLAUW = 3, BRUIN = 4;

	public static void main(String[] args) {
		ActorSystem world = ActorSystem.create("world");
		
		ActorRef sinterklaas = world.actorOf(Props.create(Sinterklaas.class), "Sinterklaas");
		ActorRef adminPiet = world.actorOf(Props.create(Administratiepiet.class, sinterklaas), "AdminPiet");
		
		//eerste werkpiet altijd zwart maken
		world.actorOf(Props.create(Werkpiet.class, adminPiet, "wp" + 0, ZWART), "wp" + 0);
		//de rest kan een random kleur krijgen
		for (int i = 1; i < NR_WP; i++) {
			world.actorOf(Props.create(Werkpiet.class, adminPiet, "wp" + i, (int)(Math.random()*5)),"wp" + i);
		}
//		for (int i = 0; i < NR_VP; i++) {
//			ActorRef pong = world.actorOf(Props.create(Verzamelpiet.class),"wp" + i);
//		}
	}
	
}
