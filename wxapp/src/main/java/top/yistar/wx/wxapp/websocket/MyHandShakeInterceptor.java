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

/**
  *@Author  ChicUniqueKing
  *@Description  websocket 拦截器
  *@Date 16:33 2019/8/2
  *@Param
  *@Return
  **/
public class MyHandShakeInterceptor implements HandshakeInterceptor {

	public static final Logger LOG = LoggerFactory.getLogger(MyHandShakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> map) throws Exception {
		 if (request instanceof ServletServerHttpRequest) {
	            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
	            String userId = servletRequest.getServletRequest().getParameter("userId");
						LOG.info("----------拦截请求-用户id--------"+userId);
			HttpSession session = UserSessionManager.getSessionUser("userSessions"+userId);
	            if(session==null){
	            	LOG.info("session 为null");
            	return false;
				}
	            // 标记用户
	            User user = (User) session.getAttribute("user");
			 UserSessionManager.clearSession("userSessions"+userId);
			 LOG.info("------------------------"+UserSessionManager.getSessionUser("userSessions"));
	            if(user!=null){
	                map.put("uid", user.getId());//为服务器创建WebSocketSession做准备
					Message msg = new Message();
					msg.setMsgType("systemMsg");
					msg.setUserName(user.getUsername());
					msg.setMessageText(user.getName()+"进来了");
					TextMessage serverReturn = new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg));
					MyWebSocketHandler.sendMsgToEveryOne(WebsocketConstant.SYSTEM_ID,serverReturn);
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
