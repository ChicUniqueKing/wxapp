package top.yistar.wx.wxapp.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.nullValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;

public class HttpUtil {

	private static CloseableHttpClient httpClient;
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		cm.setDefaultMaxPerRoute(20);
		cm.setDefaultMaxPerRoute(50);
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static String PostMap(String url, Map<String, String> map) {

		String responseContent = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
			response = httpClient.execute(post);
			if (response != null && response.getStatusLine().getStatusCode() == 200) {
				entity = response.getEntity();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				Logger logger = LogUtil.getLog(HttpUtil.class);
				logger.info("postMap 调用结果：{}", responseContent.toString());
			}
			return responseContent;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (httpClient != null) {
				try {
					httpClient.close();
					if (response != null) {
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

	/**
	 * 
	 * @param url     请求url
	 * @param reqType 请求方式
	 * @param reqData 请求数据
	 * @return
	 */
	public static String invokeRemote(String url, String reqType, Map<String, String> reqData) {

		InputStream in = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		OutputStream out = null;
		try {
			URL Uri = new URL(url);
			connection = (HttpURLConnection) Uri.openConnection();
			connection.setConnectTimeout(50000);// 超时时间
			connection.setRequestMethod(reqType);// 请求方式
			//connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
			//connection.setRequestProperty("Accept", "application/json");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			// 设置请求参数
			/*
			 * for(String key:reqData.keySet()) { connection.addRequestProperty(key,
			 * reqData.get(key)); }
			 */
			// System.out.println(connection.getRequestProperty("name"));
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			// 获取输出流
			out = connection.getOutputStream();
			DataOutputStream outs = new DataOutputStream(out);
			// 输出流里写入POST参数
			/*StringBuffer buffer1 = new StringBuffer();
			for (String key : reqData.keySet()) {
				buffer1.append(key + "=" + reqData.get(key));
			}*/
			outs.writeBytes(JSON.toJSONString(reqData));
			outs.flush();
			outs.close();
			in = connection.getInputStream();
			inputStreamReader = new InputStreamReader(in);
			reader = new BufferedReader(inputStreamReader);
			String line = "";
			StringBuffer buffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			return buffer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (inputStreamReader != null)
					inputStreamReader.close();
				if (reader != null)
					reader.close();
				// if(connection!=null)connection.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

	public static String getInvoke(String url, Map<String, String> map) {

		HttpGet httpGet = new HttpGet();
		httpGet.addHeader("Content-Type", "application/Json; charset=UTF-8");
		try {
			httpGet.setURI(new URI(url));
			BasicHttpParams params = new BasicHttpParams();
			for (String key : map.keySet()) {
				params.setParameter(key, map.get(key));
			}
			httpGet.setParams(params);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			httpClient.execute(httpGet);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("name", "15850781443");
		/*String json = JSON.toJSONString(map);
		System.out.println("=============??data"+json);
		String str = invokeRemote("http://localhost:8888/test", "POST", map);
		System.out.println(str);*/
	    /*String rs = post("http://localhost:8888/test",json);
	    System.out.println(rs);*/
		// getInvoke("http://localhost:8888/test",map);
		String rs = post("http://localhost:9990/test", JSON.toJSONString(map));
		System.out.println(rs);
	}

	public static String post(String url, String jsonString) {
		CloseableHttpResponse response = null;
		BufferedReader in = null;
		String result = "";
		try {
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
					.setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
			httpPost.setConfig(requestConfig);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("Content-type", "application/json; charset=utf-8");
			httpPost.setHeader("Accept", "application/json");
			httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
			response = httpClient.execute(httpPost);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			//String NL = System.getProperty("line.separator");
			//System.out.println("==========================================================="+NL);
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != response) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
