package top.yistar.wx.wxapp.mini.pay.config;

import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

public class MyWxConfig implements WXPayConfig{

	@Override
	public String getAppID() {
		// TODO Auto-generated method stub
				return "wx4361973f8c3a1b2f";
	}

	@Override
	public String getMchID() {
		// TODO Auto-generated method stub
		return "1507388901";
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "ad1f6a1df23zxdf5a1ds21f23xz1f5as";
	}

	@Override
	public InputStream getCertStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		// TODO Auto-generated method stub
		return 0;
	}

}
