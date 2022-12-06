package com.example.nutritionapp.model;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.lang.String;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Appointment implements Parcelable {
    int id;
    String name;
    Date date;
    Time time;

    public Appointment (int id, String name, Date date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setId(int id) {this.id = id;}

    protected Appointment(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.date = new Date(in.readString());
        this.time = Time.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.date.toString());
        dest.writeString(this.time.toString());
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static  final  Creator<Appointment> CREATOR =new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    @Override
    public String toString() {
        return "ID: " + this.id + "\n" +
                 "Acompañante: " + name + "\n" +
                 "Fecha: " + date.toString() + "\n" +
                 "Hora: " + time.toString();
    }
}