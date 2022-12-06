package com.example.nutritionapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class UserAppointments implements Parcelable {

    ArrayList<Appointment> appointments;
    int highestId;

    public UserAppointments()
    {
        this.highestId = 0;
        this.appointments = new ArrayList<Appointment>();
    }

    public void pushAppointment(Appointment newAppointment){
        newAppointment.setId(highestId + 1);
        highestId = newAppointment.getId();
        appointments.add(newAppointment);
    }

    public void popAppointment(int targetId) {
        boolean idFound = false;

        for(int i = 0; i < appointments.size() && idFound == false; i++)
        {
            System.out.println(i);
            System.out.println(appointments.get(i).getId() + " vs " + targetId);
            if(appointments.get(i).getId() == targetId)
            {
                System.out.println("hallado");
                appointments.remove(i);
                if(targetId >= highestId)
                {

                    findNewHighestId();
                }
                idFound = true;
            }
        }
    }

    private void findNewHighestId(){
        System.out.println("hay que buscar");
        if(appointments.size() > 0){
            System.out.println("hay que iterar");
            highestId = appointments.get(0).getId();
            int indexId = 0;
            for(int i = 0; i < appointments.size(); i++)
            {
                indexId = appointments.get(i).getId();
                if(indexId > highestId){
                    highestId = indexId;
                }
            }
        }else{
            System.out.println("lista esta vacia");
            //List is currently empty.
            highestId = 0;
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    protected UserAppointments(Parcel in){
        this.highestId = in.readInt();
        this.appointments = in.readArrayList(UserAppointments.class.getClassLoader());
    }

    public int getHighestId()
    {
        return highestId;
    }

    private Date generateDate(long date) {
        return new Date(date);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.highestId);
        dest.writeList(this.appointments);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static  final  Creator<UserAppointments> CREATOR =new Creator<UserAppointments>() {
        @Override
        public UserAppointments createFromParcel(Parcel source) {
            return new UserAppointments(source);
        }

        @Override
        public UserAppointments[] newArray(int size) {
            return new UserAppointments[size];
        }
    };

    @Override
    public String toString() {
       return this.appointments.get(0).toString();
    }
}
