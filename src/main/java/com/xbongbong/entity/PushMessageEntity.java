package com.xbongbong.entity;

import java.io.Serializable;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-10
 * Time: 14:42
 */
public class PushMessageEntity implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1L;

    private String title;
    private String content;
    private String messageUrl;

    public PushMessageEntity() {
    }

    public PushMessageEntity(String title, String content, String messageUrl) {
        this.title = title;
        this.content = content;
        this.messageUrl = messageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }
}
