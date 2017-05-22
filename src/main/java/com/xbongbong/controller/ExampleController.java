package com.xbongbong.controller;

import com.xbongbong.base.BaseController;
import com.xbongbong.component.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-07
 * Time: 19:25
 */
@RestController
@RequestMapping(value = "/example", produces = "application/json;charset=UTF-8")
public class ExampleController extends BaseController {

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/set")
    public String set(String key, String value) throws Exception{
        redisClient.set(key, value);
        return formatSuccessResponse();
    }

    @RequestMapping("/get")
    public String get(String key) throws Exception {
        return formatSuccessResponse(redisClient.get(key));
    }
}