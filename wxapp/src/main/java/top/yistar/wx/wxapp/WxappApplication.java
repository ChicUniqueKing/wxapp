package top.yistar.wx.wxapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WxappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WxappApplication.class, args);
	}

}
