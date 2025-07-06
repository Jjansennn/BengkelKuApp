package com.example.bengkelkuapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarItem implements Parcelable {
    private String brand, model, year, plate;
    private int imageRes;

    public CarItem(String brand, String model, String year, String plate, int imageRes) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.plate = plate;
        this.imageRes = imageRes;
    }

    protected CarItem(Parcel in) {
        brand = in.readString();
        model = in.readString();
        year = in.readString();
        plate = in.readString();
        imageRes = in.readInt();
    }

    public static final Creator<CarItem> CREATOR = new Creator<CarItem>() {
        @Override
        public CarItem createFromParcel(Parcel in) {
            return new CarItem(in);
        }

        @Override
        public CarItem[] newArray(int size) {
            return new CarItem[size];
        }
    };

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public String getPlate() {
        return plate;
    }

    public int getImageRes() {
        return imageRes;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(model);
        dest.writeString(year);
        dest.writeString(plate);
        dest.writeInt(imageRes);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
