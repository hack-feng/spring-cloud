package com.maple.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.userapi.bean.BaseResources;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 基础信息-资源表 Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Mapper
public interface BaseResourcesMapper extends BaseMapper<BaseResources> {

    List<BaseResources> getResourcesByRoleId(Integer roleId);
}
