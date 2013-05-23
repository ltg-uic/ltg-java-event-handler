/**
 * 
 */
package ltg.commons.ltg_handler.examples;

import ltg.commons.ltg_handler.LTGEvent;
import ltg.commons.ltg_handler.LTGEventHandler;
import ltg.commons.ltg_handler.LTGEventListener;

/**
 * @author tebemis
 *
 */
public class AsynchronousLTGEventHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// We start by creating the event handler
		final LTGEventHandler eh = new LTGEventHandler("fg-master@ltg.evl.uic.edu", "fg-master", "fg-pilot-oct12@conference.ltg.evl.uic.edu");

		// Then we can add all the listeners.
		// Here we add one for a simple event with no payload
		// {"event": "event_a", "payload": {} }
		eh.registerHandler("event_a", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				eh.generateEvent(e);
			}
		});				

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runAsynchronously();

		// Now we can simulate the main thread  and make ourselves busy
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				eh.close();
			}
		}

		// NOTE: unless the main thread is suspended we will 
		// never get to this point in the code 
	}

}
