package distar.project.service;

import java.util.HashMap;

public class Notification {
	public static final int TYPE_INFO = 1;
	public static final int TYPE_SUCCESS = 2;
	public static final int TYPE_WARN = 3;
	public static final int TYPE_ERROR = 4;
	private static final String TITLE = "title";
	private static final String MESSAGE = "message";

	private int type;
	private HashMap<String, String> msgMap;

	public Notification(int msgType, String msg) {
		String title;
		type = msgType;
		this.msgMap = new HashMap<String, String>();
		msgMap.put(MESSAGE, msg);

		switch (msgType) {
		case TYPE_INFO:
			title = "�?�?าวสาร";
			break;
		case TYPE_SUCCESS:
			title = "เสร�?�?สิ�?�?";
			break;
		case TYPE_ERROR:
			title = "�?�?อ�?ิด�?ลาด";
			break;
		case TYPE_WARN:
			title = "�?ำเตือ�?";
			break;

		default:
			title = "�?�?าวสาร";
			break;
		}
		msgMap.put(TITLE, title);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return this.msgMap.remove(TITLE);
	}

	public void setTitle(String title) {
		this.msgMap.put(TITLE, title);
	}

	public String getMessage() {
		return this.msgMap.remove(MESSAGE);
	}

	public void setMessage(String message) {
		this.msgMap.put(MESSAGE, message);

	}

	public boolean getTestVoidMessage() {

		return (msgMap.get(MESSAGE) == null);
	}

}
