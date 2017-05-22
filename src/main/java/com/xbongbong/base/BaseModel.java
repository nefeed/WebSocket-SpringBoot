package com.xbongbong.base;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-07
 * Time: 17:45
 */
public class BaseModel {
    protected static final String TAG = "BaseModel";

    public BaseModel() {
        super();
    }

    public BaseModel(int code, boolean success, String message) {
        super();
        this.code = code;
        this.success = success;
        this.message = message;
    }

    private int code;

    private boolean success;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
