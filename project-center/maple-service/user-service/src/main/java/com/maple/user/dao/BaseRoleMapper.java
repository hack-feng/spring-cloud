package com.maple.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.userapi.bean.BaseRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 基础信息-用户角色表 Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Mapper
public interface BaseRoleMapper extends BaseMapper<BaseRole> {

}
