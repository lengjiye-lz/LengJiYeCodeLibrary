package com.lengjiye.code.mvptest.service;

import com.code.lengjiye.retrofit.BasicHttpResult;
import com.code.lengjiye.retrofit.constant.ServerConstants;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/27
 * 修改备注:
 */
public interface MvpTestService {

    /**
     * 登录态修改密码获取验证码
     *
     * @return
     */
    @GET(ServerConstants.DOWNLOAD_LOG)
    Observable<BasicHttpResult<Object>> sendCheckMobile();

//    /**
//     * 获取陪玩首页feed流数据
//     *
//     * @param body
//     * @return
//     */
//    @POST(ServerConstants.PLAY_GOD_GAME_FEEDS)
//    Observable<BasicHttpResult<PlayHomeFeedData>> sendCheckMobile(@Body RequestBody body);

}
