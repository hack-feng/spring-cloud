package com.maple.userapi.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty(value = "资源路径")
    @TableField("res_url")
    private String resUrl;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseResources{" +
        ", id=" + id +
        ", parentId=" + parentId +
        ", resName=" + resName +
        ", resDesc=" + resDesc +
        ", resType=" + resType +
        ", icon=" + icon +
        ", sortNum=" + sortNum +
        ", resUrl=" + resUrl +
        ", createDate=" + createDate +
        ", modifyDate=" + modifyDate +
        ", isDelete=" + isDelete +
        ", status=" + status +
        "}";
    }
}
