package com.xbongbong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xbongbong.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class XbbWebSocketApplicationTests {

	private static final Logger LOG = LogManager
			.getLogger(XbbWebSocketApplicationTests.class);

	private int mStartTime;

	@Before
	public void beforeJunitTest() {
		mStartTime = DateUtil.getInt();
		LOG.info("========================= 单元测试开始时间：" + DateUtil.getInt() + " ==========================");
	}

	@After
	public void afterJunitTest() {
		int endTime = DateUtil.getInt();
		LOG.info("========================= 单元测试开始时间：" + endTime + " ==========================");
		LOG.info("*********************** 累计用时：" + (endTime - mStartTime) + "s ************************");
	}

	@Test
	public void contextLoads() {
		JSONObject json = (JSONObject) JSON.parse("{\"addTime\":1489827964,\"corpid\":\"1\",\"message\":\"{\\\"content\\\":\\\"新建客户 1个客户 成功，请及时跟进！1个客户 ——来自销售自动化\\\",\\\"messageUrl\\\":\\\"http://localhost:8088//opportunity/detail.html?id=240%26dd_nav_bgcolor=ffff943e\\\",\\\"title\\\":\\\"1个客户需要关注，请及时跟进\\\"}\",\"pushTime\":1489827964,\"userId\":\"1\"}");
		LOG.info(json.toJSONString());
	}

}
