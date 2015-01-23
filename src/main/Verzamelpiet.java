package main;

import akka.actor.UntypedActor;

public class Verzamelpiet extends UntypedActor {

	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void collect() {
		try {
			System.out.println();
			Thread.sleep((int)Math.random() * 1000);
		} catch(InterruptedException e) {}
	}

}
