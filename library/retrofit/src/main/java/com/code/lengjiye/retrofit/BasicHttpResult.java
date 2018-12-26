package com.code.lengjiye.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * 封装Http请求数据
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public class BasicHttpResult<T> {

    @SerializedName(value = "statusCode", alternate = {"code", "errcode"})
    private int errcode;

    @SerializedName(value = "msg", alternate = {"errmsg"})
    private String errmsg;
    private T data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
