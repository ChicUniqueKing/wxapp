package top.yistar.wx.wxapp.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import top.yistar.wx.wxapp.entity.Message;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


//Mywebsockethandler处理类实现websockethandler
@Component
public class MyWebSocketHandler implements WebSocketHandler {

	public static final Logger LOG = LoggerFactory.getLogger(MyWebSocketHandler.class);


	
	 //当MyWebSocketHandler类被加载时就会创建该Map，随类而生
    public static final Map<String, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<String, WebSocketSession>();
    }
	
    
    //连接建立之后
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
    	LOG.info("建立连接");
		// TODO Auto-generated method stub
		String uid = webSocketSession.getAttributes().get("uid").toString();
		LOG.info("websocket id=",uid);
        if (userSocketSessionMap.get(uid) == null) {
            userSocketSessionMap.put(uid, webSocketSession);
        }
	}
    
	//发送信息前的处理
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		LOG.info("=========msg handler=======  msg:"+message);
		 if(message.getPayloadLength()==0)return;
	        //得到Socket通道中的数据并转化为Message对象
	        Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);
	        Timestamp now = new Timestamp(System.currentTimeMillis());
	        msg.setMessageDate(now);
	        LOG.info("===="+msg);
//	        msg.setUserName("websocker服务器");
	        //将信息保存至数据库
	       // youandmeService.addMessage(msg.getFromId(),msg.getFromName(),msg.getToId(),msg.getMessageText(),msg.getMessageDate());
	        //发送Socket信息

		  //判断消息类型
		String msgType = msg.getMsgType();
		TextMessage  serverReturn = new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg));

		if(msgType.equals("0001")){//用户退出消息

		}else if(msgType.equals("0002")){//用户进入

		}else if(msgType.equals("0003")){//用户列表

		}else if(msgType.equals("0004")){//普通文本消息
			if(msg.getToId()==null){//目标对象为null 则为群聊
				sendMsgToEveryOne(msg.getFromId(),serverReturn);
			}else{
				//发送给指定用户
				sendMessageToUser(msg.getFromId(), serverReturn);
			}

		}else{//心跳续命
			sendMessageToUser(msg.getFromId(), serverReturn);
		}

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    	LOG.info("---------------------handleTransportError----- ------------"+exception.getMessage());
		//session.close();
		/*for(Integer key : userSocketSessionMap.keySet()){
			if(){

			}
		}*/
		
	}
	//连接关闭之后
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        LOG.info("WebSocket:"+webSocketSession.getAttributes().get("uid")+"close connection");
        Iterator<Map.Entry<String,WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,WebSocketSession> entry = iterator.next();
            if(entry.getValue().getAttributes().get("uid")==webSocketSession.getAttributes().get("uid")){
                userSocketSessionMap.remove(webSocketSession.getAttributes().get("uid"));
                System.out.println("WebSocket in staticMap:" + webSocketSession.getAttributes().get("uid") + "removed");
            }
        }
		Message msg = new Message();
        msg.setMsgType("systemMsg");
		msg.setMessageText("离开聊天室");
		TextMessage  serverReturn = new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg));
		sendMsgToEveryOne(WebsocketConstant.SYSTEM_ID,serverReturn);
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return true;
	}
    
	
	
	 //发送信息的实现 一对一
    public  static void sendMessageToUser(String uid, TextMessage message)
            throws IOException {
        WebSocketSession session = userSocketSessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }

    //群聊 发送消息
	public static void sendMsgToEveryOne(String userSendId,TextMessage message)
			throws IOException {
    	for(String key: userSocketSessionMap.keySet()){
    		//自己发群聊消息 自己不用接受给自己返回
//    		if(key.equals(userSendId)){
//				WebSocketSession session1 = userSocketSessionMap.get(key);
//				session1.sendMessage(message);
//				continue;
//			}
			WebSocketSession session = userSocketSessionMap.get(key);
			if (session != null && session.isOpen()) {
				session.sendMessage(message);
			}
		}

	}

}
