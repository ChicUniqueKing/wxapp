package top.yistar.wx.wxapp.wxcontroller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.yistar.wx.wxapp.entity.ReceiveXmlModel;
import top.yistar.wx.wxapp.entity.ResponsePlafe;
import top.yistar.wx.wxapp.entity.User;
import top.yistar.wx.wxapp.msg.handler.MessageHandlerManager;
import top.yistar.wx.wxapp.util.HttpUtil;
import top.yistar.wx.wxapp.util.ReceiveXmlProcess;
import top.yistar.wx.wxapp.util.SHA1;
import top.yistar.wx.wxapp.util.UserInfoManager;
import top.yistar.wx.wxapp.websocket.UserSessionManager;


/**
 * 
 * @author ChicUniqueKing
 *	微信公众号控制器类
 */
@RestController
public class WxController {
	
	
	@Autowired
	private MessageHandlerManager messageHandlerManager;
	
	
	private static final String JOK1 = "春分，意思就是春天到了，该分手了。";
	private static final String JOK2 = "就算失败了99次，也要努力继续。。。凑个整！";
	private static final String JOK3 = "还有种距离叫：想通过朋友圈了解他，但他不发朋友圈。";
	private static final String JOK4 = "没有存在感，但内心却全速运转，简直就像是空调室外机一样的人。";
	private static final String JOK5 = "微胖的女孩，运气不会太差，因为运气太差的女孩都是巨胖。";
	private static final String JOK6 = "我好哥们儿的妻子明天要去跳伞了，我真的好担心她的伞打不开。上次这么大的东西撞击地球，所有的恐龙都消失了。";
	private static final String JOK7 = "不知道古代那个从南方拉荔枝给妃子吃的皇帝，得知今天的大豆跨越半个地球只是用来喂猪这件事后会怎么想？？？";
	private static final String JOK8 = "其实几年前也有人找到我，问我是否愿意进娱乐圈试试，但我知道我的长相注定了我在演艺圈发展不会太好，戏路太窄，只能演花瓶，就拒绝了。";
	private static final String JOK9 = "去便利店买了24块钱的东西——招商银行app一条信息，您消费24元；短信一条信息，您消费24元；微信支付一条信息，您消费24元。给我整一愣：我买啥了就花了70多? ";
	private static final String JOK10 = "养金鱼真的挺方便的，第一天养，第二天就都死光了，饲料都不用喂。 ";
	private static final String JOK11 = "年纪大了最显著的特征是，八卦讲到一半，姐妹说上次已经讲过了。";
	private static final String JOK12 = "保险公司的存在并不是为了给你提供保险。他们其实是想从你不需要保险的计算风险中赚钱。 ";
	private static final String JOK13 = "我有一种特别厉害的能力，总能在人们丢东西前找到它们。但是警察却坚持说我这叫偷窃。";
	private static final String JOK14 = "“代沟”存在的好处就是让上一代的劣俗和毛病不会传承下去。 ";
	
	private static final List<String> joks = new ArrayList<>();
	
	static {
			joks.add(JOK1);
			joks.add(JOK2);
			joks.add(JOK3);
			joks.add(JOK4);
			joks.add(JOK5);
			joks.add(JOK6);
			joks.add(JOK7);
			joks.add(JOK8);
			joks.add(JOK9);
			joks.add(JOK10);
			joks.add(JOK11);
			joks.add(JOK12);
			joks.add(JOK13);
			joks.add(JOK14);
	}
	
	
	public static final Logger LOG = LoggerFactory.getLogger(WxController.class);
	//验证接口  微信服务器对开发者模式的验证接口
	@GetMapping(value="/wx")
	public String tokenCheck(HttpServletRequest request,HttpServletResponse response) {
		String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String echostr=request.getParameter("echostr");
        String token="honsin008";//这里填基本配置中的token
        String jiami="";
         jiami=SHA1.getSHA1(token, timestamp, nonce);//这里是对三个参数进行加密
         System.out.println("加密"+jiami);
         System.out.println("本身"+signature);
            PrintWriter out;
			try {
				out = response.getWriter();
				 if(jiami.equals(signature))
			            out.print(echostr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
		return null;
	}
	
	
	//测试手机号归属地api测试
	@RequestMapping(value="/test")
	public Map<String, String> indexTest(HttpServletRequest request){
		Map<String, String> map = new HashMap<>();
		String name = request.getParameter("name");
		System.out.println("===================>>>>>"+name);
		map.put("name", name);
		//String phoneUrl = "http://www.yistar.top/test";
		/*InputStream inputStream = null;
		try {
			inputStream  = request.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			StringBuffer sBuffer = new StringBuffer();
			String line = "";
			while((line=reader.readLine())!=null) {
				sBuffer.append(line);
			}
			LOG.info("-------------------------------->>>"+sBuffer.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*CloseableHttpClient httpClient = HttpClients.createDefault();
		//HttpClient client = new HttpClient();
		List<NameValuePair> paris = new ArrayList<>();
		for()
		HttpPost httpPost =new HttpPost(phoneUrl);
		HostConfiguration configuration = new HostConfiguration();
		configuration.setHost(phoneUrl);
		HttpClientParams params = new HttpClientParams();
		params.setParameter("phone", name);
		 client.setHostConfiguration(configuration);
		client.setParams(params);
		PostMethod method = new PostMethod();
		try {
			int rs = client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//String str = HttpUtil.invokeRemote(phoneUrl, "GET", map);
		//System.out.println(str);
		return map;
	}
	
	//接受微信服务器的消息
	@PostMapping(value="/wx")
	public String receiveMsg(HttpServletRequest reqeust,HttpServletResponse response) {
		LOG.info("come to wx application msg handler");
		String returnMsg ="";
		InputStream in =null;
		Reader reader =null;
		BufferedReader  reader2 =null;
		try {
			in  = reqeust.getInputStream();
			 reader = new InputStreamReader(in);
			 reader2 = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			String line="";
			while((line =reader2.readLine())!=null) {
				buffer.append(line);
			}
			String xml = buffer.toString();
			LOG.info("receive message ={}",xml);
			ReceiveXmlModel receiveMsg = ReceiveXmlProcess.getMsgEntity(xml);
			String msgType = receiveMsg.getMsgType();
			String msgText = receiveMsg.getContent();
			String msgId = UUID.randomUUID().toString().replaceAll("-", "");
			String time = System.currentTimeMillis()+"";
			String timestamps = time.substring(0, 10);
			LOG.info("微信消息类型={}",msgType);
			returnMsg = messageHandlerManager.getHandleResult(receiveMsg);
			LOG.info("return  message ={}",returnMsg);
			PrintWriter writer = response.getWriter();
			writer.write(returnMsg);
			writer.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭资源
				try {
					if(reader2!=null)reader2.close();
					if(reader!=null)reader.close();
					if(in!=null)in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}
		return null;
	}

	/**
	  *@Author  ChicUniqueKing
	  *@Description  天气查询接口
	  *@Date 14:16 2019/7/31
	  *@Param
	  *@Return
	  **/
	@RequestMapping(value="/wether")
	public ResponsePlafe getwether(HttpServletRequest request){
	    String cityCode  = request.getParameter("cityCode");
        String extensions  = request.getParameter("extensions");
		try {
			String key = "5e00f78a6de2cd2a687c0e9b753e2318";
			String url ="https://restapi.amap.com/v3/weather/weatherInfo?parameters";
			Map<String,Object> reqData = new HashMap<>();
			reqData.put("key",key);
			reqData.put("city",cityCode);
			reqData.put("extensions",extensions);
			reqData.put("output","JSON");
			String rs = HttpUtil.inVokeRemote(url,reqData,"GET");
			return new ResponsePlafe(001,"获取天气成功",rs);
		}catch(Exception e){
			LOG.info("-----------"+e.getMessage());
		}
		return new ResponsePlafe(002,"调用天气接口异常",null);

	}

	/**
	  *@Author  ChicUniqueKing
	  *@Description  ip定位
	  *@Date 15:18 2019/7/31
	  *@Param [request]
	  *@Return top.yistar.wx.wxapp.entity.ResponsePlafe
	  **/
	@RequestMapping(value="/location")
	public ResponsePlafe getLocationForIp(HttpServletRequest request){
		try {
			//获取ip
			String userIP = getIp2(request);
			//String xforIP = request.getHeader("X-Forwarded-For");
			//String realIp = request.getHeader("X-Real-IP");
			LOG.info("客户端ip："+userIP);
			String key = "8e21fc98a34fd2fc539c3cdc082eb8a1";
			String url = "https://restapi.amap.com/v3/ip?parameters";
			Map<String,Object> reqData = new HashMap<>();
			reqData.put("key",key);
			reqData.put("ip",userIP);
			reqData.put("output","JSON");
			String rs = HttpUtil.inVokeRemote(url,reqData,"GET");
			return new ResponsePlafe(001,"获取位置成功",rs);
		}catch(Exception e){
			LOG.info("-----------"+e.getMessage());
		}
		return new ResponsePlafe(002,"调用天气接口异常",null);

	}
	//获取用户ip
	public static String getIp2(HttpServletRequest request) {
		           String ip = request.getHeader("X-Forwarded-For");
		          if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			                //多次反向代理后会有多个ip值，第一个ip才是真实ip
			                int index = ip.indexOf(",");
		              if(index != -1){
				                   return ip.substring(0,index);
				              }else{
				                   return ip;
				                }
			            }
		           ip = request.getHeader("X-Real-IP");
		           if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			               return ip;
			           }
		           return request.getRemoteAddr();
		       }

	@RequestMapping("/tochat")
	public User index1(String username, HttpServletRequest request) {
		LOG.info("------------------用户的登录--------");
		User user = new User();


		//
		String userId = UUID.randomUUID().toString().replaceAll("-","");
		user.setId(userId);
		user.setName(username);
		System.out.println(user);
		HttpSession session = request.getSession();
		LOG.info("-----------登陆的session"+session);
		session.setAttribute("user",user);
		UserSessionManager.setSessionUser("userSessions",session);
		HttpSession session1 = UserSessionManager.getSessionUser("userSessions");
		LOG.info("-----------保存在UserSessionManager中的:"+session);
		return user;
	}


	@RequestMapping(value="/register")
	public ResponsePlafe index1(@RequestBody  User user, HttpServletRequest request) {
		//分配一个用户id
		//String userId =
			return new ResponsePlafe(0001,"",null);
	}
	//:https://graph.qq.com/oauth2.0/me?access_token=57959F319C907272DC1B22E2E9C77963



	//https://graph.qq.com/user/get_user_info?access_token=57959F319C907272DC1B22E2E9C77963&oauth_consumer_key=101684736&openid=D1640F154C32F1E4676A5BB0EBAA9868
	@RequestMapping(value="/getUserInfo")
	public ResponsePlafe getUserInfo(HttpServletRequest request){
		//验证token 有效性  有可能该token 已经过期 那么需要用户重新登录
		//TODO
		//access_token=57959F319C907272DC1B22E2E9C77963
		try {
			String openId_url = "https://graph.qq.com/oauth2.0/me";
			Map<String, Object> reqData = new HashMap<>();
			//获取qq登录的access_token
			String access_token = request.getParameter("access_token");
			LOG.info("-----------access_token="+access_token);
			reqData.put("access_token", access_token);
			String rs = HttpUtil.inVokeRemote(openId_url, reqData, "GET");
			String rs_n = rs.substring(rs.indexOf("(")+1, rs.lastIndexOf(")"));
			LOG.info("-----------------="+rs_n);
			String openId = "";
			if (rs != null) {
				JSONObject jsonObject = JSON.parseObject(rs_n);
				openId = jsonObject.getString("openid");
				if (openId == null) {
					return new ResponsePlafe(0002, "获取openid失败", null);
				}
			}
			//access_token=57959F319C907272DC1B22E2E9C77963&oauth_consumer_key=101684736&openid=D1640F154C32F1E4676A5BB0EBAA9868
			String userinfo_url = "https://graph.qq.com/user/get_user_info";
			Map<String, Object> userinfo_Data = new HashMap<>();
			userinfo_Data.put("access_token", access_token);
			userinfo_Data.put("oauth_consumer_key", "101684736");
			userinfo_Data.put("openid", openId);
			String userInfoRs = HttpUtil.inVokeRemote(userinfo_url, userinfo_Data, "GET");
//		{
//			"ret": 0,
//				"msg": "",
//				"is_lost": 0,
//				"nickname": "NoGrilFriendException",
//				"gender": "男",
//				"province": "广东",
//				"city": "深圳",
//				"year": "1991",
//				"constellation": "",
//				"figureurl": "http:\/\/qzapp.qlogo.cn\/qzapp\/101684736\/D1640F154C32F1E4676A5BB0EBAA9868\/30",
//				"figureurl_1": "http:\/\/qzapp.qlogo.cn\/qzapp\/101684736\/D1640F154C32F1E4676A5BB0EBAA9868\/50",
//				"figureurl_2": "http:\/\/qzapp.qlogo.cn\/qzapp\/101684736\/D1640F154C32F1E4676A5BB0EBAA9868\/100",
//				"figureurl_qq_1": "http://thirdqq.qlogo.cn/g?b=oidb&k=Z9rxm8oUoa4vQybVd0ysww&s=40&t=1502759387",
//				"figureurl_qq_2": "http://thirdqq.qlogo.cn/g?b=oidb&k=Z9rxm8oUoa4vQybVd0ysww&s=100&t=1502759387",
//				"figureurl_qq": "http://thirdqq.qlogo.cn/g?b=oidb&k=Z9rxm8oUoa4vQybVd0ysww&s=140&t=1502759387",
//				"figureurl_type": "1",
//				"is_yellow_vip": "0",
//				"vip": "0",
//				"yellow_vip_level": "0",
//				"level": "0",
//				"is_yellow_year_vip": "0"
//		}
			//解析数据
			if (userInfoRs != null) {
				JSONObject jsonObject = JSON.parseObject(userInfoRs);
				String nickName = jsonObject.getString("nickname");
				if (nickName != null) {
					User user = new User();
					user.setNickname(nickName);
					return new ResponsePlafe(0001, "获取用户信息成功", user);
				}
			}
		}catch (Exception e){
			LOG.info("接口调用失败 请重试");
			return new ResponsePlafe(0002,"调用qqAPI失败",null);
		}
		return null;
	}


	@RequestMapping(value="/ceshi")
	public String ceshi(HttpServletRequest request){
		LOG.info("-----------------接口访问----------------");
		String userId = request.getParameter("userId");
		return "success";
	}


	

}
