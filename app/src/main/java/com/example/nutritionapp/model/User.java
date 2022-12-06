package com.example.nutritionapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String firstName;
    String lastName;
    String password;
    int id;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    protected User(Parcel in){
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.password = in.readString();
        this.id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.password);
        dest.writeInt(id);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static  final  Creator<User> CREATOR =new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
