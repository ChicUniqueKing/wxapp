package top.yistar.wx.wxapp.msg.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	
	
	private static final String JOK1 = "春分，意思就是春天到了，该分手了。";
	private static final String JOK2 = "就算失败了99次，也要努力继续。。。凑个整！";
	private static final String JOK3 = "还有种距离叫：想通过朋友圈了解他，但他不发朋友圈。";
	private static final String JOK4 = "没有存在感，但内心却全速运转，简直就像是空调室外机一样的人。";
	private static final String JOK5 = "微胖的女孩，运气不会太差，因为运气太差的女孩都是巨胖。";
	private static final String JOK6 = "我好哥们儿的妻子明天要去跳伞了，我真的好担心她的伞打不开。上次这么大的东西撞击地球，所有的恐龙都消失了。";
	private static final String JOK7 = "不知道古代那个从南方拉荔枝给妃子吃的皇帝，得知今天的大豆跨越半个地球只是用来喂猪这件事后会怎么想？？？";
	private static final String JOK8 = "其实几年前也有人找到我，问我是否愿意进娱乐圈试试，但我知道我的长相注定了我在演艺圈发展不会太好，戏路太窄，只能演花瓶，就拒绝了。";
	private static final String JOK9 = "去便利店买了24块钱的东西——招商银行app一条信息，您消费24元；短信一条信息，您消费24元；微信支付一条信息，您消费24元。给我整一愣：我买啥了就花了70多? ";
	private static final String JOK10 = "养金鱼真的挺方便的，第一天养，第二天就都死光了，饲料都不用喂。 ";
	private static final String JOK11 = "年纪大了最显著的特征是，八卦讲到一半，姐妹说上次已经讲过了。";
	private static final String JOK12 = "保险公司的存在并不是为了给你提供保险。他们其实是想从你不需要保险的计算风险中赚钱。 ";
	private static final String JOK13 = "我有一种特别厉害的能力，总能在人们丢东西前找到它们。但是警察却坚持说我这叫偷窃。";
	private static final String JOK14 = "“代沟”存在的好处就是让上一代的劣俗和毛病不会传承下去。 ";
	
	private static final List<String> joks = new ArrayList<>();
	
	static {
		for(int i=0;i<14;i++) {
			joks.add(JOK1);
			joks.add(JOK2);
			joks.add(JOK3);
			joks.add(JOK4);
			joks.add(JOK5);
			joks.add(JOK6);
			joks.add(JOK7);
			joks.add(JOK8);
			joks.add(JOK9);
			joks.add(JOK10);
			joks.add(JOK11);
			joks.add(JOK12);
			joks.add(JOK13);
			joks.add(JOK14);
		}
	}
	
	public static final Logger Log = LogUtil.getLog(TextMessageHandler.class);

	@Override
	public String handleMessage(ReceiveXmlModel receiveXmlModel) {
		Log.info("----come to text message handler------");
		//对消息进行处理
			String msgId = UUID.randomUUID().toString().replaceAll("-", "");
			String timestamps = System.currentTimeMillis()+"";
			String msgText = receiveXmlModel.getContent();
			String returnMsg ="";
			if(msgText.equals("1")){
				 int index = (int)(1+Math.random()*13);
				String content = joks.get(index);
				returnMsg ="<xml>\r\n" + 
						"  <ToUserName><![CDATA["+receiveXmlModel.getFromUserName()+"]]></ToUserName>\r\n" + 
						"  <FromUserName><![CDATA["+receiveXmlModel.getToUserName()+"]]></FromUserName>\r\n" + 
						"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
						"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
						"  <Content><![CDATA["+content+"]]></Content>\r\n" + 
						"  <MsgId>"+msgId+"</MsgId>\r\n" + 
						"</xml>";
			}else {
				returnMsg ="<xml>\r\n" + 
						"  <ToUserName><![CDATA["+receiveXmlModel.getFromUserName()+"]]></ToUserName>\r\n" + 
						"  <FromUserName><![CDATA["+receiveXmlModel.getToUserName()+"]]></FromUserName>\r\n" + 
						"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
						"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
						"  <Content><![CDATA[欢迎关注订阅号 还在持续开发中 敬请期待]]></Content>\r\n" + 
						"  <MsgId>"+msgId+"</MsgId>\r\n" + 
						"</xml>";
			}
		
		return returnMsg;
	}

}
