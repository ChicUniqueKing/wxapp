package top.yistar.wx.wxapp.wxcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.yistar.wx.wxapp.util.HttpUtil;

/**
 * 
 * @author ChicUniqueKing
 *  第三方qq登录 回调
 */
@Controller
public class ThreedGroupLoginController {
	
	public static final Logger LOG = LoggerFactory.getLogger(ThreedGroupLoginController.class);
	
	
	public static final String QQ_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
	
	/**
	 *  step1: https://graph.qq.com/oauth2.0/authorize 获取认证code
	 */
	//https://www.yistar.top/callback?code=8D234BE8D947163E1CE0F772249E4BDE
	
	@RequestMapping(value="/")
	public String loginForQQCallBack(HttpServletRequest request,HttpServletResponse response) {
		String code = request.getParameter("code");
		LOG.info("--=================>>qq登录回调------》》》");
		/*if(!StringUtils.hasLength(code)) {
			return new ResponsePlafe(003,"","获取qq登录回调code失败");
			return "redirect:https://www.yistar.top/line.html?rs=failed";
		}
		try {
		//根据code获取token
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "authorization_code");
		params.put("client_id", "101684736");
		params.put("client_secret", "6f2c34d716f7add016cbe94db7f103af");
		params.put("code", code);
		params.put("redirect_uri", "https://www.yistart.top");
		//String rs =HttpUtil.postData(QQ_TOKEN_URL, JSON.toJSONString(params), "");
		String rs = "";
		LOG.info("------获取token-------"+rs);
		if(!StringUtils.hasLength(rs)) {
			return "redirect:https://www.yistar.top/line.html?rs=failed";
		}
		JSONObject jObject = new JSONObject();
		jObject.parseObject(rs);
		String token = jObject.getString("access_token");
		Map<String, String> returnData = new HashMap<>();
		returnData.put("access_token", token);//qq登录token
		returnData.put("redirect_uri", "https://www.yistart.top");
		return new ResponsePlafe(001,"success",returnData);
		return "redirect:https://www.yistar.top/line.html?rs=failed";
		}catch(Exception e) {
			LOG.info("调用qq获取token异常",e.getMessage());
			//return new ResponsePlafe(004,"","调用qq获取token异常");
			return "redirect:https://www.yistar.top";
		}*/
		//access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14 
		//登录态token
		String access_token = request.getParameter("access_token");
		
		String refresh_token = request.getParameter("refresh_token");
		LOG.info("-----------"+access_token+"===="+refresh_token);
		
		//request.setAttribute("access_token", access_token);
		HttpSession session = request.getSession();
		request.setAttribute("session", session);
		Cookie cookie = new Cookie("access_token", access_token);
		cookie.setDomain("www.yistar.top");
		cookie.setPath("/");
		response.addCookie(cookie);
		try {
			response.addHeader("access_token", access_token);
			/*response.addHeader("Cookie", access_token);*/
			//response.sendRedirect("https://www.yistar.top");
		} catch (Exception e) {
			LOG.info("==========>>>.重定向 失败"+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:https://www.yistar.top";
		
		
	}
	
	
	
	public static void main(String arg []) {
		
		/*System.out.println(new Date().getTime());
		System.out.println(System.currentTimeMillis());*/
		
		//根据code获取token
				Map<String, String> params = new HashMap<String, String>();
				params.put("grant_type", "authorization_code");
				params.put("client_id", "101684736");
				params.put("client_secret", "6f2c34d716f7add016cbe94db7f103af");
				params.put("code", "B5AF6092F7C4C556BB7CB97F1747169C");
				params.put("redirect_uri", "https://www.yistart.top");
				StringBuffer buffer = new StringBuffer(QQ_TOKEN_URL);
				
				for(String key:params.keySet()) {
					buffer.append("&").append(key).append("=").append(params.get(key));
				}
			String url = buffer.toString();
			String url_n = url.trim().replaceFirst("&", "?");
					LOG.info("=============>>>>>>>>"+url_n);
				//String rs =	top.yistar.wx.wxapp.mini.pay.config.HttpUtil.postData(url_n, params.toString());
				String rs = HttpUtil.invokeRemote(url_n, "GET", new HashMap<>());
				LOG.info("------获取token-------"+rs);
		
	}
	

}
