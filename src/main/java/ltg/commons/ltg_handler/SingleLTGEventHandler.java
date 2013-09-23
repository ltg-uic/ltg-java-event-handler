/**
 * 
 */
package ltg.commons.ltg_handler;

import java.util.ArrayList;
import java.util.List;

import ltg.commons.SimpleXMPPClient;

import org.jivesoftware.smack.packet.Message;

/**
 * @author tebemis
 *
 */
public class SingleLTGEventHandler extends LTGEventHandler {

	/**
	 * TODO
	 * 
	 * @param fullJid
	 * @param password
	 */
	public SingleLTGEventHandler(String fullJid, String password) {
		sc = new SimpleXMPPClient(fullJid, password);
	}

	
	/**
	 * TODO
	 * 
	 * @param fullJid
	 * @param password
	 * @param chatRoom
	 */
	public SingleLTGEventHandler(String fullJid, String password, String chatRoom)  {
		sc = new SimpleXMPPClient(fullJid, password, chatRoom);
	}

	
	/**
	 * TODO
	 * 
	 * @param eventType
	 * @param listener
	 */
	public synchronized void registerHandler(String eventType, SingleLTGEventListener listener) {
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
		List<SingleLTGEventListener> els = new ArrayList<SingleLTGEventListener>();
		if (event!=null) {
			for (String eventSelector : listeners.keySet())
				if (event.getEventType().matches(eventSelector))
					els.add((SingleLTGEventListener) listeners.get(eventSelector));
			for (SingleLTGEventListener el : els)
				el.processEvent(event);
		}
	}

}
