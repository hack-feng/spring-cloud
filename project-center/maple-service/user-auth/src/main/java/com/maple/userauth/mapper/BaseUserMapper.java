package com.maple.userauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.userapi.bean.BaseUser;
import org.apache.ibatis.annotations.Mapper;

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

}
