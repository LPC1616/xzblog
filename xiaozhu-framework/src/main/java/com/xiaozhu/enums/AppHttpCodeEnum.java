package com.xiaozhu.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    CONTENT_NOT_NULL(506, "内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误，请上传png文件"),
    USERNAME_NOT_NULL(508, "用户名不能为空"),
    NICKNAME_NOT_NULL(509, "昵称不能为空"),
    PASSWORD_NOT_NULL(510, "密码不能为空"),
    EMAIL_NOT_NULL(511, "邮箱不能为空"),
    NICKNAME_EXIST(512, "昵称已存在"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    TAGNAME_NOT_NULL(513, "标签内容不能为空"),
    REMARK_NOT_NULL(514, "备注不能为空"),
    ADD_MENU_FAIL(515, "该菜单已存在"),
    DELETE_MENU_REFUSE(516, "不允许删除该菜单" ),
    ROLE_INFO_EXIST(517, "角色已存在"),
    ROLEKEY_INFO_EXIST(518, "key已存在"),
    DELETE_USER_REFUSE(519, "不允许删除该用户"),
    CATEGORY_IS_EXIST(520, "分类已存在"),
    DELETE_CATEGORY_FAIL(521, "删除类别失败"),
    LINK_IS_EXIST(522, "友链已存在"),
    DELETE_LINK_FAIL(523, "友链删除失败");



    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}