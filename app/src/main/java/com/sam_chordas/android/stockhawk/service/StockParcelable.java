package com.sam_chordas.android.stockhawk.service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raanand on 10/10/16.
 */

public class StockParcelable implements Parcelable{

    public String date;
    public double closeValue;

    protected StockParcelable(Parcel in) {
        date = in.readString();
        closeValue = in.readDouble();
    }

    public StockParcelable(String date, double closeValue) {
        this.date = date;
        this.closeValue = closeValue;
    }

    public static final Creator<StockParcelable> CREATOR = new Creator<StockParcelable>() {
        @Override
        public StockParcelable createFromParcel(Parcel in) {
            return new StockParcelable(in);
        }

        @Override
        public StockParcelable[] newArray(int size) {
            return new StockParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeDouble(closeValue);
    }
}