package com.maple.system.api.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_info")
@ApiModel(value="Info对象", description="")
public class Info extends Model<Info> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "连接的url")
    private String host;

    @ApiModelProperty(value = "端口号")
    private String port;

    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField("pass_word")
    private String passWord;

    @ApiModelProperty(value = "信息类型（mysql ; redis；rabbitmq）")
    @TableField("info_type")
    private String infoType;

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

    @ApiModelProperty(value = "状态（0：未启用 1：启用）")
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
