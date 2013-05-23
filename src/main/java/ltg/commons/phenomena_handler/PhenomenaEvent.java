package ltg.commons.phenomena_handler;

import org.dom4j.Element;

public class PhenomenaEvent {
	
	private String event;
	private Element xml;
	
	
	public PhenomenaEvent(String event, Element xml) {
		super();
		this.event = event;
		this.xml = xml;
	}


	public String getType() {
		return event;
	}
	
	
	public Element getXML() {
		return xml;
	}
}
