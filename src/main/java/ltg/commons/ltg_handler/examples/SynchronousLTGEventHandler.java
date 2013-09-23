/**
 * 
 */
package ltg.commons.ltg_handler.examples;

import ltg.commons.ltg_handler.LTGEvent;
import ltg.commons.ltg_handler.SingleLTGEventHandler;
import ltg.commons.ltg_handler.SingleLTGEventListener;

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
		final SingleLTGEventHandler eh = new SingleLTGEventHandler("test@ltg.evl.uic.edu", "test", "test-room@conference.ltg.evl.uic.edu");

		// Then we can add all the listeners.
		// Here we add an echo for a simple event with no payload
		// {"event": "event_a", "payload": {} }
		eh.registerHandler("event_a", new SingleLTGEventListener() {
			public void processEvent(LTGEvent e) {
				eh.generateEvent(e);
			}
		});		

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runSynchronously();
	}

}
