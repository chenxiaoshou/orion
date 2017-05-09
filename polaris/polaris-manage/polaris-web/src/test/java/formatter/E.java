package formatter;

import java.io.Serializable;

public class E implements Serializable {

	private static final long serialVersionUID = -4862286219628129409L;
	
	private byte[] content;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
