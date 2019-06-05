package top.yistar.wx.wxapp.msg.handler;

import top.yistar.wx.wxapp.annotation.MsgHandler;
import top.yistar.wx.wxapp.entity.ReceiveXmlModel;

@MsgHandler(type="image")
public class ImageMessageHandler implements MessageHandler{

	@Override
	public String handleMessage(ReceiveXmlModel receiveXmlModel) {
		return null;
	}

}
