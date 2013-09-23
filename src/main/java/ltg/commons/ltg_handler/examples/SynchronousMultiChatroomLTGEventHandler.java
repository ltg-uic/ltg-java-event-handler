/**
 * 
 */
package ltg.commons.ltg_handler.examples;

import java.util.ArrayList;
import java.util.List;

import ltg.commons.ltg_handler.LTGEvent;
import ltg.commons.ltg_handler.MultiLTGEventHandler;
import ltg.commons.ltg_handler.MultiLTGEventListener;

/**
 * @author tebemis
 *
 */
public class SynchronousMultiChatroomLTGEventHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// We start by creating the event handler
		List<String> chatrooms = new ArrayList<String>();
		chatrooms.add("test-room-1@conference.ltg.evl.uic.edu");
		chatrooms.add("test-room-2@conference.ltg.evl.uic.edu");
		final MultiLTGEventHandler eh = new MultiLTGEventHandler("test-bot@ltg.evl.uic.edu", "test-bot", chatrooms);

		// Then we can add all the listeners.
		// Here we add an echo for a simple event with no payload
		// {"event": "event_a", "payload": {} }
		eh.registerHandler("test_event", new MultiLTGEventListener() {
			public void processEvent(String chatroom, LTGEvent e) { 
				System.out.println("Received event " + e.getEventType() + " from " + chatroom);
			}
		});		

		// Once we are done registering the listeners we can actually start 
		// to listen for events and handle them 
		eh.runSynchronously();
	}

}
