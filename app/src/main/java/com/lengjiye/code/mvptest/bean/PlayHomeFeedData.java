package com.lengjiye.code.mvptest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：luck
 * @date：2018/6/19 下午3:43
 * @describe：描述
 */
public class PlayHomeFeedData implements Parcelable {
    private List<PlayDataFeedEntity> feeds;
    private int p;

    public List<PlayDataFeedEntity> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<PlayDataFeedEntity> feeds) {
        this.feeds = feeds;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.feeds);
        dest.writeInt(this.p);
    }

    public PlayHomeFeedData() {
    }

    protected PlayHomeFeedData(Parcel in) {
        this.feeds = new ArrayList<PlayDataFeedEntity>();
        in.readList(this.feeds, PlayDataFeedEntity.class.getClassLoader());
        this.p = in.readInt();
    }

    public static final Creator<PlayHomeFeedData> CREATOR = new Creator<PlayHomeFeedData>() {
        @Override
        public PlayHomeFeedData createFromParcel(Parcel source) {
            return new PlayHomeFeedData(source);
        }

        @Override
        public PlayHomeFeedData[] newArray(int size) {
            return new PlayHomeFeedData[size];
        }
    };
}
