package top.yistar.wx.wxapp.wxcontroller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.client.utils.HttpClientUtils;
import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.yistar.wx.wxapp.entity.ReceiveXmlModel;
import top.yistar.wx.wxapp.util.ReceiveXmlProcess;
import top.yistar.wx.wxapp.util.SHA1;


/**
 * 
 * @author ChicUniqueKing
 *	微信公众号控制器类
 */
@RestController
public class WxController {
	
	
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
	
	
	public static final Logger LOG = LoggerFactory.getLogger(WxController.class);
	//验证接口  微信服务器对开发者模式的验证接口
	@GetMapping(value="/wx")
	public String tokenCheck(HttpServletRequest request,HttpServletResponse response) {
		String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String echostr=request.getParameter("echostr");
        String token="honsin008";//这里填基本配置中的token
        String jiami="";
         jiami=SHA1.getSHA1(token, timestamp, nonce);//这里是对三个参数进行加密
         System.out.println("加密"+jiami);
         System.out.println("本身"+signature);
            PrintWriter out;
			try {
				out = response.getWriter();
				 if(jiami.equals(signature))
			            out.print(echostr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
		return null;
	}
	
	
	//测试手机号归属地api测试
	@RequestMapping(value="/test")
	public Map<String, String> indexTest(String name){
		Map<String, String> map = new HashMap<>();
		String phoneUrl = "https://www.baifubao.com/callback?phone=15850781443";
		HttpClient client = new HttpClient();
		HostConfiguration configuration = new HostConfiguration();
		configuration.setHost(phoneUrl);
		HttpClientParams params = new HttpClientParams();
		params.setParameter("phone", name);
		 client.setHostConfiguration(configuration);
		client.setParams(params);
		PostMethod method = new PostMethod();
		try {
			int rs = client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("hello", name);
		return map;
	}
	
	//接受微信服务器的消息
	@PostMapping(value="/wx")
	public String receiveMsg(HttpServletRequest reqeust,HttpServletResponse response) {
		LOG.info("come to wx application msg handler");
		String returnMsg ="";
		InputStream in =null;
		Reader reader =null;
		BufferedReader  reader2 =null;
		try {
			in  = reqeust.getInputStream();
			 reader = new InputStreamReader(in);
			 reader2 = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			String line="";
			while((line =reader2.readLine())!=null) {
				buffer.append(line);
			}
			String xml = buffer.toString();
			LOG.info("receive message ={}",xml);
			ReceiveXmlModel receiveMsg = ReceiveXmlProcess.getMsgEntity(xml);
			String msgType = receiveMsg.getMsgType();
			String msgText = receiveMsg.getContent();
			String msgId = UUID.randomUUID().toString().replaceAll("-", "");
			String time = System.currentTimeMillis()+"";
			String timestamps = time.substring(0, 10);
			LOG.info("微信消息类型={}",msgType);
			if(msgType.equals("text")) {
				if(msgText.equals("1")){
					 int index = (int)(1+Math.random()*13);
					String content = joks.get(index);
					returnMsg ="<xml>\r\n" + 
							"  <ToUserName><![CDATA["+receiveMsg.getFromUserName()+"]]></ToUserName>\r\n" + 
							"  <FromUserName><![CDATA["+receiveMsg.getToUserName()+"]]></FromUserName>\r\n" + 
							"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
							"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
							"  <Content><![CDATA["+content+"]]></Content>\r\n" + 
							"  <MsgId>"+msgId+"</MsgId>\r\n" + 
							"</xml>";
				}else {
					returnMsg ="<xml>\r\n" + 
							"  <ToUserName><![CDATA["+receiveMsg.getFromUserName()+"]]></ToUserName>\r\n" + 
							"  <FromUserName><![CDATA["+receiveMsg.getToUserName()+"]]></FromUserName>\r\n" + 
							"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
							"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
							"  <Content><![CDATA[欢迎关注订阅号 还在持续开发中 敬请期待]]></Content>\r\n" + 
							"  <MsgId>"+msgId+"</MsgId>\r\n" + 
							"</xml>";
				}
			}else if(msgType.equals("event")) {
				returnMsg = "<xml>\r\n" + 
						"  <ToUserName><![CDATA["+receiveMsg.getFromUserName()+"]]></ToUserName>\r\n" + 
						"  <FromUserName><![CDATA["+receiveMsg.getToUserName()+"]]></FromUserName>\r\n" + 
						"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
						"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
						"  <Content><![CDATA[欢迎关注订阅号 还在持续开发中 敬请期待 ieischic520@163.com \n 1:笑话]]></Content>\r\n" + 
						"  <MsgId>"+msgId+"</MsgId>\r\n" + 
						"</xml>";
			} else{
				returnMsg = "<xml>\r\n" + 
						"  <ToUserName><![CDATA["+receiveMsg.getFromUserName()+"]]></ToUserName>\r\n" + 
						"  <FromUserName><![CDATA["+receiveMsg.getToUserName()+"]]></FromUserName>\r\n" + 
						"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
						"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
						"  <Content><![CDATA[功能还在完善中,您可以发送您的想法 到我的个人邮箱ieischic520@163.com 互相共同学习进步]]></Content>\r\n" + 
						"  <MsgId>"+msgId+"</MsgId>\r\n" + 
						"</xml>";
			}
			LOG.info("return  message ={}",returnMsg);
			PrintWriter writer = response.getWriter();
			writer.write(returnMsg);
			writer.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭资源
			
				try {
					if(reader2!=null)reader2.close();
					if(reader!=null)reader.close();
					if(in!=null)in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}
		return null;
	}
	

}
