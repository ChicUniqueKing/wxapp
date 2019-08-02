package top.yistar.wx.wxapp;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import top.yistar.wx.wxapp.util.LogUtil;

@SpringBootApplication
@ServletComponentScan
public class WxappApplication {
	
	private static final Logger LOG = LogUtil.getLog(WxappApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WxappApplication.class, args);
	}

	@PostConstruct
	public void serverInit() {
		LOG.info("------------------------------服务器启动  测试 jenkins自动部署功能-----------");
	}

}
