package com.maple.user.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.user.dao.BaseResourcesMapper;
import com.maple.user.dao.BaseRoleMapper;
import com.maple.user.dao.BaseUserMapper;
import com.maple.user.service.IBaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseResources;
import com.maple.userapi.bean.BaseRole;
import com.maple.userapi.bean.BaseUser;
import com.maple.userapi.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 基础信息-用户信息 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

    @Autowired
    private BaseUserMapper userMapper;
    @Autowired
    private BaseRoleMapper baseRoleMapper;
    @Autowired
    private BaseResourcesMapper baseResourcesMapper;

    public BaseUser userLogin(String username, String password) throws RuntimeException {
        BaseUser user = userMapper.selectOne(new QueryWrapper<BaseUser>().eq("user_name", username).eq("is_delete", 0));
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }

        if (!user.getPassWord().equals(password)) {
            throw new RuntimeException("用户名或密码错误！");
        }

        if (user.getIsLock().equals(1)) {
            throw new RuntimeException("该用户被锁定！");
        }
        return user;
    }

    @Override
    public UserInfo getUserInfo(BaseUser user) {
        UserInfo userInfo = new UserInfo();
        user.setPassWord(null);
        userInfo.setSysUser(user);
        //设置角色列表
        List<BaseRole> roleList = baseRoleMapper.listRolesByUserId(user.getId());
        List<String> roles = roleList
                .stream()
                .map(BaseRole::getRoleCode)
                .collect(Collectors.toList());
        userInfo.setRoles(ArrayUtil.toArray(roles, String.class));

        List<Integer> roleIds = roleList
                .stream()
                .map(BaseRole::getId)
                .collect(Collectors.toList());
        //设置权限列表
        Set<String> resSet = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<BaseResources> resourcesListTmp = baseResourcesMapper.getResourcesByRoleId(roleId);
            List<String> resourcesStrArr = resourcesListTmp
                    .stream()
                    .filter(baseResources -> StrUtil.isNotEmpty(baseResources.getResCode()))
                    .map(BaseResources::getResCode)
                    .collect(Collectors.toList());
            resSet.addAll(resourcesStrArr);
        });
        if (resSet != null && resSet.size() > 0) {
            userInfo.setPermissions(ArrayUtil.toArray(resSet, String.class));
        }

        return userInfo;
    }

    @Override
    public IPage getUserPage(Page page, BaseUser user) {
        return userMapper.getUserPage(page, user);
    }

    @Override
    @Transactional
    public String deleteByIds(String ids) {
        String[] idArr = ids.split(",");
        userMapper.deleteByIds(idArr);
        return "删除成功";
    }
}
