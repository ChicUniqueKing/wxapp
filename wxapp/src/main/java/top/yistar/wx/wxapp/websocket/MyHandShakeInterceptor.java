package top.yistar.wx.wxapp.websocket;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import top.yistar.wx.wxapp.entity.Message;
import top.yistar.wx.wxapp.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;



public class MyHandShakeInterceptor implements HandshakeInterceptor {

	public static final Logger LOG = LoggerFactory.getLogger(MyHandShakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> map) throws Exception {
		LOG.info("----------拦截请求---------");
		 if (request instanceof ServletServerHttpRequest) {
	            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = UserSessionManager.getSessionUser("userSessions");
	            if(session==null){
	            	LOG.info("session 为null");
	            	return false;
				}
	            // 标记用户
	            User user = (User) session.getAttribute("user");
			 UserSessionManager.clearSession("userSessions");
	            if(user!=null){
	                map.put("uid", user.getId());//为服务器创建WebSocketSession做准备
					Message msg = new Message();
					msg.setMsgType("systemMsg");
					msg.setUserName(user.getUsername());
					msg.setMessageText(user.getName()+"进来了");
					TextMessage serverReturn = new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg));
					MyWebSocketHandler.sendMsgToEveryOne("0000",serverReturn);
	                System.out.println("用户id："+user.getId()+" 被加入");
	            }else{
	                System.out.println("user为空");
	                return false;
	            }
	        }
	        return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
	}
}
