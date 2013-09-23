package ltg.commons.ltg_event_handler;

public interface MultiChatLTGEventListener extends LTGEventListener {
	
	public void processEvent(String chatroom, LTGEvent e);

}
