package com.maple.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.userapi.bean.BaseUser;

/**
 * <p>
 * 基础信息-用户信息 服务类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
public interface IBaseUserService extends IService<BaseUser> {
    BaseUser userLogin(String username, String password);
}
