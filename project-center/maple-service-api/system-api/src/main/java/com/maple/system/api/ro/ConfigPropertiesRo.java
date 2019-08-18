package com.maple.system.api.ro;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.maple.common.core.bean.BaseRo;
import com.maple.system.api.bean.ConfigProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统配置-config动态配置
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ConfigProperties对象", description = "系统配置-config动态配置")
public class ConfigPropertiesRo extends BaseRo<ConfigProperties> {

    @ApiModelProperty(value = "配置中key的值")
    @NotBlank(message = "配置中的key不能为空")
    private String key1;

    @ApiModelProperty(value = "配置中value的值")
    @NotBlank(message = "配置中的value不能为空")
    private String value1;

    @ApiModelProperty(value = "应用名")
    @NotBlank(message = "应用名不能为空")
    private String application;

    @ApiModelProperty(value = "环境")
    private String profile = "dev";

    @ApiModelProperty(value = "读取的分支")
    private String label = "master";

    @ApiModelProperty(value = "排序")
    private Integer sort = 0;

    @ApiModelProperty(value = "备注")
    private String remark;

}
