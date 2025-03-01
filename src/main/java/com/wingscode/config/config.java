package com.wingscode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author：zhangyan
 * Date：2025-02-28 15:46
 * Description：<今天又是元气满满的一天>
 */
@Configuration
public class config implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path="D:/naicha/src/main/java/file/";
       registry.addResourceHandler("/file/**").addResourceLocations("file:" + path +"/");
    }

    private String fileUtils(String path) {

        int i= path.lastIndexOf("/")+ 1;
        return path.substring(i);

    }


}