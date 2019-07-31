package com.maple.common.security.annotation;

import com.maple.common.security.component.MapleResourceServerConfigurerAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author zhua
 * @date 2019/7/31
 * 资源服务注解
 */
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(MapleResourceServerConfigurerAdapter.class)
public @interface EnableMapleResourceServer {
}
