package com.maple.user.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maple.user.dao.BaseRoleResMapper;
import com.maple.user.service.IBaseRoleResService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseRoleRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 基础信息-角色和资源关联表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Service
public class BaseRoleResServiceImpl extends ServiceImpl<BaseRoleResMapper, BaseRoleRes> implements IBaseRoleResService {

    @Autowired
    private BaseRoleResMapper baseRoleResMapper;

    @Override
    @Transactional
    public String updateRoleAuth(Integer roleId, String resIds) {

        UpdateWrapper<BaseRoleRes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        baseRoleResMapper.delete(updateWrapper);

        if (StrUtil.isEmpty(resIds)) {
            return "操作成功";
        }

        String[] resIdArr = resIds.split(",");
        for (String id : resIdArr) {
            BaseRoleRes baseRoleRes = new BaseRoleRes();
            baseRoleRes.setRoleId(roleId);
            baseRoleRes.setResId(Convert.toInt(id));
            baseRoleResMapper.insert(baseRoleRes);
        }

        return "操作成功";
    }
}
