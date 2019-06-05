package top.yistar.wx.wxapp.msg.handler;

import top.yistar.wx.wxapp.entity.ReceiveXmlModel;

/**
 * 
 * @author ChicUniqueKing
 *	微信消息处理器接口
 */
public interface MessageHandler {
	
	public String handleMessage(ReceiveXmlModel receiveXmlModel);

}
