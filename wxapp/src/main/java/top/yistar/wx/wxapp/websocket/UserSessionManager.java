package top.yistar.wx.wxapp.websocket;

import top.yistar.wx.wxapp.entity.User;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author chic
 * @Date 2019/8/1 10 46
 * @Description
 * @Company 测试信息技术
 * @Team chic项目组
 */
public class UserSessionManager {

    /**
       线程绑定的map
      **/
    private static final Map<String,HttpSession> sessionMap = new ConcurrentHashMap<>();


    //获取session
    public static HttpSession getSessionUser(String userId){
        return sessionMap.get(userId);
    }

     //设置session
    public static void setSessionUser(String userId,HttpSession Session){
        sessionMap.put(userId,Session);
    }

    //清除session
    public static void clearSession(String userId){
        sessionMap.remove(userId);
    }



}
