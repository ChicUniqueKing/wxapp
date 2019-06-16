package top.yistar.wx.wxapp.msg.handler;

import java.util.UUID;

import top.yistar.wx.wxapp.annotation.MsgHandler;
import top.yistar.wx.wxapp.entity.ReceiveXmlModel;

@MsgHandler(type="vedio")
public class VedioMessaageHandler implements MessageHandler{

	@Override
	public String handleMessage(ReceiveXmlModel receiveXmlModel) {
		String time = System.currentTimeMillis()+"";
		String msgId = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamps = time.substring(0, 10);
		String returnMsg = "<xml>\r\n" + 
				"  <ToUserName><![CDATA["+receiveXmlModel.getFromUserName()+"]]></ToUserName>\r\n" + 
				"  <FromUserName><![CDATA["+receiveXmlModel.getToUserName()+"]]></FromUserName>\r\n" + 
				"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
				"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
				"  <Content><![CDATA[功能还在完善中,您可以发送您的想法 到我的个人邮箱ieischic520@163.com 互相共同学习进步]]></Content>\r\n" + 
				"  <MsgId>"+msgId+"</MsgId>\r\n" + 
				"</xml>";
		return returnMsg;
	}
	
	
	

}
