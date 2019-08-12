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
@TableName("system_microservices")
@ApiModel(value="Microservices对象", description="")
public class Microservices extends Model<Microservices> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "微服务名称")
    @TableField("service_name")
    private String serviceName;

    @ApiModelProperty(value = "微服务部署机器ip")
    @TableField("service_ip")
    private String serviceIp;

    @ApiModelProperty(value = "是否启动")
    @TableField("is_start")
    private Integer isStart;

    @ApiModelProperty(value = "使用状态（0：未使用  1：已使用）")
    private Integer status;

    @ApiModelProperty(value = "是否默认创建config配置信息")
    @TableField("is_create_config")
    private Integer isCreateConfig;

    @ApiModelProperty(value = "是否使用数据库")
    @TableField("is_use_msyql")
    private Integer isUseMsyql;

    @ApiModelProperty(value = "是否使用redis")
    @TableField("is_user_redis")
    private Integer isUserRedis;

    @ApiModelProperty(value = "是否使用redis")
    @TableField("is_use_rabbitmq")
    private Integer isUseRabbitmq;

    @ApiModelProperty(value = "mysql信息")
    @TableField("mysql_info")
    private String mysqlInfo;

    @ApiModelProperty(value = "redis信息")
    @TableField("redis_info")
    private String redisInfo;

    @ApiModelProperty(value = "rabbitmq信息")
    @TableField("rabbitmq_info")
    private String rabbitmqInfo;

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
