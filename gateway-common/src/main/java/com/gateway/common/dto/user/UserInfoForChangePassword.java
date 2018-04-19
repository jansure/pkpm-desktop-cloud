package com.gateway.common.dto.user;

import lombok.Data;

/**
 * @author xuhe
 * @description
 * @date 2018/4/19
 */
@Data
public class UserInfoForChangePassword {

    private Integer userId;
    private String oldPassword;
    private String newPassword;
}
