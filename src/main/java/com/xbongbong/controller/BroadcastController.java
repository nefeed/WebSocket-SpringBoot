package com.xbongbong.controller;

import com.alibaba.fastjson.JSON;
import com.xbongbong.base.BaseController;
import com.xbongbong.component.RedisClient;
import com.xbongbong.entity.BroadcastMessageEntity;
import com.xbongbong.entity.PushMessageEntity;
import com.xbongbong.socket.XbbWebSocket;
import com.xbongbong.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-18
 * Time: 18:29
 */
@RestController
@RequestMapping(value = "/broadcast", produces = "application/json;charset=UTF-8")
public class BroadcastController extends BaseController {
    protected static final String TAG = "BroadcastController";

    @Bean
    public XbbWebSocket webSocket() {
        return new XbbWebSocket();
    }

    @Autowired
    private RedisClient redisClient;

    /**
     * 紧急广播信息（只有标题，没有内容，立刻推送）
     *
     * @param title 广播标题
     * @param url   广播对应的 Html 地址
     * @return
     */
    @RequestMapping("/urgent")
    public String broadcastUrgent(String title, String url) {
        PushMessageEntity message = new PushMessageEntity(title, "", url);
        BroadcastMessageEntity broadcastMessage = new BroadcastMessageEntity(JSON.toJSONString(message), DateUtil.getInt());
        try {
            webSocket().sendBroadcast(JSON.toJSONString(broadcastMessage));
            return formatSuccessResponse();
        } catch (Exception e) {
            return formatErrorResponse(-1, "false");
        }
    }

    /**
     * 广播信息
     *
     * @param title    广播标题
     * @param content  广播内容
     * @param url      广播对应的 Html 地址
     * @param pushTime 广播推送时间
     * @return
     */
    @RequestMapping("/common")
    public String broadcastCommon(final String title, final String content, final String url, final int pushTime) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PushMessageEntity message = new PushMessageEntity(title, content, url);
                    BroadcastMessageEntity broadcastMessage = new BroadcastMessageEntity(JSON.toJSONString(message), pushTime);
                    boolean flag = true;
                    while (flag) {
                        if (DateUtil.getInt() > pushTime) {
                            flag = false;
                            webSocket().sendBroadcast(JSON.toJSONString(broadcastMessage));
                        }
                    }
                }
            }).start();
            return formatSuccessResponse();
        } catch (Exception e) {
            return formatErrorResponse(-1, "false");
        }
    }
}
