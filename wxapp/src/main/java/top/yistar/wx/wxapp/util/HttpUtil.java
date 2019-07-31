package top.yistar.wx.wxapp.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.nullValue;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import top.yistar.wx.wxapp.exception.RquesetMethodException;

public class HttpUtil {


	/**
	 * 请求方式
	 *
	  **/
	public static final  String GET ="GET";

	public static final  String POST ="POST";




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

			e.printStackTrace();
		} finally {

			try {
				if (reader != null)
					reader.close();
				if (inputStreamReader != null)
					inputStreamReader.close();
				if (reader != null)
					reader.close();
				if(connection!=null)connection.disconnect();
			} catch (IOException e) {
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

		String key = "5e00f78a6de2cd2a687c0e9b753e2318";

		Map<String, Object> map = new HashMap<>();
		map.put("name", "15850781443");
		/*String json = JSON.toJSONString(map);
		System.out.println("=============??data"+json);
		String str = invokeRemote("http://localhost:8888/test", "POST", map);
		System.out.println(str);*/
	    /*String rs = post("http://localhost:8888/test",json);
	    System.out.println(rs);*/
		// getInvoke("http://localhost:8888/test",map);
		//String rs = post("http://www.yistar.top/wx_mini/test", JSON.toJSONString(map));
		//String url = "http://localhost:8888/test";
//		map.put("version","v1");
//		map.put("cityid","101120201");
//		map.put("city","青岛");
//		map.put("ip","27.193.132.255");
//		map.put("callback","dd");
		//String url ="https://www.tianqiapi.com/api/";
		map.put("key",key);
		map.put("city","430300");
		map.put("extensions","all");
		map.put("output","JSON");
		String url ="https://restapi.amap.com/v3/weather/weatherInfo?parameters";
		//String url ="http://localhost:8888/test";
		//String url = "https://www.yistar.top/wx_mini/test";
		String rs =inVokeRemote(url,map,"GET");
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



	// Http工具远程接口调用工具   上面的工具都有问题 这个已经经过测试
	public static  String inVokeRemote(String url,Map<String,Object> map,String requestType){

		InputStream in =null;
		InputStreamReader  reader  = null;
		BufferedReader bufferedReader = null;
		try {
			List<NameValuePair> pairs = new ArrayList<>();
			for(String key : map.keySet()){
				NameValuePair pair = new NameValuePair() {
					@Override
					public String getName() {
						return key;
					}
					@Override
					public String getValue() {
						return map.get(key).toString();
					}
				};
				pairs.add(pair);
			}

			HttpEntity entity = getEntity(url,pairs,requestType);
			in = getInpputStream(entity);
			if(in!=null){
				reader = new InputStreamReader(in);
				bufferedReader = new BufferedReader(reader);
			}
			String line = "";
			StringBuffer buffer = new StringBuffer();
			while((line =bufferedReader.readLine())!=null){
				buffer.append(line);
			}
			return buffer.toString();
		} catch (RquesetMethodException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
		try {
			if(bufferedReader!=null)bufferedReader.close();
			if(reader!=null)reader.close();
			if(in!=null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	  *@Author  ChicUniqueKing
	  *@Description
	  *@Date 9:58 2019/7/31
	  *@Param [url, pairs, requestType]
	  *@Return org.apache.http.HttpEntity
	  **/
	public static HttpEntity getEntity(String url,List<NameValuePair> pairs,String requestType) throws RquesetMethodException, IOException {
		HttpEntity entity = null;
		HttpClient client = new DefaultHttpClient();
		HttpRequest request =null;
		if(requestType.equals(GET)){
			StringBuilder builder = new StringBuilder(url);
			builder.append("?");
			for(NameValuePair NameValuePair:pairs){
				builder.append(NameValuePair.getName()).append("=").append(NameValuePair.getValue()).append("&");
			}
			//删除builder 的最后一个字符
			builder.deleteCharAt(builder.length()-1);
			request = new HttpGet(builder.toString());
		}else if(requestType.equals(POST)){
			request = new HttpPost(url);
			UrlEncodedFormEntity reEntity = new UrlEncodedFormEntity(pairs,"utf-8");
			((HttpPost) request).setEntity(reEntity);
		}else{
			throw new RquesetMethodException("reqquest 方式错误");
		}
		HttpResponse response = client.execute((HttpUriRequest) request);
		if(response.getStatusLine().getStatusCode()==200){
			entity = response.getEntity();
		}
		return entity;
	}

		public static InputStream getInpputStream(HttpEntity entity) throws IOException {
			return entity!=null? entity.getContent():null;
		}





}


