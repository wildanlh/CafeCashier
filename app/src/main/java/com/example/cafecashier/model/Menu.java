package com.example.cafecashier.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {
    private String url;
    private String name;
    private int price;
    private int totalCheckOut;

    protected Menu(Parcel in) {
        url = in.readString();
        name = in.readString();
        price = in.readInt();
        totalCheckOut = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(totalCheckOut);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalCheckOut() {
        return totalCheckOut;
    }

    public void setTotalCheckOut(int totalCheckOut) {
        this.totalCheckOut = totalCheckOut;
    }
}
