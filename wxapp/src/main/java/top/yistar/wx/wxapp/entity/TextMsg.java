package top.yistar.wx.wxapp.entity;


/**
 * 
 * @author ChicUniqueKing
 *  微信文本消息对象
 */


public class TextMsg {
	
	
	public TextMsg() {
		
	}
	public TextMsg(String toUserName, String fomUserName, String createTime, String msgType, String mediaID,
			String format, String msgId) {
		super();
		this.toUserName = toUserName;
		this.fomUserName = fomUserName;
		this.createTime = createTime;
		MsgType = msgType;
		MediaID = mediaID;
		this.format = format;
		this.msgId = msgId;
	}


	
	

	public String getToUserName() {
		return toUserName;
	}



	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}



	public String getFomUserName() {
		return fomUserName;
	}



	public void setFomUserName(String fomUserName) {
		this.fomUserName = fomUserName;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getMsgType() {
		return MsgType;
	}



	public void setMsgType(String msgType) {
		MsgType = msgType;
	}



	public String getMediaID() {
		return MediaID;
	}



	public void setMediaID(String mediaID) {
		MediaID = mediaID;
	}



	public String getFormat() {
		return format;
	}



	public void setFormat(String format) {
		this.format = format;
	}



	public String getMsgId() {
		return msgId;
	}



	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}





	private String toUserName; //接受者
	private String fomUserName;//发送者
	private String createTime;//消息创建时间
	private String MsgType;//消息类型
	private String MediaID;//媒体类型
	private String format;//Format
	private String msgId;//消息id
	

}
