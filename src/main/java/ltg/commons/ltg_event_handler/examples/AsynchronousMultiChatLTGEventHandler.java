/**
 * 
 */
package ltg.commons.ltg_event_handler.examples;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import ltg.commons.ltg_event_handler.LTGEvent;
import ltg.commons.ltg_event_handler.MultiChatLTGEventHandler;
import ltg.commons.ltg_event_handler.MultiChatLTGEventListener;

/**
 * @author tebemis
 *
 */
public class AsynchronousMultiChatLTGEventHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// We start by creating the event handler
		List<String> chatrooms = new ArrayList<String>();
		chatrooms.add("test-room-1@conference.ltg.evl.uic.edu");
		chatrooms.add("test-room-2@conference.ltg.evl.uic.edu");
		final MultiChatLTGEventHandler eh = new MultiChatLTGEventHandler("test-bot@ltg.evl.uic.edu", "test-bot", chatrooms);

		// Then we can add all the listeners.
		// Here we add one for a simple event with no payload
		// {"event": "test_event", "payload": {} }
		eh.registerHandler("test_event", new MultiChatLTGEventListener() {
			public void processEvent(String chatroom, LTGEvent e) { 
				System.out.println("Received event " + e.getEventType() + " from " + chatroom);
			}
		});				

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runAsynchronously();

		// Now we can simulate the main thread  and make ourselves busy
		while(true) {
			try {
				eh.generateEvent("test-room-1@conference.ltg.evl.uic.edu", "test_event", JsonNodeFactory.instance.objectNode());
				Thread.sleep(5000);
				eh.generateEvent("test-room-2@conference.ltg.evl.uic.edu", "test_event", JsonNodeFactory.instance.objectNode());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				eh.shutdown();
			}
		}

		// NOTE: unless the main thread is suspended we will 
		// never get to this point in the code 
	}

}
