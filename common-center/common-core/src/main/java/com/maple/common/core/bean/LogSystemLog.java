package com.maple.common.core.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 日志信息-系统日志表
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Data
@ApiModel(value="LogSystemLog对象", description="日志信息-系统日志表")
public class LogSystemLog {

    @ApiModelProperty(value = "服务名称")
    private String appName;

    @ApiModelProperty(value = "日志参数")
    private String params;

    @ApiModelProperty(value = "返回结果")
    private String results;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "请求ip")
    private String requestIp;

    @ApiModelProperty(value = "请求路径")
    private String allMethodName;

    @ApiModelProperty(value = "请求方法名称")
    private String methodName;

    @ApiModelProperty(value = "操作方式")
    private String operationType;

    @ApiModelProperty(value = "是否成功（0：否 1：是）")
    private String success;

    @ApiModelProperty(value = "响应时间")
    private String respTime;

    @ApiModelProperty(value = "错误信息")
    private String errorMsg;

    @ApiModelProperty(value = "操作人id")
    private Integer operateId;

    @ApiModelProperty(value = "操作人姓名")
    private String operateName;

    @ApiModelProperty(value = "日志类型")
    private String logType;

    @ApiModelProperty(value = "日志描述")
    private String logDesc;

}
