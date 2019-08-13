package com.maple.cloud.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.cloud.manage.mapper.ConfigPropertiesMapper;
import com.maple.cloud.manage.service.IConfigPropertiesService;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ConfigPropertiesMapper configPropertiesMapper;

    @Override
    public List<ConfigProperties> getList(ConfigProperties configProperties) {
        Class cls = configProperties.getClass();
        Map<String, Object> map = new HashMap<>();
        if(configProperties.getApplication() != null){
            map.put("application", configProperties.getApplication());
        }else{
            map.put("application", "不存在");
        }

        if(configProperties.getKey1() != null){
            map.put("key1", configProperties.getKey1());
        }

        if(configProperties.getValue1() != null){
            map.put("value1", configProperties.getValue1());
        }
        return configPropertiesMapper.selectByMap(map);
    }

    @Override
    public R add(ConfigProperties configProperties) {
        int configCount = configProperties.selectCount(new QueryWrapper<ConfigProperties>()
                .eq("application", configProperties.getApplication())
                .eq("key1", configProperties.getKey1()));
        if(configCount > 0){
            return R.failed("该配置已存在，不能重复添加");
        }
        int count =  configPropertiesMapper.insert(configProperties);
        return R.isOk(count > 0, "新增配置信息");
    }

    @Override
    public R update(ConfigProperties configProperties) {
        int configCount = configProperties.selectCount(new QueryWrapper<ConfigProperties>()
                .ne("id", configProperties.getId())
                .eq("application", configProperties.getApplication())
                .eq("key1", configProperties.getKey1()));
        if(configCount > 0){
            return R.failed("该配置已存在，不能重复添加");
        }
        int count =  configPropertiesMapper.updateById(configProperties);
        return R.isOk(count > 0, "修改配置信息");
    }

    @Override
    public R delete(Integer id) {
        int count = configPropertiesMapper.deleteById(id);
        return R.isOk(count > 0, "删除配置信息");
    }
}
