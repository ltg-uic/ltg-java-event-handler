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
		LTGEventHandler eh = new LTGEventHandler("fg-master@ltg.evl.uic.edu", "fg-master", "fg-pilot-oct12@conference.ltg.evl.uic.edu");

		// Then we can add all the listeners
		eh.registerHandler("event_a", new LTGEventListener() {
			public void processEvent(LTGEvent e) {
				// Process this event with empty payload
				e.getDestination();
			}
		});		

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runSynchronously();


		// That's it!

		// To generate an event, use one of the following methods
		//eh.generateEvent(event)
		//eh.generatePrivateEvent(event);
	}

}
