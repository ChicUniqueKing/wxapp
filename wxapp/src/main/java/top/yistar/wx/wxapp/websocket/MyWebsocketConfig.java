package top.yistar.wx.wxapp.websocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Component
@EnableWebSocket
public class MyWebsocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	
	@Autowired
	MyWebSocketHandler handler;
	


	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(handler, "/ws").setAllowedOrigins("*").addInterceptors(new MyHandShakeInterceptor());
		
		registry.addHandler(handler, "/ws/socketjs").setAllowedOrigins("*").addInterceptors(new MyHandShakeInterceptor()).withSockJS();
		
	}
}
