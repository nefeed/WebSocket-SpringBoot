package com.xbongbong.model;


import com.xbongbong.base.BaseModel;

import java.io.Serializable;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-07
 * Time: 17:45
 */
public class ResponseModal extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public ResponseModal(){
        super();
    }

    public ResponseModal(int code, boolean success, String message){
        super(code,success,message);
    }

    public ResponseModal(int code, boolean success, String message, Object obj){
        super(code,success,message);
        this.response = obj;
    }

    private Object response;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

}
