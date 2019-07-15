package top.yistar.wx.wxapp.mini.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxpay.sdk.WXPay;

import top.yistar.wx.wxapp.entity.ResponsePlafe;
import top.yistar.wx.wxapp.mini.pay.config.IpAddressGet;
import top.yistar.wx.wxapp.mini.pay.config.MyWxConfig;

@RestController
public class WxMiniUserInfoController {

	@PostMapping(value = "saveUserInfo")
	public ResponsePlafe updateUserInfo() {

		return null;
	}

	@RequestMapping(value="/minipay")
	public ResponsePlafe payment(HttpServletRequest req,@RequestBody String productNo) {
		
		if(!StringUtils.hasText(productNo)) {
			return new ResponsePlafe(001,"参数出错",null);
		}
		//商品订单号
		//String uuid = System.currentTimeMillis()+"ch";
		String orderNo = "18374812374819279431";
		// String itemid="23424242";// 2.商品的id
				// String payment="88";// 3.支付金额
				// 沙箱环境
				// boolean useSandbox =true;
				// 1.获取支付订单号

				// 2 获取当前操作用户的ip
				String ipAddress = IpAddressGet.getIpAddr(req);

				String code_url = "";
				MyWxConfig config = new MyWxConfig();
				// 生产环境
				try {
				WXPay wxpay = new WXPay(config);
				//沙箱环境
				// WXPay wxpay = new WXPay(config,SignType.MD5, useSandbox);
				Map<String,String> data = new HashMap<String, String>();
				data.put("body", "uniqueKing小程序商城");// 商品描述
				data.put("out_trade_no", orderNo);// 商户的订单号
				data.put("device_info", "chic");// 设备号pc 端填写web
				data.put("fee_type", "CNY");// 标价币种 此处是人民币CNY
				data.put("total_fee", "530000");// 标价金额 int 88 单位分
				data.put("spbill_create_ip", ipAddress);// 终端ip（用户的ip）
				data.put("notify_url", "http://www.yistar.top/wx_mini/notify");// 通知地址
				//JSAPI ------ 小程序支付--
				// NATIVE ---- 扫码支付
				data.put("trade_type", "JSAPI"); // 交易类型 此处指定为扫码支付
				// 微信支付下单请求
				// data.put("product_id",itemid );//商品的id 非必传  小程序支付必传
				data.put("product_id",productNo );
				Map<String, String> resp;
				resp = wxpay.unifiedOrder(data);
				System.out.println(resp);
				code_url = resp.get("code_url");
				Double pays = Double.parseDouble("530000")*0.01;
				req.setAttribute("orderid", orderNo);
				req.setAttribute("price", pays);
				req.setAttribute("code_url", code_url);
				} catch (Exception e) {
					e.printStackTrace();
					return new ResponsePlafe(001,"调用下单接口出错",null);
				}
				
				//TODO
				//调用微信小程序  需要的参数
				//appId
				//timeStamp
				//nonceStr
				//package   统一下单接口返回的 prepay_id 参数
				//signType
				
				
		
		return null;
	}

}
