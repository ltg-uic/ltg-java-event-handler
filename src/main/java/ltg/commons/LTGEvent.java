package ltg.commons;

import com.fasterxml.jackson.databind.JsonNode;

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
	
	
	public LTGEvent(String event, JsonNode payload) {
		this.event = event;
		this.payload = payload;
	}


	public String getType() {
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
		String es = "[" + event + "] ";
		if (origin!=null)
			es = es + "from:" + origin + " ";
		if (destination!=null)
			es = es + "to:" + destination + " ";
		es = es + payload.toString();
		return es;
	}

}
