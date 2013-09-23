/**
 * 
 */
package ltg.commons.ltg_handler;

import java.util.ArrayList;
import java.util.List;

import ltg.commons.SimpleXMPPClient;

import org.jivesoftware.smack.packet.Message;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author tebemis
 *
 */
public class MultiLTGEventHandler extends LTGEventHandler {

	/**
	 * TODO
	 * 
	 * @param fullJid
	 * @param password
	 */
	public MultiLTGEventHandler(String fullJid, String password) {
		sc = new SimpleXMPPClient(fullJid, password);
	}


	/**
	 * TODO
	 * 
	 * @param fullJid
	 * @param password
	 * @param chatRooms
	 */
	public MultiLTGEventHandler(String fullJid, String password, List<String> chatRooms)  {
		sc = new SimpleXMPPClient(fullJid, password, chatRooms);
	}


	/**
	 * TODO
	 * 
	 * @param eventType
	 * @param listener
	 */
	public synchronized void registerHandler(String eventType, MultiLTGEventListener listener) {
		super.registerHandler(eventType, listener);
	}


	/**
	 * TODO
	 */
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
		List<MultiLTGEventListener> els = new ArrayList<MultiLTGEventListener>();
		if (event!=null) {
			for (String eventSelector : listeners.keySet())
				if (event.getEventType().matches(eventSelector))
					els.add((MultiLTGEventListener) listeners.get(eventSelector));
			for (MultiLTGEventListener el : els)
				el.processEvent(m.getFrom().split("/")[0], event);
		}
	}


	/**
	 * Generates a public event within a specific group chat. 
	 * The event can be addressed to a specific client
	 * or simply a broadcast.
	 * 
	 * @param chatroom the chatroom the event is generated in
	 * @param e the event
	 */
	public void generateEvent(String chatroom, LTGEvent e) {
		sc.sendMUCMessage(chatroom, LTGEvent.serializeEvent(e));
	}


	/**
	 * Generates a public event that is broadcasted to a 
	 * specific chat room.
	 * 
	 * @param chatroom the chatroom the event is generated in
	 * @param event
	 * @param payload
	 */
	public void generateEvent(String chatroom, String event, JsonNode payload) {
		generateEvent(chatroom, new LTGEvent(event, null, null, payload));
	}

}
