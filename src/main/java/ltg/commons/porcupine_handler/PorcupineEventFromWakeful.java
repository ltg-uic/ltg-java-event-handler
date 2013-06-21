package ltg.commons.porcupine_handler;

import com.fasterxml.jackson.databind.JsonNode;

public class PorcupineEventFromWakeful extends PorcupineEvent {
	// Needed for serializable
	private static final long serialVersionUID = -8567184442515799263L;


	public PorcupineEventFromWakeful(String action, String url, JsonNode data) {
		this.action = action;
		this.url = url;
		this.data = data;
	}
	

	public String getAction() {
		return action;
	}
	
	

	
	@Override
	public String toString() {
		String es = "{" + action + "} ";
		if (url!=null)
			es = es + "url:" + url + " ";
		es = es + data.toString();
		return es;
	}

}
