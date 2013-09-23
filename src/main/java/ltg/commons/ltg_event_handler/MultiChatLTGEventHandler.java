/**
 * 
 */
package ltg.commons.ltg_event_handler;

import java.util.ArrayList;
import java.util.List;

import ltg.commons.SimpleXMPPClient;

import org.jivesoftware.smack.packet.Message;

import com.fasterxml.jackson.databind.JsonNode;

public class MultiChatLTGEventHandler extends LTGEventHandler {

	/**
	 * This constructor connects to an XMPP server but doesn't join any 
	 * chat room.
	 * 
	 * @param fullJid
	 * @param password
	 */
	public MultiChatLTGEventHandler(String fullJid, String password) {
		sc = new SimpleXMPPClient(fullJid, password);
	}


	/**
	 * This constructor connects to an XMPP server and joins
	 * a list of chat rooms.
	 * 
	 * @param fullJid
	 * @param password
	 * @param chatRooms
	 */
	public MultiChatLTGEventHandler(String fullJid, String password, List<String> chatRooms)  {
		sc = new SimpleXMPPClient(fullJid, password, chatRooms);
	}


	/**
	 * Registers a listener for a particular event or 
	 * a pattern specified using regular expressions. 
	 * 
	 * @param eventType the event or patter that will activate the listener
	 * @param listener the callback in charge of performing the action when the pattern is matched
	 */
	public synchronized void registerHandler(String eventType, MultiChatLTGEventListener listener) {
		super.registerHandler(eventType, listener);
	}


	protected synchronized void processEvent(Message m) {
		// Parse JSON
		LTGEvent event = null;
		try {
			event = LTGEvent.deserializeEvent(m.getBody());
		} catch (Exception e) {
			// Not JSON or wrong format, ignore and return
			return;
		}
		// Process event
		List<MultiChatLTGEventListener> els = new ArrayList<MultiChatLTGEventListener>();
		if (event!=null) {
			for (String eventSelector : listeners.keySet())
				if (event.getEventType().matches(eventSelector))
					els.add((MultiChatLTGEventListener) listeners.get(eventSelector));
			for (MultiChatLTGEventListener el : els)
				el.processEvent(m.getFrom().split("/")[0], event);
		}
	}


	/**
	 * Generates a public event that is either broadcasted (no destination)
	 * to a specific group chat or addressed to a specific 
	 * bot or client (destination set) within a specific group chat.
	 * 
	 * @param chatroom the event is generated in
	 * @param event 
	 */
	public void generateEvent(String chatroom, LTGEvent event) {
		sc.sendMUCMessage(chatroom, LTGEvent.serializeEvent(event));
	}


	/**
	 * Generates a public event that is broadcasted (no destination)
	 * to the whole group chat.
	 * 
	 * @param chatroom the event is generated in
	 * @param eventType
	 * @param payload
	 */
	public void generateEvent(String chatroom, String eventType, JsonNode payload) {
		generateEvent(chatroom, new LTGEvent(eventType, null, null, payload));
	}

}
