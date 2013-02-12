/**
 * 
 */
package ltg.commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.packet.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author tebemis
 *
 */
public class LTGEventHandler {

	private SimpleXMPPClient sc = null;
	private ObjectMapper jsonParser = new ObjectMapper();
	private Map<String, LTGEventListener> handlers = new HashMap<String, LTGEventListener>();


	public LTGEventHandler(String fullJid, String password) {
		sc = new SimpleXMPPClient(fullJid, password);
	}


	public LTGEventHandler(String fullJid, String password, String chatRoom)  {
		sc = new SimpleXMPPClient(fullJid, password, chatRoom);
	}


	public void registerHandler(String eventType, LTGEventListener listener) {
		handlers.put(eventType, listener);
	}


	public void runSynchronously() {
		// We are now connected and in the group chat room. If we don't do something
		// the main will terminate...
		// ... so let's go ahead and wait for a message to arrive...
		while (!Thread.currentThread().isInterrupted()) {
			// ... and process it ...
			processMessage(sc.nextMessage());
		}
		// ... and finally disconnect.
		sc.disconnect();
	}


	public void runAsynchronously() {
		sc.registerEventListener(new MessageListener() {
			public void processMessage(Message m) {
				processMessage(m);
			}
		});
	}
	
	
	public void close() {
		sc.disconnect();
	}


	public void generateEvent(LTGEvent e) {

	}


	public void generatePrivateEvent(LTGEvent e) {

	}
	
	
	private void processMessage(Message m) {
		// Parse JSON
		JsonNode json = null;
		try {
			json = jsonParser.readTree(m.getBody());
		} catch (JsonProcessingException e) {
			// Not JSON, ignore and return
			return;
		} catch (IOException e) {
			// Not JSON, ignore and return
			return;
		}
		// Event validation
		String event = json.path("event").textValue();
		String origin = json.path("origin").textValue();
		String destination = json.path("origin").textValue();
		JsonNode payload = json.path("payload");
		// Event processing
		LTGEventListener el = handlers.get(event);
		if (el==null)
			return;
		else
			el.processEvent(new LTGEvent(event, origin, destination, payload));
	}

}
