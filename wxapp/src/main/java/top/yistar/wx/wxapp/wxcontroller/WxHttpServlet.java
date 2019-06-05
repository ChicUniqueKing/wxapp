package top.yistar.wx.wxapp.wxcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.yistar.wx.wxapp.entity.ReceiveXmlModel;
import top.yistar.wx.wxapp.util.ReceiveXmlProcess;
import top.yistar.wx.wxapp.util.SHA1;


//@WebServlet("/wx")
public class WxHttpServlet extends HttpServlet{
	
	private static final Logger LOG = LoggerFactory.getLogger(WxHttpServlet.class);
	
	
	
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response) {
		
		LOG.info("验证公众号开发者服务器==================》》》");
		//接受微信发来的消息
				//signature
				String appId = "wxc7b58b5590c1e89d";
				String appSecret = "ebfe5805ed68dbfb4f8bebc453fb3950";
				//时间戳
				//随机数
				//String nonce = "1";
				//随机字符串
				//String echostr ="";
				
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
	}
	
	@Override
	public void doPost(HttpServletRequest request,HttpServletResponse response) {
		LOG.info("come to wx application msg handler");
		String returnMsg ="";
		InputStream in =null;
		Reader reader =null;
		BufferedReader  reader2 =null;
		try {
			in  = request.getInputStream();
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
			if(msgType.equals("text")) {
				String msgText = receiveMsg.getContent();
				String msgId = UUID.randomUUID().toString().replaceAll("-", "");
				String time = System.currentTimeMillis()+"";
				String timestamps = time.substring(0, 10);
				if(msgText.equals("笑话")){
					returnMsg ="<xml>\r\n" + 
							"  <ToUserName><![CDATA["+receiveMsg.getFromUserName()+"]]></ToUserName>\r\n" + 
							"  <FromUserName><![CDATA["+receiveMsg.getToUserName()+"]]></FromUserName>\r\n" + 
							"  <CreateTime>"+timestamps+"</CreateTime>\r\n" + 
							"  <MsgType><![CDATA[text]]></MsgType>\r\n" + 
							"  <Content><![CDATA[哈哈哈哈哈哈哈哈哈]]></Content>\r\n" + 
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
	}

}
