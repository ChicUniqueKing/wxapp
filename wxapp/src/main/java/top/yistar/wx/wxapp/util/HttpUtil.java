package top.yistar.wx.wxapp.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

public class HttpUtil {
	
	
	
	public static String PostMap(String  url,Map<String, String> map) {
		
		String responseContent = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for(Map.Entry<String, String> entry:map.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpResponse response =null;
		HttpEntity entity =null;
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
				response =httpClient.execute(post);
				if(response !=null && response.getStatusLine().getStatusCode()==200) {
					entity = response.getEntity();
					responseContent = EntityUtils.toString(entity, "UTF-8");
					Logger logger = LogUtil.getLog(HttpUtil.class);
					logger.info("postMap 调用结果：{}",responseContent.toString());
				}
				return responseContent;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
				if(httpClient!=null) {
					try {
						httpClient.close();
						if(response!=null) {
							response.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		return null;
	}
	
	
	public static String invokeRemote(String url,String reqType,Map<String, String> reqData) {
		
		InputStream in = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try {
			URL Uri = new URL(url);
			HttpURLConnection connection =(HttpURLConnection) Uri.openConnection();
			//connection.setRequestProperty(reqData.keySet().);
			connection.connect();
			//connection.setRequestMethod(reqType);
			in = connection.getInputStream();
			inputStreamReader = new InputStreamReader(in);
			reader = new BufferedReader(inputStreamReader);
			String line ="";
			StringBuffer buffer = new StringBuffer();
			while((line=reader.readLine())!=null) {
				buffer.append(line);
			}
			
			return buffer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					if(reader!=null)reader.close();
					if(inputStreamReader!=null)inputStreamReader.close();
					if(reader!=null)reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
		return null;
		
	}
	
	
	
	
	

}
