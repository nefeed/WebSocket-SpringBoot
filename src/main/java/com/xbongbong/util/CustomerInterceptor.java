package com.xbongbong.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-27
 * Time: 14:37
 */
public class CustomerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LogManager.getLogger(CustomerInterceptor.class);
    protected static final String TAG = "CustomerInterceptor";

    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_HANDLING_TIME = "handlingTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = DateUtil.getLong();
        request.setAttribute(KEY_START_TIME, startTime);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        long startTime = (Long) request.getAttribute(KEY_START_TIME);
        request.removeAttribute(KEY_START_TIME);
        long endTime = DateUtil.getLong();
        LOG.info("URL：" + request.getRequestURL() + "耗时：" + (endTime - startTime) + "ms");
        request.setAttribute(KEY_HANDLING_TIME, endTime - startTime);
    }
}
