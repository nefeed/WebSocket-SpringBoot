package com.xbongbong.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-08
 * Time: 14:41
 */
@Configuration
@ComponentScan(value = "com.xbongbong.service.task")
@EnableScheduling
public class TaskSchedulerConfig {
    protected static final String TAG = "TaskSchedulerConfig";
}
