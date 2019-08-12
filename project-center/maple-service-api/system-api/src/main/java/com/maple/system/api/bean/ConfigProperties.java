package com.maple.system.api.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("system_config_properties")
@ApiModel(value="ConfigProperties对象", description="系统配置-config动态配置")
public class ConfigProperties extends Model<ConfigProperties> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "配置中key的值")
    private String key1;

    @ApiModelProperty(value = "配置中value的值")
    private String value1;

    @ApiModelProperty(value = "应用名")
    private String application;

    @ApiModelProperty(value = "环境")
    private String profile;

    @ApiModelProperty(value = "读取的分支")
    private String label;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_date")
    private Date createDate;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_date")
    private Date modifyDate;

    @ApiModelProperty(value = "创建人")
    @TableField("create_id")
    private Integer createId;

    @ApiModelProperty(value = "修改人")
    @TableField("modify_id")
    private Integer modifyId;

    @ApiModelProperty(value = "备注")
    private String remark;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
