package com.maple.cloud.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.ConfigProperties;

import java.util.List;

/**
 * <p>
 * 系统配置-config动态配置 服务类
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
public interface IConfigPropertiesService extends IService<ConfigProperties> {

    List<ConfigProperties> getList(ConfigProperties configProperties);

    R add(ConfigProperties configProperties);

    R update(ConfigProperties configProperties);

    R delete(Integer id);
}
