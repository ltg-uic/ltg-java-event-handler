package ltg.commons.porcupine_handler;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

abstract public class PorcupineEvent implements Serializable {
	// Needed for serializable
	private static final long serialVersionUID = 954867594050013588L;
	
	protected String request = null;
	protected String action = null;
	protected String origin = null;
	protected String url = null;
	protected JsonNode data = null;


	public String getURL() {
		return url;
	}
	
	
	public JsonNode getData() {
		return data;
	}	

}
