package com.gradproject.server.entity.model;

public class SelfResponse {

    private static final String OK = "ok";
    private static final String ERROR = "error";

    private Meta result;
    private Object data;

    public SelfResponse success() {
        this.result = new Meta(true, OK, ReturnCode.SUCCESS);
        return this;
    }

    public SelfResponse success(Object data) {
        this.result = new Meta(true, OK, ReturnCode.SUCCESS);
        this.data = data;
        return this;
    }


    public SelfResponse failure() {
        this.result = new Meta(false, ERROR, ReturnCode.FAILURE);
        return this;
    }

    public SelfResponse failure(String message) {
        this.result = new Meta(false, message, ReturnCode.FAILURE);
        return this;
    }

    public SelfResponse failure(String message, ReturnCode code) {
        this.result = new Meta(false, message, code);
        this.data = "请重新登录";
        return this;
    }

    public Meta getResult() {
        return result;
    }

    public Object getData() {
        return data;
    }

    public class Meta {

        private boolean success;  //true or false

        private String message;   //处理信息   可传错误信息

        private ReturnCode code;  //返回码，后端统一定义，前端与后端保持一致

        public Meta(boolean success) {
            this.success = success;
        }

        public Meta(boolean success, String message, ReturnCode code) {
            this.success = success;
            this.message = message;
            this.code = code;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code.toString();
        }


    }
}
