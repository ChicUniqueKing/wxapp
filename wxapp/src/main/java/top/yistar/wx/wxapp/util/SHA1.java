package top.yistar.wx.wxapp.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA1 {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SHA1.class);

		
		private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f' };
	 
		private static String getFormattedText(byte[] bytes) {
			int len = bytes.length;
			StringBuilder buf = new StringBuilder(len * 2);
			// 把密文转换成十六进制的字符串形式
			for (int j = 0; j < len; j++) {
				buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
				buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
			}
			String str = buf.toString();
			LOGGER.info("===============加密的值是={}",str);
			return str;
		}
		/**
		 * 微信接入需要的加密
		 * @param str
		 * @return
		 */
		public static String encode(String str) {
			if (str == null) {
				return null;
			}
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
				messageDigest.update(str.getBytes());
				return getFormattedText(messageDigest.digest());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		public static String getSHA1(String token, String timestamp, String nonce) {
			
			List<String> list = new ArrayList<>();
			list.add(token);
			list.add(timestamp);
			list.add(nonce);
			Collections.sort(list, new SpellComparator());
			StringBuffer buffer = new StringBuffer();
			for(String s:list) {
				buffer.append(s);
			}
			return encode(buffer.toString());
		}
}

class SpellComparator implements Comparator {
	 public int compare(Object o1, Object o2) {
	  try {
	   // 取得比较对象的汉字编码，并将其转换成字符串
	   String s1 = new String(o1.toString().getBytes("GB2312"), "ISO-8859-1");
	   String s2 = new String(o2.toString().getBytes("GB2312"), "ISO-8859-1");
	   // 运用String类的 compareTo（）方法对两对象进行比较
	   return s1.compareTo(s2);
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  return 0;
	 }
}
