package com.maple.user.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.common.core.constant.CommonConstants;
import com.maple.common.core.util.AesEncryptUtil;
import com.maple.common.core.util.R;
import com.maple.common.security.util.SecurityUtils;
import com.maple.user.service.IBaseUserService;
import com.maple.userapi.bean.BaseUser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 基础信息-用户信息 前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/baseUser")
@RefreshScope
public class BaseUserController {

    @Autowired
    private IBaseUserService userService;

    /**
     * 使用AES加密模式，key需要为16位
     */
    @Value("${security.encode.key:1234567812345678}")
    private String KEY;

    /**
     * 获取当前用户全部信息
     *
     * @return zhua
     */
    @GetMapping(value = {"/info"})
    public R info() {
        String username = SecurityUtils.getUsername();
        BaseUser user = userService.getOne(Wrappers.<BaseUser>query()
                .lambda().eq(BaseUser::getUserName, username));
        if (user == null) {
            return R.failed("获取当前用户信息失败");
        }
        return R.ok(userService.getUserInfo(user));
    }

    @HystrixCommand(fallbackMethod = "baseHys")
    @GetMapping(value = "getList")
    public JSONObject getList() {
        JSONObject result = new JSONObject();
        List<BaseUser> list = userService.list(null);
        result.put("code", 200);
        result.put("data", list);
        return result;
    }

    private JSONObject baseHys() {
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "BaseUserController的线路中断");
        return a;
    }

    @HystrixCommand(fallbackMethod = "getUserInfoHys")
    @RequestMapping(value = "getUserInfo")
    public JSONObject getUserInfo(Integer id) {
        JSONObject result = new JSONObject();
        BaseUser user = null;
        try {
            user = userService.getById(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("code", 200);
        result.put("data", user);
        return result;
    }

    private JSONObject getUserInfoHys(Integer id) {
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "BaseUserController的线路中断");
        return a;
    }


    /**
     * 分页查询用户
     *
     * @param page 参数集
     * @param user 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getUserPage(Page page, BaseUser user) {
        return R.ok(userService.getUserPage(page, user));
    }


    /**
     * 添加用户
     *
     * @param user
     * @return
     * @author zhua
     */
    @ApiOperation(value = "添加用户服务", notes = "新增一个用户")
    @PostMapping("/add")
    public R add(BaseUser user) {
        if (user == null) {
            return R.failed("获取用户信息失败");
        }
        user.setPassWord(CommonConstants.DEFAULT_PASSWORD);
        user.setIsDelete(Convert.toInt(CommonConstants.STATUS_NORMAL));
        user.setCreateDate(new Date());
        return R.ok(userService.save(user));
    }

    /**
     * 编辑用户
     *
     * @param user
     * @return
     * @author zhua
     */
    @ApiOperation(value = "编辑用户服务", notes = "编辑现有的用户")
    @PostMapping("/update")
    public R update(BaseUser user) {
        if (user == null) {
            return R.failed("获取用户信息失败");
        }
        user.setModifyDate(new Date());
        return R.ok(userService.updateById(user));
    }

    /**
     * 修改密码
     *
     * @param passwd
     * @return
     * @author zhua
     */
    @ApiOperation(value = "修改密码", notes = "用户修改密码")
    @PostMapping("/updatePasswd")
    public R updatePasswd(String oldPasswd, String passwd) {
        String username = SecurityUtils.getUsername();
        BaseUser user = userService.getOne(Wrappers.<BaseUser>query()
                .lambda().eq(BaseUser::getUserName, username));
        if (user == null) {
            return R.failed("获取当前用户信息失败");
        }
        if (!user.getPassWord().equals(oldPasswd)) {
           return R.failed("当前密码输入错误");
        }
        user.setPassWord(AesEncryptUtil.desEncrypt(passwd, KEY));
        return R.ok(userService.updateById(user));
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     * @author zhua
     */
    @ApiOperation(value = "删除用户服务", notes = "删除现有的用户")
    @DeleteMapping("/delete")
    public R delete(String ids) {
        if (ids == null) {
            return R.failed("获取用户信息失败");
        }
        return R.ok(null,userService.deleteByIds(ids));
    }
}

