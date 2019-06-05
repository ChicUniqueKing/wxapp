package top.yistar.wx.wxapp.wxcontroller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;

public class ShutdownController implements ApplicationContextAware {
	
	 private ApplicationContext context;
	    
	    @PostMapping(value="/sdapp")
	    public void shutdownContext() {
	        ((ConfigurableApplicationContext) context).close();
	    
	    }
	    
	    @Override
	    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
	        this.context = ctx;    
	    }

}
