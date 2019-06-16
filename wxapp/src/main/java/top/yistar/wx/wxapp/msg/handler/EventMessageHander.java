package top.yistar.wx.wxapp.msg.handler;

import java.util.UUID;

import org.slf4j.Logger;

import top.yistar.wx.wxapp.annotation.MsgHandler;
import top.yistar.wx.wxapp.entity.ReceiveXmlModel;
import top.yistar.wx.wxapp.util.LogUtil;


@MsgHandler(type="event")
public class EventMessageHander implements MessageHandler{
	
	private Logger logger = LogUtil.getLog(EventMessageHander.class);

	@Override
	public String handleMessage(ReceiveXmlModel receiveXmlModel) {
		
		logger.info("-------come to event message handler----------");
		String time = System.currentTimeMillis()+"";
		String msgId = UUID.randomUUID().toString().replaceAll("-", "");
		String timestamps = time.substring(0, 10);
		String returnMsg = "<xml>\r\n" + 
				"  <ToUserName><![CDATA["+receiveXmlModel.getFromUserName()+"]]></ToUserName>\r\n" + 
				"  <FromUserName><![CDATA["+receiveXmlModel.getToUserName()+"]]></FromUserName>\r\n" + 
				"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
				"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
				"  <Content><![CDATA[欢迎关注订阅号 还在持续开发中 敬请期待 ieischic520@163.com \n 1:笑话]]></Content>\r\n" + 
				"  <MsgId>"+msgId+"</MsgId>\r\n" + 
				"</xml>";
		
		return returnMsg;
	}

}
