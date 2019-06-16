package top.yistar.wx.wxapp.msg.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import top.yistar.wx.wxapp.annotation.MsgHandler;
import top.yistar.wx.wxapp.entity.ReceiveXmlModel;
import top.yistar.wx.wxapp.enums.MessageTypeEnum;
import top.yistar.wx.wxapp.util.LogUtil;

/**
 * 
 * @author ChicUniqueKing
 *	消息处理器
 */
@Component
public class MessageHandlerManager implements InitializingBean{
	
	
	/**
	 * 日志
	 */
	private static final Logger LOG = LogUtil.getLog(MessageHandlerManager.class);
	
	@Autowired
	private List<MessageHandler> messageHandlers;//消息处理器集合
	
	private Map<String, MessageHandler> handlers = new HashMap<>();
	
	
	
	/**
	 * 
	 * @param receiveXmlModel
	 * @return 根据消息类型获取对应的处理器
	 */
	public String getHandleResult(ReceiveXmlModel receiveXmlModel) {
		//
		String msgType = receiveXmlModel.getMsgType();
		MessageHandler messageHandler = handlers.get(msgType);
		if(messageHandler==null) {
			//获取不到对应类型的消息处理器
			LOG.info("can not get "+msgType+" message handler!");
		}
		//获取处理结果
		String rs = messageHandler.handleMessage(receiveXmlModel);
		return rs;
	}

	@Override
	public void afterPropertiesSet() throws Exception {//初始化消息处理器
		LOG.info("init weixin message handler------");
		List<String> msgTypes = MessageTypeEnum.getWxMessageTypes();
		if(msgTypes.isEmpty()) {
			return;
		}
		for(int i=0;i<msgTypes.size();i++) {
			for(int j= 0;j<messageHandlers.size();j++) {
				MessageHandler messageHandler = messageHandlers.get(j);
				MsgHandler msgHandler = messageHandler.getClass().getAnnotation(MsgHandler.class);
				String msgType = msgHandler.type();
				if(!StringUtils.hasText(msgType)) {
					//
					LOG.info("The message handler is no msg type ={}");
					continue;
				}
				if(msgType.equals(msgTypes.get(i))) {
					handlers.put(msgType, messageHandlers.get(j));
				}
				
			}
		}
	}
}
