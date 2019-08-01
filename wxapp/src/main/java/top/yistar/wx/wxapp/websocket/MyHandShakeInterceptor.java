package top.yistar.wx.wxapp.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import top.yistar.wx.wxapp.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;


public class MyHandShakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> map) throws Exception {
		 if (request instanceof ServletServerHttpRequest) {
	            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
	            HttpSession session = servletRequest.getServletRequest().getSession(false);
	            // 标记用户
	            User user = (User) session.getAttribute("user");
	            if(user!=null){
	                map.put("uid", user.getId());//为服务器创建WebSocketSession做准备
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
