package com.example.cafecashier.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class CafeModel implements Parcelable {
    private String name;
    private String address;
    private int tax;
    private List<Menu> menus;
    private Hours hours;


    protected CafeModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        tax = in.readInt();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(tax);
        dest.writeTypedList(menus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CafeModel> CREATOR = new Creator<CafeModel>() {
        @Override
        public CafeModel createFromParcel(Parcel in) {
            return new CafeModel(in);
        }

        @Override
        public CafeModel[] newArray(int size) {
            return new CafeModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }
}
