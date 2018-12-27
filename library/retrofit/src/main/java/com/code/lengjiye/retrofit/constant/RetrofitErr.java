/*
 * Copyright (c) 2018.
 * Author：Zhao
 * Email：joeyzhao1005@gmail.com
 */

package com.code.lengjiye.retrofit.constant;

/**
 * ErrorCode
 */
public interface RetrofitErr {
    /**
     * 成功
     */
    int OK = 0;

    /**
     * 参数错误
     */
    int MOBILE_ERRORDEFAULT = -1;
    /**
     * 没有该方法
     */
    int ERROR = 401;

    /**
     * 网络请求失败
     */
    int CURL_ERROR = 4000;
    /**
     * 用户不存在
     */
    int USER_EMPTY = 1000;

    /* 200 - 300 注册登录占用 */

    /**
     * tocken失效
     */
    int TOKEN_OVERDUE = 2000;

    /**
     * 异地登录
     */
    int TOKEN_FAIL = 2001;

    /**
     * 用户发表包含敏感词的言论，被删号
     */
    int SENSITIVE_WORD_ERROR = 3060;

    /**
     * 用户信息不存在
     */
    int USER_NOT_FOUND = 2010;

    /**
     * 手机号已注册
     */
    int MOBILE_FOUND = 2100;

    /**
     * 短信验证码发送失败
     */
    int SMS_SEND_FAILED = 2110;

    /**
     * 密码未发送变化
     */
    int PASSWORD_NO_CHANGE = 2120;

    /**
     * 密码格式错误
     */
    int PASSWORD_ERROR = 2130;

    /**
     * 验证码错误
     */
    int CAPTCHA_ERROR = 2140;

    /**
     * 手机号格式错误
     */
    int MOBILE_ERROR = 2150;

    /**
     * 用户被禁用
     */
    int DISABLE_USER = 2160;

    /**
     * 用户被拉黑
     */
    int BLACKLIST_USER = 2161;

    /**
     * 退出登录失败
     */
    int LOGOUT_FAILED = 2200;

    /**
     * 保存数据失败
     */
    int SAVE_FAILED = 3000;

    /*游戏评价相关301-400*/

    /**
     * 您已经评价过了
     */
    int ALREADY_COMMENT = 3010;

    /**
     * 非本人操作 ,删除,更新评论(只有自己才能操作)
     */
    int ACTION_INVALID = 3020;

    /**
     * 礼包已领完
     */
    int GIFT_BAG_RECEIVE_FINISHED = 3050;

    /**
     * 评测游戏已经下架
     */
    int EVALUATION_GAME_OFF = 3051;

    /**
     * 游戏已经下架
     */
    int GAME_OFF = 3052;


}
