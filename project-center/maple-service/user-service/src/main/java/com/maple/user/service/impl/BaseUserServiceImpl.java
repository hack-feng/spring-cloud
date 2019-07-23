package com.maple.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.user.dao.BaseUserMapper;
import com.maple.user.service.IBaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public BaseUser userLogin(String username, String password) throws RuntimeException{
        BaseUser user = userMapper.selectOne(new QueryWrapper<BaseUser>().eq("user_name", username).eq("is_delete", 0));
        if (user == null){
            throw new RuntimeException("该用户不存在！");
        }

        if (!user.getPassWord().equals(password)){
            throw new RuntimeException("用户名或密码错误！");
        }

        if (user.getIsLock().equals(1)){
            throw new RuntimeException("该用户被锁定！");
        }
        return user;
    }
}
