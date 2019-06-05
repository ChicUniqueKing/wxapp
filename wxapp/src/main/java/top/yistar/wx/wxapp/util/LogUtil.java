package top.yistar.wx.wxapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	
	
	public static Logger getLog(Class<?> clazz) {
		Logger log = LoggerFactory.getLogger(clazz);
		return log;
	} 

}
