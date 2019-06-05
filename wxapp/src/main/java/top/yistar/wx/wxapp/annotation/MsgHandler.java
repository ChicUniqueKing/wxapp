package top.yistar.wx.wxapp.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 
 * @author ChicUniqueKing 微信消息处理器初始化注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // 让它标记的类交给spring容器管理bean实例对象
public @interface MsgHandler {

	/**
	 * type : 消息类型 spring容器启动 用户初始化 微信消息处理器对象
	 */
	public String type() default "";

}
