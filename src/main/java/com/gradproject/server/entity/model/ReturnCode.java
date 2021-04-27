package com.gradproject.server.entity.model;

public enum ReturnCode {
    SUCCESS("20000", "处理成功"),
    FAILURE("50000", "处理失败"),
    BAD_REQUEST("40000", "错误的请求"),
    UNAUTHORIZED("40100","无权访问"),
    ILLEGAL_TOKEN("40315", "非法的token"),
    INVALIS_TOKEN("40316","无效的token"),
    TOKEN_EXPIRE("40317", "token 过期"),
    SSOTOKEN_EXPIRE("40318","sso登陆时token 过期"),
    DATA_MISS("40340", "数据异常，发送数据为空"),
    USERNAME_NOT_FOUND("40308", "用户名称未找到"),
    FORBIDDEN("40300", "无权访问"),
    METHOD_NOT_ALLOWED("40500", "提交方法的类型错误"),
    UNSUPPORTED_MEDIA_TYPE("41500", "不支持的Content-Type"),
    FILE_RESOLVE_ERROR("41600", "文件解析错误"),
    ;

    private String code;
    private String msg;
    ReturnCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
