package com.maple.userauth.endpoint;

import cn.hutool.core.util.StrUtil;
import com.maple.common.core.constant.SecurityConstants;
import com.maple.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
    private final TokenStore tokenStore;
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

    @GetMapping("logout")
    public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StrUtil.isBlank(authHeader)) {
            return R.ok("退出失败，token 为空");
        }

        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
            return R.ok("退出失败，token 无效");
        }
        tokenStore.removeAccessToken(accessToken);

        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        tokenStore.removeRefreshToken(refreshToken);

        return R.ok("退出成功");
    }
}
