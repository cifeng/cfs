package com.platform.cfs.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class SystemUser extends BaseEntity{
    private String id;

    private String username;

    @NotBlank(message = "姓名不能为空")
    private String name;

    private String cardNum;

    @NotBlank(message = "手机号不能为空")
    private String mobile;

    private String balance;

    private Integer frequency;

    private String address;

    private String remark;

    private Integer type;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    private Date lastTime;

    private String password;

}