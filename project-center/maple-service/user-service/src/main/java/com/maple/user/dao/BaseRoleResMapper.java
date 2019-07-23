package com.maple.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.userapi.bean.BaseRoleRes;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 基础信息-角色和资源关联表 Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Mapper
public interface BaseRoleResMapper extends BaseMapper<BaseRoleRes> {

}
