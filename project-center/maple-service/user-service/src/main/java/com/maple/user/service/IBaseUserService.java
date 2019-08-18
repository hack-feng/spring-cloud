package com.maple.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.userapi.bean.BaseUser;
import com.maple.userapi.vo.UserInfo;

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

    UserInfo getUserInfo(BaseUser user);

    IPage getUserPage(Page page, BaseUser user);

    String deleteByIds(String ids);
}
