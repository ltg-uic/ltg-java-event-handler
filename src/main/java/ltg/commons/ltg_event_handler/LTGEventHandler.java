package ltg.commons.ltg_event_handler;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import ltg.commons.MessageListener;
import ltg.commons.SimpleXMPPClient;

import org.jivesoftware.smack.packet.Message;

import com.fasterxml.jackson.databind.JsonNode;

public abstract class LTGEventHandler {
	
	protected SimpleXMPPClient sc = null;
	// Event listeners
	protected Map<String, LTGEventListener> listeners = new HashMap<String, LTGEventListener>();
	
	
	/**
	 * Utility method that registers a listener for a particular event or
	 * a pattern specified using regular expressions. 
	 * This method is used by subclasses.
	 * 
	 * @param eventType the event or patter that will activate the listener
	 * @param listener the callback in charge of performing the action when the pattern is matched
	 */
	protected synchronized void registerHandler(String eventType, LTGEventListener listener) {
		try {
			Pattern.compile(eventType);
		} catch (PatternSyntaxException e) {
			System.out.println("Invalid event type. If you are writing a regular expression check your syntax. Terminating... ");
			System.exit(-1);
		}
		listeners.put(eventType, listener);
	}
	
	
	/**
	 * Starts the event listener in asynchronous mode.
	 * Whenever an event comes the current thread is interrupted
	 * and the event processed.
	 */
	public void runAsynchronously() {
		sc.registerEventListener(new MessageListener() {
			public void processMessage(Message m) {
				processEvent(m);
			}
		});
	}


	/**
	 * Starts the event listener in synchronous mode.
	 * All we do is put the main thread asleep until 
	 * a new message arrives and then process it.
	 */
	public void runSynchronously() {
		while (!Thread.currentThread().isInterrupted()) {
			// ... and process it ...
			processEvent(sc.nextMessage());
		}
		// Once we interrupt the main thread we can disconnect
		shutdown();
	}
	
	
	/**
	 * Prints all the events listeners that have been
	 * registered and are being handled by this handler.
	 */
	public synchronized void printRegisteredListeners() {
		String registeredListeners = " ";
		for (String s: listeners.keySet())
			registeredListeners = registeredListeners + s + ", ";
		if (registeredListeners.length()>3) {
			System.out.print("Listening for events of type [");
			System.out.print(registeredListeners.substring(0, registeredListeners.length()-2)+" ]\n");
		} else {
			System.out.print("Listening for events of type [ ]\n");
		}
	}
	
	
	/**
	 * Returns the ID of this event handler.
	 * 
	 * @return string representation of the ID
	 */
	public String getId() {
		return sc.getUsername();
	}
	
	
	/**
	 * Stops the event handler.
	 */
	public void shutdown() {
		sc.disconnect();
	}
	
	
	/**
	 * Generates a public event that is either broadcasted (no destination)
	 * to the whole group chat or addressed to a specific 
	 * bot or client (destination set).
	 * 
	 * @param event the event that will be generated
	 */
	public void generateEvent(LTGEvent event) {
		sc.sendMUCMessage(LTGEvent.serializeEvent(event));
	}
	
	
	/**
	 * Generates a public event that is broadcasted (no destination)
	 * to the whole group chat.
	 * 
	 * @param eventType 
	 * @param payload 
	 */
	public void generateEvent(String eventType, JsonNode payload) {
		generateEvent(new LTGEvent(eventType, null, null, payload));
	}
	

	/**
	 * Generates a private (point-to-point) event that is sent 
	 * to a specific bot or client, off the public group chat.
	 * 
	 * @param clientID
	 * @param event
	 */
	public void generatePrivateEvent(String clientID, LTGEvent event) {
		sc.sendMessage(clientID, LTGEvent.serializeEvent(event));
	}

	
	/**
	 * Generates a private (point-to-point) event that is sent 
	 * to a specific bot or client, off the public group chat.
	 * 
	 * @param clientID
	 * @param eventType
	 * @param payload
	 */
	public void generatePrivateEvent(String clientID, String eventType, JsonNode payload) {
		generatePrivateEvent(clientID, new LTGEvent(eventType, null, clientID, payload));
	}
	
	
	/**
	 * This method is called every time a new event comes.
	 * Subclasses need to implement this method in order to 
	 * process events accordingly. 
	 * 
	 * @param m the XMPP message coming to the handler
	 */
	protected abstract void processEvent(Message m);
	
}
