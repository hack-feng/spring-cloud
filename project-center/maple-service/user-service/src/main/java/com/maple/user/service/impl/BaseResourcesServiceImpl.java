package com.maple.user.service.impl;

import com.maple.user.dao.BaseResourcesMapper;
import com.maple.user.service.IBaseResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseResources;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础信息-资源表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Service
public class BaseResourcesServiceImpl extends ServiceImpl<BaseResourcesMapper, BaseResources> implements IBaseResourcesService {

}
