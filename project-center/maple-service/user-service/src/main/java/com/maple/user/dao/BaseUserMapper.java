package com.maple.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.userapi.bean.BaseUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 基础信息-用户信息 Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Mapper
public interface BaseUserMapper extends BaseMapper<BaseUser> {

    IPage<BaseUser> getUserPage(Page page, @Param("user") BaseUser user);

    void deleteByIds(@Param("idArr") String[] idArr);
}
