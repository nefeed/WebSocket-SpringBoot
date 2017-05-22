package com.xbongbong;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User: Gavin
 * E-mail: GavinChangCN@163.com
 * Desc:
 * Date: 2017-03-23
 * Time: 11:26
 */
@Component
@ConfigurationProperties(prefix="localConstant")
public class LocalConstant {
    protected static final String TAG = "localConstant";

    private String projectName;

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }
}
