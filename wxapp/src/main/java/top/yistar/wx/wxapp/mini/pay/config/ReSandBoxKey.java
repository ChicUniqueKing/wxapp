package top.yistar.wx.wxapp.mini.pay.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import javax.lang.model.element.NestingKind;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.github.wxpay.sdk.WXPayUtil;

/**
 * 获取沙箱key
 * @author Administrator
 *
 */
public class ReSandBoxKey {
	
public static void main(String[] args) {
	
	String nonce_str =WXPayUtil.generateNonceStr();//随机字符串
    String mch_id ="11473623";//商户号
	
    WxConfig wx = new WxConfig();
    Map<String, Object> map =new HashMap<>();
	map.put("nonce_str", nonce_str);
	map.put("mch_id", mch_id);
	String sign =CreateSign.getSign(map,wx.getKey());
	ReSandBoxKey a = new ReSandBoxKey();

	try {
	
	Map<String, String> map2 = new HashMap<>();
	map2.put("nonce_str", nonce_str);
	map2.put("mch_id", mch_id);
	map2.put("sign", sign);
	
	//获取沙箱密钥请求地址
	String strurl =a.requestWithoutCert("https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey", map2,8000,8000 );
	
	System.out.println(strurl);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
		//HttpUtil.postData(urlStr, data);
	}
	
	public String requestWithoutCert(String strUrl, Map<String, String> reqData, int connectTimeoutMs,
			int readTimeoutMs) throws Exception {
		String UTF8 = "UTF-8";
		String reqBody = ReSandBoxKey.mapToXml(reqData);
		System.out.println(reqBody);
		URL httpUrl = new URL(strUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setConnectTimeout(connectTimeoutMs);
		httpURLConnection.setReadTimeout(readTimeoutMs);
		httpURLConnection.connect();
		OutputStream outputStream = httpURLConnection.getOutputStream();
		outputStream.write(reqBody.getBytes(UTF8));

		 if (httpURLConnection.getResponseCode()!= 200) {
	 throw new Exception(String.format("HTTP response code is %d, not 200",
		 httpURLConnection.getResponseCode()));
	 }

	
		InputStream inputStream = httpURLConnection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
		final StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		String resp = stringBuffer.toString();
		if (stringBuffer != null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// if (httpURLConnection!=null) {
		// httpURLConnection.disconnect();
		// }

		return resp;
	}
	//字典排序
	public String buildSignStr(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, Object> sortParams = new TreeMap<String, Object>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, Object> entry : sortParams.entrySet()) {
            if (sb.length()!=0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }
	
	
	 public static String mapToXml(Map<String, String> data) throws Exception {
	        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
	        org.w3c.dom.Document document = documentBuilder.newDocument();
	        org.w3c.dom.Element root = document.createElement("xml");
	        document.appendChild(root);
	        for (String key: data.keySet()) {
	            String value = data.get(key);
	            if (value == null) {
	                value = "";
	            }
	            value = value.trim();
	            org.w3c.dom.Element filed = document.createElement(key);
	            filed.appendChild(document.createTextNode(value));
	            root.appendChild(filed);
	        }
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        DOMSource source = new DOMSource(document);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        transformer.transform(source, result);
	        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
	        try {
	            writer.close();
	        }
	        catch (Exception ex) {
	        }
	        return output;
	    }
	
}
