package com.dexertencreatives.stockfetcherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: shola
 * Created by: ModelGenerator on 11/10/2018
 */
public class ParseData implements Parcelable {
    private String symbol;
    private String price;
    private String timestamp;


    public ParseData(String symbol, String price, String timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.symbol);
        dest.writeString(this.price);
        dest.writeString(this.timestamp);
    }

    protected ParseData(Parcel in) {
        this.symbol = in.readString();
        this.price = in.readString();
        this.timestamp = in.readString();
    }

    public String getSymbol() {
        return symbol;
    }


    public String getPrice() {
        return price;
    }


    public String getTimeStamp() {
        return timestamp;
    }

    public static final Creator<ParseData> CREATOR = new Creator<ParseData>() {
        @Override
        public ParseData createFromParcel(Parcel source) {
            return new ParseData(source);
        }

        @Override
        public ParseData[] newArray(int size) {
            return new ParseData[size];
        }
    };
}