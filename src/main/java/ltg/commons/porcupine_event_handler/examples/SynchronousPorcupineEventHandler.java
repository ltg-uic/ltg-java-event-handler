/**
 * 
 */
package ltg.commons.porcupine_event_handler.examples;

import ltg.commons.porcupine_event_handler.PorcupineEventFromXMPP;
import ltg.commons.porcupine_event_handler.PorcupineEventHandler;
import ltg.commons.porcupine_event_handler.PorcupineEventListener;

/**
 * @author tebemis
 *
 */
public class SynchronousPorcupineEventHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// We start by creating the event handler
		final PorcupineEventHandler eh = new PorcupineEventHandler("test@ltg.evl.uic.edu", "test", "test@conference.ltg.evl.uic.edu");

		// Then we can add all the listeners for messages like...
		// {
	    //	"request": "create",
	    //	"origin": "gugo",
	    //	"url": "/mydb/something/id",
	    //	"data": {
	        	//...
	    // 	}
	    // }
		eh.registerHandler("create", new PorcupineEventListener() {
			public void processEvent(PorcupineEventFromXMPP e) {
				System.out.println("Just received a CREATE request");
			}
		});

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runSynchronously();
	}

}
