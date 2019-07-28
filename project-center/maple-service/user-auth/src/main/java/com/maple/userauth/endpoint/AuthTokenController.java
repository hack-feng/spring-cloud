package com.maple.userauth.endpoint;

import com.maple.common.core.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/token")
public class AuthTokenController {
    private static final String PROJECT_OAUTH_ACCESS = SecurityConstants.PROJECT_PREFIX + SecurityConstants.OAUTH_PREFIX + "access:";

    private final RedisTemplate redisTemplate;

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, String code) {
        ModelAndView model = new ModelAndView("/login");
        return model;
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

}
