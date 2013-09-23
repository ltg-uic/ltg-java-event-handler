/**
 * 
 */
package ltg.commons.porcupine_event_handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ltg.commons.MessageListener;
import ltg.commons.SimpleXMPPClient;

import org.jivesoftware.smack.packet.Message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author tebemis
 *
 */
public class PorcupineEventHandler {

	private SimpleXMPPClient sc = null;
	private Map<String, PorcupineEventListener> listeners = new HashMap<String, PorcupineEventListener>();


	public PorcupineEventHandler(String fullJid, String password) {
		sc = new SimpleXMPPClient(fullJid, password);
	}


	public PorcupineEventHandler(String fullJid, String password, String chatRoom)  {
		sc = new SimpleXMPPClient(fullJid, password, chatRoom);
	}


	public void registerHandler(String eventType, PorcupineEventListener listener) {
		listeners.put(eventType, listener);
	}


	public void runSynchronously() {
		printRegisteredListeners();
		// We are now connected and in the group chat room. If we don't do something
		// the main thread will terminate. Let's go ahead and 
		// wait for a message to arrive...
		while (!Thread.currentThread().isInterrupted()) {
			// ... and process it ...
			processEvent(sc.nextMessage());
		}
		// ... and finally disconnect
		sc.disconnect();
	}


	public void runAsynchronously() {
		printRegisteredListeners();
		sc.registerEventListener(new MessageListener() {
			public void processMessage(Message m) {
				processEvent(m);
			}
		});
	}


	public void close() {
		sc.disconnect();
	}


	/**
	 * Generates a public event that is either broadcasted to the whole group chat
	 * or addressd to a specific agent or client.
	 * 
	 * @param e the event
	 */
	public void generateEvent(PorcupineEventFromWakeful e) {
		sc.sendMUCMessage(serializeEvent(e));
	}


	/**
	 * Generates a public event that is broadcasted to the whole group chat.
	 * 
	 * @param event
	 * @param payload
	 */
	public void generateEvent(String action, String url, JsonNode data) {
		generateEvent(new PorcupineEventFromWakeful(action, url, data));
	}


	/**
	 * Serializes a <code>PorcupineEventFromWakeful</code> object into JSON.
	 * 
	 * @param e
	 * @return
	 */
	public static String serializeEvent(PorcupineEventFromWakeful e) {
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("action", e.getAction());
		json.put("url", e.getURL());
		json.put("data", e.getData());
		return json.toString();
	}


	/**
	 * De-serializes JSON into a <code>PorcupineEventFromXMPP</code> object.
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws NotAPorcupineEventFromXMPPException
	 */
	public static PorcupineEventFromXMPP deserializeEvent(String json) throws IOException, NotAPorcupineEventFromXMPPException {
		// Parse JSON
		ObjectMapper jsonParser = new ObjectMapper();
		JsonNode jn = jsonParser.readTree(json);
		JsonNode jna = jn.path("action");
		// Parse the rest of the request
		String request = jn.path("request").textValue();
		String origin = jn.path("origin").textValue();
		String url = jn.path("url").textValue();
		JsonNode data = jn.path("data");
		// Discard actions and only deal with requests
		if (!(jna instanceof MissingNode))
			throw new NotAPorcupineEventFromXMPPException();
		// Validate data
		if(request==null || request.isEmpty()) 
			throw new NotAPorcupineEventFromXMPPException();
		if (origin==null || origin.isEmpty())
			throw new NotAPorcupineEventFromXMPPException();
		if (url==null || url.isEmpty())
			throw new NotAPorcupineEventFromXMPPException();
		if (data==null )
			throw new NotAPorcupineEventFromXMPPException();
		// Create and return event
		return new PorcupineEventFromXMPP(request, origin, url, data);
	}


	private void processEvent(Message m) {
		// Parse JSON
		PorcupineEventFromXMPP request = null;
		try {
			request = deserializeEvent(m.getBody());
		} catch (Exception e) {
			// Not JSON or wrong format, ignore and return
			return;
		}
		// Process event
		PorcupineEventListener el;
		if (request!=null) {
			el = listeners.get(request.getRequest());
			if (el!=null)
				el.processEvent(request);
		}
	}


	private void printRegisteredListeners() {
		String registeredListeners = " ";
		for (String s: listeners.keySet())
			registeredListeners = registeredListeners + s + ", ";
		if (registeredListeners.length()>3) {
			System.out.print("Listening for events of type [");
			System.out.print(registeredListeners.substring(0, registeredListeners.length()-2)+" ]\n");
		} else {
			System.out.print("Listening for events of type [ ]");
		}
	}

}
