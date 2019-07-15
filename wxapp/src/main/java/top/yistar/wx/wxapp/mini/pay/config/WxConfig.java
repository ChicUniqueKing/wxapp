package top.yistar.wx.wxapp.mini.pay.config;

import java.io.InputStream;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;





/**
 * 
 * @author xiaosa
 * @className WxConfig.java
 * @descripe 微信支付的配置类(即微信商户的信息)
 * @data 2018年6月12日
 */

public class WxConfig  extends WXPayConfig {

	
	/* private byte[] certData;
	
	  public WxConfig() throws Exception {
	      String certPath = "/yehuishou/src/main/resources/apiclient_cert.p12"; //证书的存放位置
	        File file = new File(certPath);
	        InputStream certStream = new FileInputStream(file);
	        this.certData = new byte[(int) file.length()];
	        certStream.read(this.certData);
	        certStream.close();
	    }*/
	
	
	
	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
		return "wx4361973f8c3a1b2f";
	}

	@Override
	public InputStream getCertStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "ad1f6a1df23zxdf5a1ds21f23xz1f5as"; //商户秘钥
	}

	@Override
	public String getMchID() {  //商户号   需要申请
		// TODO Auto-generated method stub
		return "1507388901";   
	}


	   public int getHttpConnectTimeoutMs() {
	        return 6*1000;
	    }

	    /**
	     * HTTP(S) 读数据超时时间，单位毫秒
	     *
	     * @return
	     */
	    public int getHttpReadTimeoutMs() {
	        return 8*1000;
	    }

	    /**
	     * 获取WXPayDomain, 用于多域名容灾自动切换
	     * @return
	     */
		public IWXPayDomain getWXPayDomain() {
			// TODO Auto-generated method stub
			return new IWXPayDomain() {
				
				@Override
				public void report(String domain, long elapsedTimeMillis, Exception ex) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public DomainInfo getDomain(WXPayConfig config) {
					// TODO Auto-generated method stub
					return new DomainInfo("api.mch.weixin.qq.com", true);
				}
			};
		}

	    /**
	     * 是否自动上报。
	     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
	     *
	     * @return
	     */
	    public boolean shouldAutoReport() {
	        return true;
	    }

	    /**
	     * 进行健康上报的线程的数量
	     *
	     * @return
	     */
	    public int getReportWorkerNum() {
	        return 6;
	    }


	    /**
	     * 健康上报缓存消息的最大数量。会有线程去独立上报
	     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
	     *
	     * @return
	     */
	    public int getReportQueueMaxSize() {
	        return 10000;
	    }

	    /**
	     * 批量上报，一次最多上报多个数据
	     *
	     * @return
	     */
	    public int getReportBatchSize() {
	        return 10;
	    }


	/*
	private byte[] certData;

    public WxConfig() throws Exception {
      String certPath = "/yehuishou/src/main/resources/apiclient_cert.p12"; //证书的存放位置
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

  //测试账号的沙箱密钥；3639bc1370e105aa65f10cd4fef2a3ef
   
	
	
    public String getAppID() {
     return "wxab8acb865bb1637e";
    //return "wx4361973f8c3a1b2f";  //用户appid
    }

    public String getMchID() {   
       return "11473623";
    	//return "1507388901";   //微信支付的商户号
    }
//3639bc1370e105aa65f10cd4fef2a3ef  测试沙箱密钥
    
  //用户的API密钥 c0ec5d9cbc3f86007e978120c076b653
    public String getKey() {
       return "2ab9071b06b9f739b950ddb41db2690d";
    	//return "09be9ecebdfe724cc11dd1e81f1115c5";
       //return "3639bc1370e105aa65f10cd4fef2a3ef";
      
    }

    public InputStream getCertStream() {//获取证书的输入流
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {//连接超时时间
        return 8000;
    }

    public int getHttpReadTimeoutMs() {  //读取数据超时的时间
        return 10000;
    }

   public InputStream getCertStream() {
	// TODO Auto-generated method stub
	return null;
} 
	*/

}  

