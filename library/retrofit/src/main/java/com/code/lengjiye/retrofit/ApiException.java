package com.code.lengjiye.retrofit;

/**
 * 服务器请求中，errorCode不为0的情况下，使用ApiException进行处理
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public class ApiException extends RuntimeException {

    private int mErrorCode;
    private String mErrorMsg;

    private Object data;

    public ApiException(int errorCode, String errorMsg, Object data) {
        super(errorMsg);
        this.mErrorCode = errorCode;
        this.mErrorMsg = errorMsg;
        this.data = data;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public Object getData() {
        return data;
    }
}
