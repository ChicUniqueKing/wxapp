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
	
	private Integer fromId; //消息来自
	
	private Integer toId;//
	
	private String messageText;
	
	private Date messageDate;//发送时间

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(Integer id, Integer fromId, Integer toId, String messageText, Date messageDate) {
		super();
		this.id = id;
		this.fromId = fromId;
		this.toId = toId;
		this.messageText = messageText;
		this.messageDate = messageDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public Integer getToId() {
		return toId;
	}

	public void setToId(Integer toId) {
		this.toId = toId;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	
	
}
