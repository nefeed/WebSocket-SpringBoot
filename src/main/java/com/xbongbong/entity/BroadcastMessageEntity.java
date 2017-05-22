package com.xbongbong.entity;

import com.xbongbong.util.DateUtil;

import java.io.Serializable;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-08
 * Time: 16:57
 */
public class BroadcastMessageEntity implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1L;

    private String message;
    private int pushTime;
    private int addTime;

    public BroadcastMessageEntity() {
    }

    public BroadcastMessageEntity(String message, int pushTime) {
        this.message = message;
        this.pushTime = pushTime;
        this.addTime = DateUtil.getInt();
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
