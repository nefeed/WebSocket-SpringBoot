package com.xbongbong.entity;

import com.xbongbong.util.DateUtil;

import java.io.Serializable;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-18
 * Time: 19:40
 */
public class UnicastMessageEntity implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1L;

    private String corpid;
    private String userId;
    private String message;
    private int pushTime;
    private int addTime;

    public UnicastMessageEntity() {
    }

    public UnicastMessageEntity(String corpid, String userId, String message, int pushTime) {
        this.corpid = corpid;
        this.userId = userId;
        this.message = message;
        this.pushTime = pushTime;
        this.addTime = DateUtil.getInt();
    }

    public String getCorpid() {
        return corpid;
    }

    public void setCorpid(String corpid) {
        this.corpid = corpid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPushTime() {
        return pushTime;
    }

    public void setPushTime(int pushTime) {
        this.pushTime = pushTime;
    }

    public int getAddTime() {
        return addTime;
    }

    public void setAddTime(int addTime) {
        this.addTime = addTime;
    }
}
