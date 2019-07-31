package com.maple.common.security.component;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author zhua
 * @date 2019/7/31
 */
@ComponentScan("com.maple.common.security")
public class MapleResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Autowired
    private FilterIgnorePropertiesConfig ignorePropertiesConfig;

    /**
     * 默认的配置，对外暴露
     *
     * @param httpSecurity
     */
    @Override
    @SneakyThrows
    public void configure(HttpSecurity httpSecurity) {

        //允许使用iframe 嵌套
        httpSecurity.headers().frameOptions().disable()
            .and().csrf().disable();
        if(ignorePropertiesConfig.getUrls() != null && ignorePropertiesConfig.getUrls().size() > 0) {
            String[] ignoreUrls = ignorePropertiesConfig.getUrls().toArray(new String[ignorePropertiesConfig.getUrls().size()]);
            httpSecurity.authorizeRequests().antMatchers(ignoreUrls).permitAll();
        }
        httpSecurity.authorizeRequests().anyRequest().authenticated();
    }
}
