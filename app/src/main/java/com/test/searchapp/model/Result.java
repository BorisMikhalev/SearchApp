package com.test.searchapp.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable{
    private String title;
    private String description;
    private String photoUrl;

    public Result(String title, String description, String photoUrl) {
        this.title = title;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public Result(Parcel parcel) {
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.photoUrl = parcel.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(photoUrl);
    }

    public static Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[0];
        }
    };
}
