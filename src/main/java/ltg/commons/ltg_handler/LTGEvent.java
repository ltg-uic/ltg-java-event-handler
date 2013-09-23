package ltg.commons.ltg_handler;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LTGEvent {

	private String event;
	private String origin = null;
	private String destination = null;
	private JsonNode payload = null;


	public LTGEvent(String event, String origin, String destination, JsonNode payload) {
		this.event = event;
		this.origin = origin;
		this.destination = destination;
		this.payload = payload;
	}


	public String getEventType() {
		return event;
	}


	public String getOrigin() {
		return origin;
	}


	public String getDestination() {
		return destination;
	}


	public JsonNode getPayload() {
		return payload;
	}	


	@Override
	public String toString() {
		String es = "event:" + event + " ";
		if (origin!=null)
			es = es + "from:" + origin + " ";
		if (destination!=null)
			es = es + "to:" + destination + " ";
		es = es + "payload:" + payload.toString();
		return es;
	}


	/**
	 * Serializes a <code>LTGEvent</code> object into JSON.
	 * 
	 * @param e
	 * @return
	 */
	public static String serializeEvent(LTGEvent e) {
		ObjectNode json = new ObjectMapper().createObjectNode();
		json.put("event", e.getEventType());
		if (e.getOrigin()!=null)
			json.put("origin", e.getOrigin());
		if (e.getDestination()!=null)
			json.put("destination", e.getDestination());
		json.put("payload", e.getPayload());
		return json.toString();
	}


	/**
	 * De-serializes JSON into a <code>LTGEvent</code> object.
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws NotAnLTGEventException
	 */
	public static LTGEvent deserializeEvent(String json) throws IOException, NotAnLTGEventException {
		// Parse JSON
		ObjectMapper jsonParser = new ObjectMapper();
		JsonNode jn = jsonParser.readTree(json);
		String event = jn.path("event").textValue();
		String origin = jn.path("origin").textValue();
		String destination = jn.path("destination").textValue();
		JsonNode payload = jn.path("payload");
		// Validate fields
		if(event==null || event.isEmpty()) 
			throw new NotAnLTGEventException();
		if (payload==null)
			throw new NotAnLTGEventException();
		// Create and return event
		return new LTGEvent(event, origin, destination, payload);
	}

}
