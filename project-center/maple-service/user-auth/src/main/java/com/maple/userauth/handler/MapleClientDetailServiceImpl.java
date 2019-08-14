package com.maple.userauth.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.common.core.constant.CommonConstants;
import com.maple.userapi.bean.BaseOauthClientDetails;
import com.maple.userauth.mapper.BaseOauthClientDetailsMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 自定义客户端认证
 *
 * @author zhua
 */
@NoArgsConstructor
public class MapleClientDetailServiceImpl implements ClientDetailsService {

    @Autowired
    private BaseOauthClientDetailsMapper baseOauthClientDetailsMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails client = null;
        if (StrUtil.isNotEmpty(clientId)) {
            QueryWrapper<BaseOauthClientDetails> wrapper = new QueryWrapper<>();
            wrapper.eq("client_id", clientId);
            BaseOauthClientDetails sysOauthClientDetails = baseOauthClientDetailsMapper.selectOne(wrapper);
            if (sysOauthClientDetails != null) {
                client = new BaseClientDetails();
                client.setClientId(clientId);
                //                client.setClientSecret(new BCryptPasswordEncoder().encode(sysOauthClientDetails.getClientSecret()));
                client.setClientSecret("{noop}" + sysOauthClientDetails.getClientSecret());
                if (StrUtil.isNotEmpty(sysOauthClientDetails.getResourceIds())) {
                    client.setResourceIds(Arrays.asList(sysOauthClientDetails.getResourceIds().split(",")));
                }
                if (StrUtil.isNotEmpty(sysOauthClientDetails.getAuthorizedGrantTypes())) {
                    client.setAuthorizedGrantTypes(Arrays.asList(sysOauthClientDetails.getAuthorizedGrantTypes().split(",")));
                }
                //不同的client可以通过 一个scope 对应 权限集
                if (StrUtil.isNotEmpty(sysOauthClientDetails.getScope())) {
                    client.setScope(Arrays.asList(sysOauthClientDetails.getScope().split(",")));
                }
                if (StrUtil.isNotEmpty(sysOauthClientDetails.getAuthorities())) {
                    client.setAuthorities(AuthorityUtils.createAuthorityList(sysOauthClientDetails.getAuthorities().split(",")));
                }
                if (sysOauthClientDetails.getAccessTokenValidity() != null) {
                    client.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(sysOauthClientDetails.getAccessTokenValidity()));
                } else {
                    client.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(CommonConstants.CLIENT_ACCESS_TOKEN_TIME)); //1天
                }
                if (sysOauthClientDetails.getRefreshTokenValidity() != null) {
                    client.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(sysOauthClientDetails.getRefreshTokenValidity()));
                } else {
                    client.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(CommonConstants.CLIENT_REFRESH_TOKEN_TIME)); //1天
                }
                if (StrUtil.isNotEmpty(sysOauthClientDetails.getWebServerRedirectUri())) {
                    String[] redirectUriArr = sysOauthClientDetails.getWebServerRedirectUri().split(",");
                    if (redirectUriArr != null && redirectUriArr.length > 0) {
                        Set<String> uris = new HashSet<>();
                        for (String redirectUri : redirectUriArr) {
                            uris.add(redirectUri);
                        }
                        client.setRegisteredRedirectUri(uris);
                    }

                }

            }

        }

        if (client == null) {
            throw new NoSuchClientException("No client width requested id: " + clientId);
        }
        return client;
    }

}
