/**
 * 
 */
package ltg.commons.ltg_event_handler.examples;

import ltg.commons.ltg_event_handler.LTGEvent;
import ltg.commons.ltg_event_handler.SingleChatLTGEventHandler;
import ltg.commons.ltg_event_handler.SingleChatLTGEventListener;

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
		final SingleChatLTGEventHandler eh = new SingleChatLTGEventHandler("test@ltg.evl.uic.edu", "test", "test-room@conference.ltg.evl.uic.edu");

		// Then we can add all the listeners.
		// Here we add one for a simple event with no payload
		// {"event": "test_event", "payload": {} }
		eh.registerHandler("test_event", new SingleChatLTGEventListener() {
			public void processEvent(LTGEvent e) {
				System.out.println("Received event " + e.getEventType());
			}
		});		

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runSynchronously();
	}

}
