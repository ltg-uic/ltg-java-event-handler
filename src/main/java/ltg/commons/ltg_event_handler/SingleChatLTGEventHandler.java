/**
 * 
 */
package ltg.commons.ltg_event_handler;

import java.util.ArrayList;
import java.util.List;

import ltg.commons.SimpleXMPPClient;

import org.jivesoftware.smack.packet.Message;

public class SingleChatLTGEventHandler extends LTGEventHandler {

	/**
	 * This constructor connects to an XMPP server but doesn't join any 
	 * chat room.
	 * 
	 * @param fullJid
	 * @param password
	 */
	public SingleChatLTGEventHandler(String fullJid, String password) {
		sc = new SimpleXMPPClient(fullJid, password);
	}

	
	/**
	 * This constructor connects to an XMPP server and joins
	 * a chat room.
	 * 
	 * @param fullJid
	 * @param password
	 * @param chatRoom
	 */
	public SingleChatLTGEventHandler(String fullJid, String password, String chatRoom)  {
		sc = new SimpleXMPPClient(fullJid, password, chatRoom);
	}

	
	/**
	 * Registers a listener for a particular event or 
	 * a pattern specified using regular expressions. 
	 * 
	 * @param eventType the event or patter that will activate the listener
	 * @param listener the callback in charge of performing the action when the pattern is matched
	 */
	public synchronized void registerHandler(String eventType, SingleChatLTGEventListener listener) {
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
		List<SingleChatLTGEventListener> els = new ArrayList<SingleChatLTGEventListener>();
		if (event!=null) {
			for (String eventSelector : listeners.keySet())
				if (event.getEventType().matches(eventSelector))
					els.add((SingleChatLTGEventListener) listeners.get(eventSelector));
			for (SingleChatLTGEventListener el : els)
				el.processEvent(event);
		}
	}

}
