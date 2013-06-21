/**
 * 
 */
package ltg.commons.porcupine_handler.examples;

import ltg.commons.porcupine_handler.PorcupineEventHandler;
import ltg.commons.porcupine_handler.PorcupineEvent;
import ltg.commons.porcupine_handler.PorcupineEventListener;

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
		final PorcupineEventHandler eh = new PorcupineEventHandler("gugo@glint", "gugo", "porcupine@conference.glint");

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
			public void processEvent(PorcupineEvent e) {
				System.out.println("Just received a CREATE request");
			}
		});
		
		eh.registerHandler("replace", new PorcupineEventListener() {
			public void processEvent(PorcupineEvent e) {
				System.out.println("Just received a REPLACE request");
			}
		});
		
		eh.registerHandler("patch", new PorcupineEventListener() {
			public void processEvent(PorcupineEvent e) {
				System.out.println("Just received a PATCH request");
			}
		});
		
		eh.registerHandler("delete", new PorcupineEventListener() {
			public void processEvent(PorcupineEvent e) {
				System.out.println("Just received a DELETE request");
			}
		});
		
		eh.registerHandler("subscribe", new PorcupineEventListener() {
			public void processEvent(PorcupineEvent e) {
				System.out.println("Just received a SUBSCRIBE request");
			}
		});

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runSynchronously();
	}

}
