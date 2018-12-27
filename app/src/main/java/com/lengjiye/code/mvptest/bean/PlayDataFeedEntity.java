package com.lengjiye.code.mvptest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author：luck
 * @date：2018/6/19 下午2:32
 * @describe：陪玩feed流
 */
public class PlayDataFeedEntity<T> implements Parcelable {
    /**
     * Banner
     */
    public static final int PW_FEED_BANNER = 1;

    /**
     * 快捷入口列表
     */
    public static final int PW_FEED_ICON = 2;

    /**
     * 推荐大神列表
     */
    public static final int PW_FEED_GOD = 3;

    /**
     * 聊天室列表
     */
    public static final int PW_FEED_ROOM = 4;

    /**
     * 陪玩品类
     */
    public static final int PW_FEED_GAME = 5;

    /**
     * 数据类型
     */
    private int ty;
    /**
     * 数据标题
     */
    private String ti;
    /**
     * 某类型下的具体json
     */
    private String body;

    /**
     * body对应的数据
     */
    private List<T> list;


    public PlayDataFeedEntity() {

    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ty);
        dest.writeString(this.ti);
        dest.writeString(this.body);
    }

    protected PlayDataFeedEntity(Parcel in) {
        this.ty = in.readInt();
        this.ti = in.readString();
        this.body = in.readString();
    }

    public static final Creator<PlayDataFeedEntity> CREATOR = new Creator<PlayDataFeedEntity>() {
        @Override
        public PlayDataFeedEntity createFromParcel(Parcel source) {
            return new PlayDataFeedEntity(source);
        }

        @Override
        public PlayDataFeedEntity[] newArray(int size) {
            return new PlayDataFeedEntity[size];
        }
    };
}
