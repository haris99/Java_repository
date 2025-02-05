package ProjectHD;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = 1745845380334011351L;
	
	protected String messageText;
	protected String remoteStackTrace;

	public Message() {
		this(null, null);
	}

	public Message(String errorText, String remoteStackTrace) {
		super();
		this.messageText = errorText;
		this.remoteStackTrace = remoteStackTrace;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getRemoteStackTrace() {
		return remoteStackTrace;
	}

	public void setRemoteStackTrace(String remoteStackTrace) {
		this.remoteStackTrace = remoteStackTrace;
	}
	
	public String toString(){
		return "ERR_MSG: "+messageText;
	}

}

