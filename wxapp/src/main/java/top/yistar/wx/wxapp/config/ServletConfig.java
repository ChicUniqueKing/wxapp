package top.yistar.wx.wxapp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

@Component
public class ServletConfig implements ServletContextInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("========================================>>>>>>>");
	}
	
	

}
