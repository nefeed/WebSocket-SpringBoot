package com.xbongbong.base;

import com.alibaba.fastjson.JSONObject;
import com.xbongbong.util.StringUtil;

import java.io.IOException;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-18
 * Time: 19:07
 */
public class BaseController {
    protected static final String TAG = "BaseController";

    /**
     * 设置Controller的返回值
     *
     * @param errorCode
     * @param msg
     * @param param
     * @return
     * @throws IOException
     * @author huajun.zhang
     * @time 2017-02-21 上午10:25:00
     */
    public String formatResponse(int errorCode, String msg, String param) {

//        response.addHeader("content-type", "application/json;charset=utf-8");
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        JSONObject json = new JSONObject();
        json.put("code", errorCode);
        json.put("msg", msg);
        if (!StringUtil.isEmpty(param)) {
            json.put("param", param);
        }
        return json.toJSONString();
    }

    public String formatErrorResponse(int errorCode, String msg) {
        return formatResponse(errorCode, msg, null);
    }

    /**
     * 设置Controller的请求成功返回
     *
     * @return
     * @throws IOException
     * @author huajun.zhang
     * @time 2017-02-21 上午10:26:00
     */
    public String formatSuccessResponse(String param) {
        return formatResponse(1, "success", param);
    }

    /**
     * 设置Controller的请求成功返回
     *
     * @return
     * @throws IOException
     * @author huajun.zhang
     * @time 2017-02-21 上午10:26:00
     */
    public String formatSuccessResponse() {
        return formatResponse(1, "success", null);
    }

}
