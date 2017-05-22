package com.xbongbong.controller;

import com.alibaba.fastjson.JSON;
import com.xbongbong.base.BaseController;
import com.xbongbong.component.RedisClient;
import com.xbongbong.socket.XbbWebSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-18
 * Time: 18:29
 */
@RestController
@RequestMapping(value = "/unicast", produces = "application/json;charset=UTF-8")
public class UnicastController extends BaseController {

    private static final Logger LOG = LogManager.getLogger(UnicastController.class);

    protected static final String TAG = "UnicastController";
    private static final String REDIS_WEB_SOCKET_TASK_QUEUE_KEY = "socket-task-queue";

    @Bean
    public XbbWebSocket webSocket() {
        return new XbbWebSocket();
    }

    @Autowired
    private RedisClient redisClient;

    /**
     * 将单播信息插入Redis队列
     *
     * @param message
     * @return
     */
    @RequestMapping("/push2Redis")
    public String push2Redis(String message) {

        try {
            redisClient.lpush(REDIS_WEB_SOCKET_TASK_QUEUE_KEY, message);
            return formatSuccessResponse();
        } catch (Exception e) {
            LOG.info("插入到 Redis 任务队列 -> " + REDIS_WEB_SOCKET_TASK_QUEUE_KEY + " 失败！");
            e.printStackTrace();
            return formatErrorResponse(-1, "false");
        }
    }

    /**
     * 将单播信息列表插入Redis队列
     *
     * @param message
     * @return
     */
    @RequestMapping("/pushList2Redis")
    public String pushList2Redis(String message) {
        try {
            List<String> webSocketTaskMessageList = JSON.parseArray(message, String.class);
            redisClient.lpushList(REDIS_WEB_SOCKET_TASK_QUEUE_KEY, webSocketTaskMessageList);
            return formatSuccessResponse();
        } catch (Exception e) {
            LOG.info("插入到 Redis 任务队列 -> " + REDIS_WEB_SOCKET_TASK_QUEUE_KEY + " 失败！");
            e.printStackTrace();
            return formatErrorResponse(-1, "false");
        }
    }
}
