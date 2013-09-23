package ltg.commons.ltg_handler;

public interface MultiLTGEventListener extends LTGEventListener {
	
	public void processEvent(String chatroom, LTGEvent e);

}
