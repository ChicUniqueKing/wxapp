package top.yistar.wx.wxapp.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    
	
	 //当MyWebSocketHandler类被加载时就会创建该Map，随类而生
    public static final Map<Integer, WebSocketSession> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<Integer, WebSocketSession>();
    }
	
    
    //连接建立之后
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		// TODO Auto-generated method stub
		int uid = (Integer)webSocketSession.getAttributes().get("uid");
        if (userSocketSessionMap.get(uid) == null) {
            userSocketSessionMap.put(uid, webSocketSession);
        }
	}
    
	//发送信息前的处理
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// TODO Auto-generated method stub
		 if(message.getPayloadLength()==0)return;

	        //得到Socket通道中的数据并转化为Message对象
	        Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);

	        Timestamp now = new Timestamp(System.currentTimeMillis());
	        msg.setMessageDate(now);
	        //将信息保存至数据库
	       // youandmeService.addMessage(msg.getFromId(),msg.getFromName(),msg.getToId(),msg.getMessageText(),msg.getMessageDate());

	        //发送Socket信息
	        sendMessageToUser(msg.getToId(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		
	}
    
	
	
	//连接关闭之后
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub

        System.out.println("WebSocket:"+webSocketSession.getAttributes().get("uid")+"close connection");
        Iterator<Map.Entry<Integer,WebSocketSession>> iterator = userSocketSessionMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer,WebSocketSession> entry = iterator.next();
            if(entry.getValue().getAttributes().get("uid")==webSocketSession.getAttributes().get("uid")){
                userSocketSessionMap.remove(webSocketSession.getAttributes().get("uid"));
                System.out.println("WebSocket in staticMap:" + webSocketSession.getAttributes().get("uid") + "removed");
            }
        }
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return true;
	}
    
	
	
	 //发送信息的实现 一对一
    public void sendMessageToUser(int uid, TextMessage message)
            throws IOException {
        WebSocketSession session = userSocketSessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
    }
}
