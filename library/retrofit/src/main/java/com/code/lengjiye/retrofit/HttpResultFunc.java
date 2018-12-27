package com.code.lengjiye.retrofit;


import com.google.gson.Gson;
import com.lengjiye.tools.StringTool;

import io.reactivex.functions.Function;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public class HttpResultFunc<T> implements Function<BasicHttpResult<T>, T> {

    @Override
    public T apply(BasicHttpResult<T> baseHttpResult) {
        int errorCode = baseHttpResult.getErrcode();

        if (errorCode == 0) {
            return baseHttpResult.getData();
        } else {

            String originalData = null;
            try {
                originalData = new Gson().toJson(baseHttpResult);
            } catch (Exception e) {

            }

            if (StringTool.isEmpty(originalData)) {
                originalData = "";
            }
            throw new ApiException(errorCode, baseHttpResult.getErrmsg(), originalData);
        }
    }
}
