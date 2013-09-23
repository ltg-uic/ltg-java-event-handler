package ltg.commons.ltg_handler;

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
	 * TODO
	 */
	public void runAsynchronously() {
		printRegisteredListeners();
		sc.registerEventListener(new MessageListener() {
			public void processMessage(Message m) {
				processEvent(m);
			}
		});
	}


	/**
	 * TODO
	 */
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
	
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public String getId() {
		return sc.getUsername();
	}
	
	
	/**
	 * TODO
	 */
	public void close() {
		sc.disconnect();
	}
	
	
	/**
	 * Generates a public event that is either broadcasted to the whole group chat
	 * or addressd to a specific agent or client.
	 * 
	 * @param e the event
	 */
	public void generateEvent(LTGEvent e) {
		sc.sendMUCMessage(LTGEvent.serializeEvent(e));
	}
	
	
	/**
	 * Generates a public event that is broadcasted to the whole group chat.
	 * 
	 * @param event
	 * @param payload
	 */
	public void generateEvent(String event, JsonNode payload) {
		generateEvent(new LTGEvent(event, null, null, payload));
	}
	

	/**
	 * Generates a point-to-point event that is sent to a particular client
	 * off the public group chat.
	 * 
	 * @param destination
	 * @param e
	 */
	public void generatePrivateEvent(String destination, LTGEvent e) {
		sc.sendMessage(destination, LTGEvent.serializeEvent(e));
	}

	
	/**
	 * Generates a point-to-point event that is sent to a particular client
	 * off the public group chat.
	 * 
	 * @param event
	 * @param destination
	 * @param payload
	 */
	public void generatePrivateEvent(String destination, String event, JsonNode payload) {
		generatePrivateEvent(destination, new LTGEvent(event, sc.getUsername(), destination, payload));
	}
	
	
	/**
	 * TODO
	 */
	protected synchronized void printRegisteredListeners() {
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
	 * TODO
	 * 
	 * @param m
	 */
	protected abstract void processEvent(Message m);
	
}
