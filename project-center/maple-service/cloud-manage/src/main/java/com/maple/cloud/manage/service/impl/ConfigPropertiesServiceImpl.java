package com.maple.cloud.manage.service.impl;

import com.maple.cloud.manage.mapper.ConfigPropertiesMapper;
import com.maple.cloud.manage.service.IConfigPropertiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.api.bean.ConfigProperties;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置-config动态配置 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Service
public class ConfigPropertiesServiceImpl extends ServiceImpl<ConfigPropertiesMapper, ConfigProperties> implements IConfigPropertiesService {

}
