package com.xbongbong.entity;

import com.xbongbong.util.StringUtil;

import java.io.Serializable;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-08
 * Time: 16:37
 */
public class SocketIdCardEntity implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1L;

    private String corpid;
    private String userId;
    private String platform;

    public SocketIdCardEntity() {
    }

    public SocketIdCardEntity(String corpid, String userId, String platform) {
        this.corpid = corpid;
        this.userId = userId;
        this.platform = platform;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SocketIdCardEntity) {
            SocketIdCardEntity another = (SocketIdCardEntity) obj;
            return (!StringUtil.isEmpty(this.getCorpid()) && this.getCorpid().equals(another.getCorpid()))
                    && (!StringUtil.isEmpty(this.getUserId()) && this.getUserId().equals(another.getUserId()))
                    && (!StringUtil.isEmpty(this.getPlatform()) && this.getPlatform().equals(another.getPlatform()));
        } else {
            return false;
        }
    }
}
