package top.yistar.wx.wxapp.msg.handler;

import top.yistar.wx.wxapp.annotation.MsgHandler;
import top.yistar.wx.wxapp.entity.ReceiveXmlModel;

@MsgHandler(type="voice")
public class VoiceMessageHandler implements MessageHandler{

	@Override
	public String handleMessage(ReceiveXmlModel receiveXmlModel) {
		return null;
	}

}
