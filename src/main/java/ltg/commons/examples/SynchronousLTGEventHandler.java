/**
 * 
 */
package ltg.commons.examples;

import ltg.commons.LTGEvent;
import ltg.commons.LTGEventHandler;
import ltg.commons.LTGEventListener;

/**
 * @author tebemis
 *
 */
public class SynchronousLTGEventHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// We start by creating the event handler
		final LTGEventHandler eh = new LTGEventHandler("fg-master@ltg.evl.uic.edu", "fg-master", "fg-pilot-oct12@conference.ltg.evl.uic.edu");

		// Then we can add all the listeners.
		// Here we add an echo for a simple event with no payload
		// {"event": "event_a", "payload": {} }
		eh.registerHandler("event_a", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				eh.generateEvent(e);
			}
		});		

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runSynchronously();
	}

}
