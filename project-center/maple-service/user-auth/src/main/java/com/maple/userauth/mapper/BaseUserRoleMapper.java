package com.maple.userauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.userapi.bean.BaseUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 基础信息-用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Mapper
public interface BaseUserRoleMapper extends BaseMapper<BaseUserRole> {

    List<String> findUserRole(String username);

    List<String> findUserRes(String username);
}
