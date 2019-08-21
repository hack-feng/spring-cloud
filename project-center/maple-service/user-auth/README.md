# spring-cloud oauth

## 前言

经过一段时间的学习Oauth2，在网上也借鉴学习了一些大牛的经验，推荐在学习的过程中多看几遍阮一峰的《理解OAuth 2.0》，经过对Oauth2的多种方式的实现，个人推荐Spring Security和Oauth2的实现是相对优雅的，理由如下：

1、相对于直接实现Oauth2，减少了很多代码量，也就减少的查找问题的成本。

2、通过调整配置文件，灵活配置Oauth相关配置。

3、通过结合路由组件（如zuul），更好的实现微服务权限控制扩展。


### Oauth2概述

oauth2根据使用场景不同，分成了4种模式
* 授权码模式（authorization code）
* 简化模式（implicit）
* 密码模式（resource owner password credentials）
* 客户端模式（client credentials）

在项目中我们通常使用授权码模式，也是四种模式中最复杂的，通常网站中经常出现的微博，qq第三方登录，都会采用这个形式。

Oauth2授权主要由两部分组成：

* Authorization server：认证服务
* Resource server：资源服务

在实际项目中以上两个服务可以在一个服务器上，也可以分开部署。

### 核心配置

核心配置主要分为授权应用和客户端应用两部分，如下：

* 授权应用：即Oauth2授权服务，主要包括Spring Security、认证服务和资源服务两部分配置
* 客户端应用：即通过授权应用进行认证的应用，多个客户端应用间支持单点登录

#### 认证中心核心配置

SecurityConfiguration 继承 WebSecurityConfigurerAdapter 核心配置 
```
http.csrf().disable()
    .requestMatchers()
    .antMatchers("/oauth/**", "/token/**")
    .and()
    .formLogin()
    .loginPage("/token/login")
    .loginProcessingUrl("/token/form")
    .failureUrl("/token/login?error=")
    .permitAll()
    .and()
    .logout().disable()
    .authorizeRequests().antMatchers("/token/**", "/oauth/authorize").permitAll()
    .anyRequest().fullyAuthenticated();
```
AuthorizationServerConfiguration 继承 AuthorizationServerConfigurerAdapter 核心配置
```
@Override
 public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    //允许表单认证
    //这里增加过滤器到安全认证链中，实现自定义认证，包括图片验证，短信验证，微信小程序，第三方系统，CAS单点登录
    //addTokenEndpointAuthenticationFilter(IntegrationAuthenticationFilter())
    //IntegrationAuthenticationFilter 采用 @Component 注入
    security.allowFormAuthenticationForClients()
            .tokenKeyAccess("isAuthenticated()")
            .checkTokenAccess("permitAll()")
            .addTokenEndpointAuthenticationFilter(mapleBasicAuthenticationFilter);
}
```

#### 客户端信息
客户端信息存储在数据库中，客户认证的时候从数据库根据clientId取信息，然后进行认证

配置
在AuthorizationServerConfiguration类中实现config方法，配置客户端认证实现类
```
@Override
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(new MapleClientDetailServiceImpl());
}
```

客户端实现类实现ClientDetailsService接口，重写loadClientByClientId方法
```
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
```

#### 资源中心核心配置

资源服务器 MapleResourceServerConfigurerAdapter 继承 ResourceServerConfigurerAdapter 类
核心配置
```
@Override
@SneakyThrows
public void configure(HttpSecurity httpSecurity) {
    //允许使用iframe 嵌套
    httpSecurity.headers().frameOptions().disable()
            .and().csrf().disable();
    if (ignorePropertiesConfig.getUrls() != null && ignorePropertiesConfig.getUrls().size() > 0) {
        String[] ignoreUrls = ignorePropertiesConfig.getUrls().toArray(new String[ignorePropertiesConfig.getUrls().size()]);
        httpSecurity.authorizeRequests().antMatchers(ignoreUrls).permitAll();
    }
    httpSecurity.authorizeRequests().anyRequest().authenticated();
}
```
ignorePropertiesConfig 是默认对外开发的请求url，在配置文件中配置
```
ignore.urls=/test
```

FilterIgnorePropertiesConfig 配置
```
@Data
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${ignore}'.isEmpty()")
@ConfigurationProperties(prefix = "ignore")
public class FilterIgnorePropertiesConfig {

    /**
     * 放行url,放行的url不再被安全框架拦截
     */
    private List<String> urls = new ArrayList<>();
}
```

#### 资源服务使用

1、在启动类上添加 @EnableMapleResourceServer 注解
```
@EnableMapleResourceServer
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
```
2、在配置文件配置client信息
```
security.oauth2.client.client-id=maple
security.oauth2.client.client-secret=maple
security.oauth2.client.scope=server
security.oauth2.resource.token-info-uri=http://127.0.0.1:3000/oauth/check_token
```

### token生成

token的存储主流有三种方式，分别为内存、redis和数据库，在本项目中使用redis存储。

配置TokenService参数
```
DefaultTokenServices tokenService = new DefaultTokenServices();
tokenService.setTokenStore(endpoints.getTokenStore());
tokenService.setSupportRefreshToken(true);
tokenService.setClientDetailsService(endpoints.getClientDetailsService());
tokenService.setTokenEnhancer(endpoints.getTokenEnhancer());
tokenService.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(CommonConstants.DUFAULT_ACCESS_TOKEN_TIME));
tokenService.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(CommonConstants.DUFAULT_REFRESH_TOKEN_TIME));
tokenService.setReuseRefreshToken(false);
endpoints.tokenServices(tokenService);
```

token存储方式
```
public TokenStore tokenStore() {
    RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
    tokenStore.setPrefix(SecurityConstants.PROJECT_PREFIX + SecurityConstants.OAUTH_PREFIX);
    return tokenStore;
}
```

### Oauth2数据结构
<table>
    <tr>
        <th>表名</th>
        <th>字段名</th>
        <th>字段说明</th>  
    </tr>
    <tr>
        <td rowspan="14">oauth_client_details</td>
        <td>client_id</td>
        <td>主键,必须唯一,不能为空. 
            用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成). 
            对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appKey,与client_id是同一个概念.</td>
    </tr>
    <tr>
        <td>resource_ids</td>
        <td>客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: "unity-resource,mobile-resource". 
            该字段的值必须来源于与security.xml中标签‹oauth2:resource-server的属性resource-id值一致. 在security.xml配置有几个‹oauth2:resource-server标签, 则该字段可以使用几个该值. 
            在实际应用中, 我们一般将资源进行分类,并分别配置对应的‹oauth2:resource-server,如订单资源配置一个‹oauth2:resource-server, 用户资源又配置一个‹oauth2:resource-server. 当注册客户端时,根据实际需要可选择资源id,也可根据不同的注册流程,赋予对应的资源id.
         </td>  
    </tr>
    <tr>
        <td>client_secret</td>
        <td>用于指定客户端(client)的访问密匙; 在注册时必须填写(也可由服务端自动生成). 
            对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appSecret,与client_secret是同一个概念.
         </td>  
    </tr>
    <tr>
        <td>scope</td>
        <td>指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔,如: "read,write". 
           scope的值与security.xml中配置的‹intercept-url的access属性有关系. 如‹intercept-url的配置为
           ‹intercept-url pattern="/m/**" access="ROLE_MOBILE,SCOPE_READ"/>
           则说明访问该URL时的客户端必须有read权限范围. write的配置值为SCOPE_WRITE, trust的配置值为SCOPE_TRUST. 
           在实际应该中, 该值一般由服务端指定, 常用的值为read,write.
        </td>  
    </tr>
    <tr>
        <td>authorized_grant_types</td>
        <td>指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: "authorization_code,password". 
          在实际应用中,当注册时,该字段是一般由服务器端指定的,而不是由申请者去选择的,最常用的grant_type组合有: "authorization_code,refresh_token"(针对通过浏览器访问的客户端); "password,refresh_token"(针对移动设备的客户端). 
          implicit与client_credentials在实际中很少使用.
        </td>  
    </tr>
    <tr>
        <td>web_server_redirect_uri</td>
        <td>客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致. 下面分别说明:
            当grant_type=authorization_code时, 第一步 从 spring-oauth-server获取 'code'时客户端发起请求时必须有redirect_uri参数, 该参数的值必须与web_server_redirect_uri的值一致. 第二步 用 'code' 换取 'access_token' 时客户也必须传递相同的redirect_uri. 
            在实际应用中, web_server_redirect_uri在注册时是必须填写的, 一般用来处理服务器返回的code, 验证state是否合法与通过code去换取access_token值. 
            在spring-oauth-client项目中, 可具体参考AuthorizationCodeController.java中的authorizationCodeCallback方法.
            当grant_type=implicit时通过redirect_uri的hash值来传递access_token值.如:
            http://localhost:7777/spring-oauth-client/implicit#access_token=dc891f4a-ac88-4ba6-8224-a2497e013865&token_type=bearer&expires_in=43199
            然后客户端通过JS等从hash值中取到access_token值.
        </td>  
    </tr>
    <tr>
        <td>authorities</td>
        <td>指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔, 如: "ROLE_UNITY,ROLE_USER". 
            对于是否要设置该字段的值,要根据不同的grant_type来判断, 若客户端在Oauth流程中需要用户的用户名(username)与密码(password)的(authorization_code,password), 
            则该字段可以不需要设置值,因为服务端将根据用户在服务端所拥有的权限来判断是否有权限访问对应的API. 
            但如果客户端在Oauth流程中不需要用户信息的(implicit,client_credentials), 
            则该字段必须要设置对应的权限值, 因为服务端将根据该字段值的权限来判断是否有权限访问对应的API. 
            (请在spring-oauth-client项目中来测试不同grant_type时authorities的变化)
        </td>  
    </tr>
    <tr>
        <td>access_token_validity</td>
        <td>设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时). 
          在服务端获取的access_token JSON数据中的expires_in字段的值即为当前access_token的有效时间值. 
          在项目中, 可具体参考DefaultTokenServices.java中属性accessTokenValiditySeconds. 
          在实际应用中, 该值一般是由服务端处理的, 不需要客户端自定义.
        </td>  
    </tr>
    <tr>
        <td>refresh_token_validity</td>
        <td>设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天). 
          若客户端的grant_type不包括refresh_token,则不用关心该字段 在项目中, 可具体参考DefaultTokenServices.java中属性refreshTokenValiditySeconds. 
          在实际应用中, 该值一般是由服务端处理的, 不需要客户端自定义.
        </td>  
    </tr>
    <tr>
        <td>additional_information</td>
        <td>这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:
           {"country":"CN","country_code":"086"}
           按照spring-security-oauth项目中对该字段的描述 
           Additional information for this client, not need by the vanilla OAuth protocol but might be useful, for example,for storing descriptive information. 
           (详见ClientDetails.java的getAdditionalInformation()方法的注释)在实际应用中, 可以用该字段来存储关于客户端的一些其他信息,如客户端的国家,地区,注册时的IP地址等等.
        </td>  
    </tr>
    <tr>
        <td>create_time</td>
        <td>数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)
        </td>  
    </tr>
    <tr>
        <td>archived</td>
        <td>用于标识客户端是否已存档(即实现逻辑删除),默认值为'0'(即未存档). 
            对该字段的具体使用请参考CustomJdbcClientDetailsService.java,在该类中,扩展了在查询client_details的SQL加上archived = 0条件 (扩展字段)
        </td>  
    </tr>
    <tr>
        <td>trusted</td>
        <td>设置客户端是否为受信任的,默认为'0'(即不受信任的,1为受信任的). 
            该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为0,则会跳转到让用户Approve的页面让用户同意授权, 
            若该字段为1,则在登录后不需要再让用户Approve同意授权(因为是受信任的). 
            对该字段的具体使用请参考OauthUserApprovalHandler.java. (扩展字段)
        </td>  
    </tr>
    <tr>
        <td>autoapprove</td>
        <td>设置用户是否自动Approval操作, 默认值为 'false', 可选值包括 'true','false', 'read','write'. 
            该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为'true'或支持的scope值,则会跳过用户Approve的页面, 直接授权. 
            该字段与 trusted 有类似的功能, 是 spring-security-oauth2 的 2.0 版本后添加的新属性.
        </td>  
    </tr>
    <tr>
        <td rowspan="6">oauth_client_token</td>
        <td>create_time</td>
        <td>数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)
        </td>
    </tr>
    <tr>
        <td>token_id</td>
        <td>从服务器端获取到的access_token的值.
        </td>
    </tr>
    <tr>
        <td>token</td>
        <td>这是一个二进制的字段, 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据.
        </td>
    </tr>
    <tr>
        <td>authentication_id</td>
        <td>该字段具有唯一性, 是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 
            具体实现请参考DefaultClientKeyGenerator.java类.
        </td>
    </tr>
    <tr>
        <td>user_name</td>
        <td>登录时的用户名
        </td>
    </tr>
    <tr>
        <td>client_id</td>
        <td>
        </td>
    </tr>
    <tr>
        <td rowspan="8">oauth_access_token</td>
        <td>create_time</td>
        <td>数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)
        </td>
    </tr>
    <tr>
        <td>token_id</td>
        <td>该字段的值是将access_token的值通过MD5加密后存储的.
        </td>
    </tr>
    <tr>
        <td>token</td>
        <td>存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值.
        </td>
    </tr>
    <tr>
        <td>authentication_id</td>
        <td>该字段具有唯一性, 其值是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultAuthenticationKeyGenerator.java类.
        </td>
    </tr>
    <tr>
        <td>user_name</td>
        <td>登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id
        </td>
    </tr>
    <tr>
        <td>client_id</td>
        <td>
        </td>
    </tr>
    <tr>
        <td>authentication</td>
        <td>存储将OAuth2Authentication.java对象序列化后的二进制数据.
        </td>
    </tr>
    <tr>
        <td>refresh_token</td>
        <td>该字段的值是将refresh_token的值通过MD5加密后存储的.
        </td>
    </tr>
    <tr>
        <td  rowspan="3">oauth_code</td>
        <td>create_time</td>
        <td>数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)
        </td>
    </tr>
    <tr>
        <td>code</td>
        <td>存储服务端系统生成的code的值(未加密).
        </td>
    </tr>
    <tr>
        <td>authentication</td>
        <td>存储将AuthorizationRequestHolder.java对象序列化后的二进制数据.
        </td>
    </tr>
</table>
