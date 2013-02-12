package ltg.commons;

import com.fasterxml.jackson.databind.JsonNode;

public class LTGEvent {
	
	private String type;
	private String origin = null;
	private String destination = null;
	private JsonNode payload = null;
	
	
	public LTGEvent(String type, String origin, String destination, JsonNode payload) {
		super();
		this.type = type;
		this.origin = origin;
		this.destination = destination;
		this.payload = payload;
	}


	public String getType() {
		return type;
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
		return "[" + type + "] from: " + origin + ", to: " + destination + " " + payload.asText();
	}

}
