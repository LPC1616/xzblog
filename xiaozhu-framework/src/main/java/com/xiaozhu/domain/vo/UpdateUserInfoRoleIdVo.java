package com.xiaozhu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoRoleIdVo {
    /**
     * 主键
     */
    private Long id;

    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    private String phonenumber;

    private String sex;

    private String email;

    private String status;

    private List<Long> roleIds;
}
