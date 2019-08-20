package com.maple.userauth.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.common.core.constant.CommonConstants;
import com.maple.common.core.constant.SecurityConstants;
import com.maple.common.core.util.AesEncryptUtil;
import com.maple.common.security.vo.AuthUser;
import com.maple.userapi.bean.BaseUser;
import com.maple.userauth.mapper.BaseUserMapper;
import com.maple.userauth.mapper.BaseUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lingnet
 */
public class MapleUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private BaseUserRoleMapper baseUserRoleMapper;

    /**
     * 使用AES加密模式，key需要为16位
     */
    @Value("${security.encode.key:1234567812345678}")
    private String KEY;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<BaseUser> queryWrapper = new QueryWrapper<BaseUser>();
        queryWrapper.eq("user_name",username);
        BaseUser user = baseUserMapper.selectOne(queryWrapper);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
//        BaseUser user = baseUserMapper.selectUserByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }

        Collection<? extends GrantedAuthority> authorities = null;
        Set<String> dbAuthsSet = new HashSet<>();

        //所谓的角色，只是增加ROLE_前缀
        List<String> roleList = baseUserRoleMapper.findUserRole(username);
        if (roleList != null && roleList.size() > 0) {
            dbAuthsSet.addAll(roleList);
        }
        List<String> permissionList = baseUserRoleMapper.findUserRes(username);
        if (permissionList != null && permissionList.size() > 0) {
            dbAuthsSet.addAll(permissionList);
        }
        if (dbAuthsSet != null && dbAuthsSet.size() > 0) {
            authorities = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        }

        boolean enabled = CommonConstants.STATUS_NORMAL.equals(Convert.toStr(user.getIsLock()));
        //返回UserDetails的实现user不为空，则验证通过
        // 构造security用户
        String password = "";
        try {
            password = AesEncryptUtil.encrypt(user.getPassWord(), KEY);
        } catch (Exception e) {
            e.printStackTrace();
            password = user.getPassWord();
        }
        return new AuthUser(user.getId(), user.getUserName(), SecurityConstants.NOOP + password,
                enabled, true, true, !CommonConstants.STATUS_LOCK.equals(Convert.toStr(user.getIsLock())), authorities);
    }
}
