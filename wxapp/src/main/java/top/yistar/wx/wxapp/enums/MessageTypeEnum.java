package top.yistar.wx.wxapp.enums;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 
 * @author ChicUniqueKing
 *	微信消息类型枚举
 */
public enum MessageTypeEnum {
	
	TEXT("text","文本消息"),
	
	IMAGE("image","图片消息"),
	
	VOICE("voice","语音消息"),
	
	VIDEO("video","视频消息");
	
	private String key;
	private String value;
	
	private MessageTypeEnum(String key,String value) {
		this.key=key;
		this.value=value;
	}
	
	public static String  getValue(String key) {
		if(!StringUtils.hasText(key)) {
			return null;
		}
		for(MessageTypeEnum mEnum:MessageTypeEnum.values()) {
			if(mEnum.getKey().equals(key)) {
				return mEnum.key;
			}
		}
		return null;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	/**
	 *  获取所有的消息类型
	 *  
	 *
	 * 
	 */
	public static  List<String> getWxMessageTypes(){
		List<String> msgTypes = new ArrayList<>();
		for(MessageTypeEnum mEnum:MessageTypeEnum.values()) {
				msgTypes.add(mEnum.key);
		}
		
		return msgTypes;
	}
}
