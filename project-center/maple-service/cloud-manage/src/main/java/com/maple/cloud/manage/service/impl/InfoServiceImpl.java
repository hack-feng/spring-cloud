package com.maple.cloud.manage.service.impl;

import com.maple.cloud.manage.mapper.InfoMapper;
import com.maple.cloud.manage.service.IInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.api.bean.Info;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements IInfoService {

}
