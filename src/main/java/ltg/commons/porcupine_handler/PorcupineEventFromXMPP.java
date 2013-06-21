package ltg.commons.porcupine_handler;

import com.fasterxml.jackson.databind.JsonNode;

public class PorcupineEventFromXMPP extends PorcupineEvent {
	// Needed for serializable
	private static final long serialVersionUID = 6910300061950879635L;
	
	
	public PorcupineEventFromXMPP(String request, String origin, String url, JsonNode data) {
		this.request = request;
		this.origin = origin;
		this.url = url;
		this.data = data;
	}
	
	public String getRequest() {
		return request;
	}
	
	
	public String getOrigin() {
		return origin;
	}

	
	@Override
	public String toString() {
		String es = "[" + request + "] ";
		if (origin!=null)
			es = es + "from:" + origin + " ";
		if (url!=null)
			es = es + "url:" + url + " ";
		es = es + data.toString();
		return es;
	}

}
