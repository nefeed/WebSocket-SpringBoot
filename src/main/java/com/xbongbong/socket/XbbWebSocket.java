package com.xbongbong.socket;

import com.alibaba.fastjson.JSON;
import com.xbongbong.entity.SocketIdCardEntity;
import com.xbongbong.util.ExceptionUtil;
import com.xbongbong.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-06
 * Time: 19:19
 */
@ServerEndpoint(value = "/xbbWebSocket")
@Component
public class XbbWebSocket {

    private static final Logger LOG = LogManager
            .getLogger(XbbWebSocket.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<XbbWebSocket> xbbWebSocketSet = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String, String> sessionIdMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        xbbWebSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
//        LOG.info("有新连接加入！当前在线人数为" + getOnlineCount() + " SessionId -> " + this.session.getId());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        xbbWebSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        if (sessionIdMap.containsKey(this.session.getId())) {
            SocketIdCardEntity idCard = JSON.parseObject(sessionIdMap.get(this.session.getId()), SocketIdCardEntity.class);
            sessionIdMap.remove(this.session.getId()); // 去除 Table 中的 Session
//            LOG.info("有一连接关闭！当前在线人数为" + getOnlineCount() + " 离线客户的 SessionId：" + this.session.getId()
//                    + " corpid -> " + idCard.getCorpid() + " userId -> " + idCard.getUserId());
        }
//        else {
//            LOG.info("有一连接关闭！当前在线人数为" + getOnlineCount() + " 离线客户的 SessionId：" + this.session.getId());
//        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        if (sessionIdMap.containsKey(this.session.getId())) {
            SocketIdCardEntity idCard = JSON.parseObject(sessionIdMap.get(this.session.getId()), SocketIdCardEntity.class);
            sessionIdMap.remove(this.session.getId()); // 去除 Table 中的 Session
//            LOG.error("发生错误！ 错误由 Session -> " + this.session.getId() + "造成。"
//                    + " corpid -> " + idCard.getCorpid() + " userId -> " + idCard.getUserId());
        } else {
//            LOG.error("发生错误！ 错误由 Session -> " + this.session.getId() + "造成。");
        }
        if (error.getClass() != EOFException.class) {
            // 忽略 EOFException
            error.printStackTrace();
            LOG.error("发生错误！" + "错误信息：" + error.getMessage());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            if (StringUtil.isEmpty(message)) {
                return;
            }
//            LOG.info("来自客户端 SessionId -> " + session.getId() + "的消息:" + message);
            message = message.trim();
            if (message.startsWith("{") && message.endsWith("}")) {
                SocketIdCardEntity idCard = JSON.parseObject(message, SocketIdCardEntity.class);
                if (StringUtil.isEmpty(idCard.getCorpid()) || StringUtil.isEmpty(idCard.getUserId())) {
                    LOG.warn("客户 SessionId：" + this.session.getId()
                            + "传递的唯一标识缺少主要信息!\n其信息内容：" + message);
                    return;
                }
                if (sessionIdMap.containsKey(session.getId())) {
                    SocketIdCardEntity originIdCard
                            = JSON.parseObject(sessionIdMap.get(this.session.getId()), SocketIdCardEntity.class);
                    if (!(originIdCard.equals(idCard))) {
                        LOG.warn("客户 SessionId：" + this.session.getId()
                                + "； corpid -> " + originIdCard.getCorpid()
                                + "； userId -> " + originIdCard.getUserId()
                                + "； platform -> " + originIdCard.getPlatform() + "想要修改 Session 唯一标识，已被阻止"
                                + "\n其信息内容：" + message);
                    }
                    return;
                }
                for (Map.Entry<String, String> entry : sessionIdMap.entrySet()) {
                    String idCardStr = entry.getValue();
                    if (StringUtil.isEmpty(idCardStr)
                            || !(idCardStr.startsWith("{") && idCardStr.endsWith("}"))) {
                        continue;
                    }
                    SocketIdCardEntity originIdCard = JSON.parseObject(idCardStr, SocketIdCardEntity.class);
                    if (originIdCard.equals(idCard)) {
                        String tempSessionId = entry.getKey();
                        sessionIdMap.remove(tempSessionId);
                        for (XbbWebSocket item : xbbWebSocketSet) {
                            if (item.session.getId().equals(tempSessionId)) {
                                xbbWebSocketSet.remove(item);
                                break;
                            }
                        }
//                        LOG.warn("客户 SessionId：" + this.session.getId()
//                                + "； corpid -> " + idCard.getCorpid()
//                                + "； userId -> " + idCard.getUserId() + "被其他用户SessionId：" + tempSessionId + "占用，已踢出前用户！");
                        break;
                    }
                }
//                LOG.info("当前在线人数为:" + getOnlineCount() + "\n客户 SessionId：" + this.session.getId()
//                        + "添加唯一标识 corpid -> " + idCard.getCorpid()
//                        + " userId -> " + idCard.getUserId());
                sessionIdMap.putIfAbsent(session.getId(), message);
            }
        } catch (Exception e) {
            LOG.error("捕获异常，由客户 SessionId：" + this.session.getId() + "造成！异常内容：" + ExceptionUtil.getStackMsg(e));
        }
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message); // 同步发送消息，多条消息会阻塞排队
        } catch (IOException e) {
            LOG.error("捕获IO异常，由客户 SessionId：" + this.session.getId() + "造成！异常内容：" + ExceptionUtil.getStackMsg(e));
        }
        //this.session.getAsyncRemote().sendText(message); // 异步发送消息，多条消息会异步发送
    }

    /**
     * 单播信息：如果当前 Session 在线就发送信息并返回 true，否则返回 false
     *
     * @param corpid
     * @param userId
     * @param message
     * @return
     * @throws IOException
     */
    public boolean unicastMessage(String corpid, String userId, String message) {
        Session unicastSession = null;
        for (XbbWebSocket item : xbbWebSocketSet) {
            if (sessionIdMap.containsKey(item.session.getId())) {
                SocketIdCardEntity idCard = JSON.parseObject(sessionIdMap.get(item.session.getId()), SocketIdCardEntity.class);
                if (corpid.equals(idCard.getCorpid()) && userId.equals(idCard.getUserId())) {
                    unicastSession = item.session;
                    break;
                }
            }
        }
        if (unicastSession == null) {
//            LOG.warn("客户"
//                    + " corpid -> " + corpid + " userId -> " + userId + "离线或未连接，发送失败！ 消息内容：" + message);
            return false;
        }
        // todo 因为用户现在会在 vue 和 普通Web 界面切换，所以不能保证准确推送率
//        LOG.info("向客户 SeesionId -> " + unicastSession.getId()
//                + " corpid -> " + corpid + " userId -> " + userId + " 发送消息：" + message);
        try {
            unicastSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            LOG.error("捕获IO异常，由客户 SessionId：" + this.session.getId() + "造成！异常内容：" + ExceptionUtil.getStackMsg(e));
        }
        return true;
    }

    /**
     * 群发自定义消息
     */
    public void sendBroadcast(String message) {
        for (XbbWebSocket item : xbbWebSocketSet) {
            item.sendMessage(message);
        }
    }

    /**
     * 显示当前的在线人数
     */
    public void showOnlineUserNum() {
        LOG.info("当前在线人数为:" + getOnlineCount());
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        XbbWebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        XbbWebSocket.onlineCount--;
    }
}