package com.xbongbong.bean;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-24
 * Time: 16:52
 */
public class TestBean {
    protected static final String TAG = "TestBean";

    private String content;

    public TestBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
