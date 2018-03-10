package com.zometer.app.mpqdashboard.dataimport.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="wiki")
public class WikiConfig {

    private List<String> urls = new ArrayList<>() ;

    public List<String> getUrls() {
        return urls;
    }

}
