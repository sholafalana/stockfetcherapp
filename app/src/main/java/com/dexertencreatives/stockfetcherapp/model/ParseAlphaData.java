package com.dexertencreatives.stockfetcherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shola on 11/13/2018.
 */

public class ParseAlphaData implements Parcelable {

    private String mLatestTradingDay;
    private String mSymbol;
    private String mOpen;
    private String mHigh;
    private String mClose;
    private String mLow;
    private String mRange;
    private String mPreviousClose;
    private String mDailyChange;
    private String mChangePercent;

    public String getmLatestTradingDay() {
        return mLatestTradingDay;
    }

    public void setmLatestTradingDay(String latestTradingDay) {
        this.mLatestTradingDay = latestTradingDay;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String fromSymbol) {
        this.mSymbol = fromSymbol;
    }


    public String getOpen() {
        return mOpen;
    }

    public void setOpen(String open) {
        this.mOpen = open;
    }

    public String getHigh() {
        return mHigh;
    }

    public void setHigh(String high) {
        this.mHigh = high;
    }

    public String getClose() {
        return mClose;
    }

    public void setClose(String close) {
        this.mClose = close;
    }

    public String getLow() {
        return mLow;
    }

    public void setLow(String low) {
        this.mLow = low;
    }


    public String getRange() {
        return mRange;
    }

    public void setmRange(String volume) {
        this.mRange = volume;
    }

    public String getPrevClose() {
        return mPreviousClose;
    }

    public void setPrevClose(String prevClose) {
        this.mPreviousClose = prevClose;
    }

    public String getDailyChange() {
        return mDailyChange;
    }

    public void setDailyChange(String dailyChange) {
        this.mDailyChange = dailyChange;
    }

    public String getChangePercent() {
        return mChangePercent;
    }

    public void setChangePercent(String changePercent) {
        this.mChangePercent = changePercent;
    }

    public ParseAlphaData() {
    }

    public ParseAlphaData(Parcel read) {
        mLatestTradingDay = read.readString();
        mSymbol = read.readString();
        mOpen = read.readString();
        mHigh = read.readString();
        mClose = read.readString();
        mLow = read.readString();
        mRange = read.readString();
        mPreviousClose = read.readString();
        mDailyChange = read.readString();
        mChangePercent = read.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel write, int flags) {
        write.writeString(mLatestTradingDay);
        write.writeString(mSymbol);
        write.writeString(mOpen);
        write.writeString(mHigh);
        write.writeString(mLow);
        write.writeString(mRange);
        write.writeString(mPreviousClose);
        write.writeString(mDailyChange);
        write.writeString(mChangePercent);
    }

    public static final Creator<ParseAlphaData> CREATOR =
            new Creator<ParseAlphaData>() {
                @Override
                public ParseAlphaData createFromParcel(Parcel source) {
                    return new ParseAlphaData(source);
                }

                @Override
                public ParseAlphaData[] newArray(int size) {
                    return new ParseAlphaData[size];
                }
            };

    public String getLastRefreshed() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-mm");
        String date = "";
        try {
            Date newDate = format.parse(getmLatestTradingDay());
            format = new SimpleDateFormat("MM dd, yyyy");
            date = format.format(newDate);
        } catch (ParseException e) {

        }

        return date;
    }
}
