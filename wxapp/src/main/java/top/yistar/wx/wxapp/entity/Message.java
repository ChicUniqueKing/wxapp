package top.yistar.wx.wxapp.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author ChicUniqueKing
 *  消息类
 *
 */
public class Message implements Serializable {
    
	private Integer id;//消息id
	
	private String fromId; //消息来自

	private String userName;//用户名称
	
	private String toId;//
	
	private String messageText;

	private String msgType;//消息类型
	
	private Date messageDate;//发送时间

	public Message() {
	}

	public Message(Integer id, String fromId, String userName, String toId, String messageText, String msgType, Date messageDate) {
		this.id = id;
		this.fromId = fromId;
		this.userName = userName;
		this.toId = toId;
		this.messageText = messageText;
		this.msgType = msgType;
		this.messageDate = messageDate;
	}

	public Integer getId() {
		return id;
	}

	public String getFromId() {
		return fromId;
	}

	public String getUserName() {
		return userName;
	}

	public String getToId() {
		return toId;
	}

	public String getMessageText() {
		return messageText;
	}

	public String getMsgType() {
		return msgType;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	@Override
	public String toString() {
		return "Message{" +
				"id=" + id +
				", fromId='" + fromId + '\'' +
				", userName='" + userName + '\'' +
				", toId='" + toId + '\'' +
				", messageText='" + messageText + '\'' +
				", msgType='" + msgType + '\'' +
				", messageDate=" + messageDate +
				'}';
	}
}
