package com.xbongbong;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class XbbWebSocketApplication {

	public static void main(String[] args) {
//		SpringApplication.run(XbbWebSocketApplication.class, args);
		new SpringApplicationBuilder(XbbWebSocketApplication.class)
				.bannerMode(Banner.Mode.CONSOLE) // 控制 Banner 的显示模式，Banner 在resources中的 Banner.txt内，可以前往 http://patorjk.com/software/taag 进行自定义
				.run(args);
	}
}
