package com.maple.userapi.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 基础信息-用户信息
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@TableName("base_user")
@ApiModel(value = "BaseUser对象", description = "基础信息-用户信息")
public class BaseUser extends Model<BaseUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;

    //@JsonIgnore
    @ApiModelProperty(value = "密码")
    @TableField("pass_word")
    private String passWord;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "是否绑定邮箱(0：否；1：是）")
    @TableField("email_status")
    private Integer emailStatus;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "是否绑定QQ（0：否；1：是）")
    @TableField("qq_status")
    private Integer qqStatus;

    @ApiModelProperty(value = "QQ链接")
    @TableField("qq_link")
    private String qqLink;

    @ApiModelProperty(value = "是否绑定微信（0：否； 1：是）")
    @TableField("wx_status")
    private Integer wxStatus;

    @ApiModelProperty(value = "微信链接")
    @TableField("wx_link")
    private String wxLink;

    @TableField("create_date")
    private Date createDate;

    @TableField("modify_date")
    private Date modifyDate;

    @ApiModelProperty(value = "是否删除")
    @TableField("is_delete")
    private Integer isDelete;

    @ApiModelProperty(value = "是否锁定")
    @TableField("is_lock")
    private Integer isLock;

    @ApiModelProperty(value = "个性签名")
    private String content;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getQqStatus() {
        return qqStatus;
    }

    public void setQqStatus(Integer qqStatus) {
        this.qqStatus = qqStatus;
    }

    public String getQqLink() {
        return qqLink;
    }

    public void setQqLink(String qqLink) {
        this.qqLink = qqLink;
    }

    public Integer getWxStatus() {
        return wxStatus;
    }

    public void setWxStatus(Integer wxStatus) {
        this.wxStatus = wxStatus;
    }

    public String getWxLink() {
        return wxLink;
    }

    public void setWxLink(String wxLink) {
        this.wxLink = wxLink;
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

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                ", id=" + id +
                ", nickName=" + nickName +
                ", userName=" + userName +
                ", passWord=" + passWord +
                ", phone=" + phone +
                ", emailStatus=" + emailStatus +
                ", email=" + email +
                ", qqStatus=" + qqStatus +
                ", qqLink=" + qqLink +
                ", wxStatus=" + wxStatus +
                ", wxLink=" + wxLink +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", isDelete=" + isDelete +
                ", isLock=" + isLock +
                ", content=" + content +
                "}";
    }
}
