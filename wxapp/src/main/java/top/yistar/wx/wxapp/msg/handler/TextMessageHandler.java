package top.yistar.wx.wxapp.msg.handler;

import org.slf4j.Logger;

import top.yistar.wx.wxapp.annotation.MsgHandler;
import top.yistar.wx.wxapp.entity.ReceiveXmlModel;
import top.yistar.wx.wxapp.util.LogUtil;

/**
 * 
 * @author ChicUniqueKing
 *	微信文本消息处理器
 */

@MsgHandler(type="text") //标记为文本消息处理器
public class TextMessageHandler implements MessageHandler{
	
	public static final Logger Log = LogUtil.getLog(TextMessageHandler.class);

	@Override
	public String handleMessage(ReceiveXmlModel receiveXmlModel) {
		Log.info("----come to text message handler------");
		//对消息进行处理
		
		
		return null;
	}

}
