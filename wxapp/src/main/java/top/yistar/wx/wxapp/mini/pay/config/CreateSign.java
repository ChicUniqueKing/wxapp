package top.yistar.wx.wxapp.mini.pay.config;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


import javax.xml.parsers.ParserConfigurationException;


import org.xml.sax.SAXException;

import com.github.wxpay.sdk.WXPayUtil;


public class CreateSign {
	
	    /**
	     * 签名算法
	     * 
	     * @param map 要参与签名的数据对象
	     * @return 签名
	     * @throws IllegalAccessException
	     */
	    public static String getSign(Map<String, Object> map,String key){
	        ArrayList<String> list = new ArrayList<String>();
	        for (Map.Entry<String, Object> entry : map.entrySet()){
	            if ( !"".equals(entry.getValue())){
	                list.add(entry.getKey() + "=" + entry.getValue() + "&");
	            }
	        }
	        int size = list.size();
	        String[] arrayToSort = list.toArray(new String[size]);
	        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < size; i++){
	            sb.append(arrayToSort[i]);
	        }
	        String result = sb.toString();
	        result += "key="+key;
	        System.out.println("签名："+result);
	        try {
				result = WXPayUtil.MD5(result).toUpperCase();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return result;
	    }
	    public static String createSign(String characterEncoding,SortedMap<String,Object> parameters){
	StringBuffer sb = new StringBuffer();
	Set es = parameters.entrySet();
	Iterator it = es.iterator();
	while(it.hasNext()) {
	Map.Entry entry = (Map.Entry)it.next();
	String k = (String)entry.getKey();
	Object v = entry.getValue();
	if(null != v && !"".equals(v) 
	&& !"sign".equals(k) && !"key".equals(k)) {
	sb.append(k + "=" + v + "&");
	}
	}
	sb.append("key=");
	System.out.println("签名："+sb.toString());
	String sign="";
	try {
		sign = WXPayUtil.MD5(sb.toString()).toUpperCase();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return sign;
	}

}
