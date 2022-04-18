package com.haibin.myzhxy.pojo;

import lombok.Data;

/**
 * @project ssm_sms
 * @description 用户登录信息
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
