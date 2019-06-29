package top.yistar.wx.wxapp.entity;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 
 * @author ChicUniqueKing
 *  返回值
 */
public class ResponsePlafe {
	
	private int resCode;
	
	private String resMsg;
	
	private Object data;

	public ResponsePlafe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponsePlafe(int resCode, String resMsg, Object data) {
		super();
		this.resCode = resCode;
		this.resMsg = resMsg;
		this.data = data;
	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
	

}
