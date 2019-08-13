package com.maple.userapi.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基础信息-资源表
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("base_resources")
@ApiModel(value="BaseResources对象", description="基础信息-资源表")
public class BaseResources extends Model<BaseResources> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父级id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "资源名称")
    @TableField("res_name")
    private String resName;

    @ApiModelProperty(value = "资源代码")
    @TableField("res_code")
    private String resCode;

    @ApiModelProperty(value = "资源描述")
    @TableField("res_desc")
    private String resDesc;

    @ApiModelProperty(value = "资源类型 1：菜单，2：权限")
    @TableField("res_type")
    private Integer resType;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "序号")
    @TableField("sort_num")
    private Integer sortNum;

    @ApiModelProperty(value = "路由")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "vue component")
    @TableField("component")
    private String component;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_date")
    private Date createDate;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_date")
    private Date modifyDate;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_delete")
    private Integer isDelete;

    @ApiModelProperty(value = "状态（0：正常   1：停用）")
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
