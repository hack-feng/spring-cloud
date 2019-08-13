package com.maple.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.userapi.bean.BaseRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<BaseRole> listRolesByUserId(Integer id);

    IPage<BaseRole> getRolePage(Page page,@Param("role") BaseRole role);

    void deleteByIds(@Param("ids") String[] idArr);
}
