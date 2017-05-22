package com.xbongbong.service.task;

import com.alibaba.fastjson.JSON;
import com.xbongbong.component.RedisClient;
import com.xbongbong.entity.PushMessageEntity;
import com.xbongbong.entity.UnicastMessageEntity;
import com.xbongbong.socket.XbbWebSocket;
import com.xbongbong.util.DateUtil;
import com.xbongbong.util.ExceptionUtil;
import com.xbongbong.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-08
 * Time: 14:36
 */
@Service
public class ScheduledTaskProducerService {
    protected static final String TAG = "ScheduledTaskProducerService";

    private static final String REDIS_WORK_ARRAY_KEY = "socket-task-queue";
    private static final String REDIS_TEMP_ARRAY_KEY = "socket-tmp-queue";

    private static final Logger LOG = LogManager
            .getLogger(ScheduledTaskProducerService.class);

    @Autowired
    private RedisClient redisClient;

    @Bean
    public XbbWebSocket webSocket() {
        return new XbbWebSocket();
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 3000)
//    public void reportCurrentTime() throws Exception {
//        LOG.info(" 每隔三秒执行一次：" + dateFormat.format(new Date()));
//        pushTask2Jedis(" 每隔五秒执行一次：" + dateFormat.format(new Date()));
//    }

//    @Scheduled(fixedRate = 5000)
//    public void popRedisData() throws Exception {
//        LOG.info(" 每隔五秒执行一次：" + dateFormat.format(new Date()));
//        String popMessage = popTask4Jedis();
//        if (StringUtil.isEmpty(popMessage)) {
//            return;
//        }
//        LOG.info(popMessage);
//        webSocket().sendInfo(popMessage);
//    }

    private int index = 0;

    @Scheduled(fixedRate = 150)
    public void popUnicastMessage() {
//        LOG.info(" 每隔0.15执行一次：" + dateFormat.format(new Date()));
        pushUnicastMessage();
        if ((++index) % 400 == 0) { // 每60秒输出一次当前用户和工作队列信息
            webSocket().showOnlineUserNum();
            index = 0;
            // 每十次显示一下当前队列中的数据
            try {
                LOG.info("Redis 中推送消息队列 socket-task-queue 的长度:" + redisClient.showListLength(REDIS_WORK_ARRAY_KEY));
//                LOG.info("Redis 中临时队列 socket-tmp-queue 的长度:" + redisClient.showListLength(REDIS_TEMP_ARRAY_KEY));
            } catch (Exception e) {
                LOG.error("捕获从 Redis 工作队列读取消息做处理准备时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
            }
        }
    }

//    @Scheduled(cron = "0 25 10 ? * *")
//    public void fixTimeExecution() throws Exception {
//        LOG.info(String.format(" 在指定时间 " + dateFormat.format(new Date()) + " 执行"));
//        String popMessage = popTask4Jedis();
//        if (StringUtil.isEmpty(popMessage)) {
//            return;
//        }
//        LOG.info(popMessage);
//    }

    private void pushTask2Jedis(String message) {
        // 模拟生成一个任务
        UUID taskid = UUID.randomUUID();
        //将任务插入任务队列：socket-task-queue
        try {
            redisClient.lpush(REDIS_WORK_ARRAY_KEY, "message -> " + message + "；" + taskid.toString());
            LOG.info("插入了一个新的任务： " + "message -> " + message + "；" + taskid);
        } catch (Exception e) {
            LOG.error("捕获插入 Redis 时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
        }
    }

    private String popTask4Jedis() throws Exception {
        Random random = new Random();
        //从任务队列"socket-task-queue"中获取一个任务，并将该任务放入暂存队列"socket-tmp-queue"
        String uniCastMessageJsonStr = redisClient.rpoplpush(REDIS_WORK_ARRAY_KEY, REDIS_TEMP_ARRAY_KEY);
        // 处理任务----纯属业务逻辑，模拟一下：睡觉
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //模拟成功和失败的偶然现象
        if (random.nextInt(13) % 7 == 0) {// 模拟失败的情况,概率为2/13
            //将本次处理失败的任务从暂存队列"socket-tmp-queue"中，弹回任务队列"socket-task-queue"
            LOG.error(uniCastMessageJsonStr + "处理失败，被弹回任务队列");
            redisClient.rpoplpush(REDIS_TEMP_ARRAY_KEY, REDIS_WORK_ARRAY_KEY);
            return null;
        } else {// 模拟成功的情况
            // 将本次任务从暂存队列"socket-tmp-queue"中清除
            LOG.info(uniCastMessageJsonStr + "处理成功，被清除");
            return redisClient.rpop(REDIS_TEMP_ARRAY_KEY);
        }
    }

    /**
     * 发送单播信息
     *
     * @throws Exception
     */
    private void pushUnicastMessage() {
        //从任务队列"socket-task-queue"中获取一个任务，并将该任务放入暂存队列"socket-tmp-queue"
        String messageJsonStr = null;
        try {
            messageJsonStr = redisClient.rpop(REDIS_WORK_ARRAY_KEY);
        } catch (Exception e) {
            LOG.error("捕获从 Redis 工作队列读取消息做处理准备时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
        }
        if (StringUtil.isEmpty(messageJsonStr)
                || !(messageJsonStr.startsWith("{") && messageJsonStr.endsWith("}"))) {
//            LOG.info("空信息，丢弃：" + messageJsonStr);
            return;
        }
//        try {
//            redisClient.lpush(REDIS_TEMP_ARRAY_KEY, messageJsonStr);
//        } catch (Exception e) {
//            LOG.error("捕获从 Redis 工作队列读取消息时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
//        }
//        // todo 校验消息发送的顺序性预留位置
//        try {
//            messageJsonStr = redisClient.rpop(REDIS_TEMP_ARRAY_KEY);
//        } catch (Exception e) {
//            LOG.error("捕获从 Redis 临时队列读取消息做处理准备时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
//        }

        UnicastMessageEntity uniCastMessage = JSON.parseObject(messageJsonStr, UnicastMessageEntity.class);
        Integer now = DateUtil.getInt();
        Integer pushTime = uniCastMessage.getPushTime();
        if (pushTime > now) {
            // 发送失败：未到发送信息的时间
//                LOG.warn("发送单播推送信息失败，未到消息的推送时间");
            try {
                redisClient.lpush(REDIS_WORK_ARRAY_KEY, messageJsonStr);
            } catch (Exception e) {
                LOG.error("未到达发送消息时间，捕获从 Redis 临时队列插入工作队列时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
            }
        } else {
            if ((now - pushTime) < 3 * 24 * 60 * 60) { // 3 天内的有效信息
                if (webSocket().unicastMessage(uniCastMessage.getCorpid(), uniCastMessage.getUserId(), messageJsonStr)) {
                    //  发送成功
                    LOG.info("发送单播推送信息成功：corpid -> " + uniCastMessage.getCorpid() + "； userId -> " + uniCastMessage.getUserId()
                            + "； content -> " + JSON.parseObject(uniCastMessage.getMessage(), PushMessageEntity.class).getContent());
                } else {
                    // 发送失败：用户未登陆 WebSocket
//                    LOG.error("发送单播推送信息失败，客户可能已经离线");
                    try {
                        redisClient.lpush(REDIS_WORK_ARRAY_KEY, messageJsonStr);
                    } catch (Exception e) {
                        LOG.error("正常发送消息失败，捕获从 Redis 临时队列插入工作队列时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
                    }
                }
            } else {
                // 超过 3 天未发送，垃圾信息，丢弃
                try {
                    LOG.warn("超过 3 天未发送的垃圾信息，丢弃! 消息内容：" + messageJsonStr);
                } catch (Exception e) {
                    LOG.error("捕获从 Redis 临时队列丢弃消息时异常！异常内容：" + ExceptionUtil.getStackMsg(e));
                }
            }
        }
    }
}
