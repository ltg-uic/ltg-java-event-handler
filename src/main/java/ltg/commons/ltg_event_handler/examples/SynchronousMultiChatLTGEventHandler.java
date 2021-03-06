/**
 * 
 */
package ltg.commons.ltg_event_handler.examples;

import java.util.ArrayList;
import java.util.List;

import ltg.commons.ltg_event_handler.LTGEvent;
import ltg.commons.ltg_event_handler.MultiChatLTGEventHandler;
import ltg.commons.ltg_event_handler.MultiChatLTGEventListener;

/**
 * @author tebemis
 *
 */
public class SynchronousMultiChatLTGEventHandler {

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
		eh.runSynchronously();
	}

}
