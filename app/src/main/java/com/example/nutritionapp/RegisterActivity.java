package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nutritionapp.fragments.TimePickerFragment;
import com.example.nutritionapp.model.Appointment;
import com.example.nutritionapp.model.User;
import com.example.nutritionapp.model.UserAppointments;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


public class RegisterActivity extends ToastActivity{
    public final static String EXTRA_MESSAGE = "Appointments";

    private User loggedInUser;
    private UserAppointments userAppointments;
    private Time appointmentTime;
    private Date appointmentDate;
    private Button calendarButton;
    private Button timeButton;
    private Button confirmButton;
    private TextView selectedTime;
    private TextView selectedDate;
    private TextView idView;
    private TextInputLayout til_guest;

    TimePickerFragment timePickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle bundle = getIntent().getExtras();
        userAppointments = bundle.getParcelable(HomeActivity.EXTRA_MESSAGE);
        loggedInUser = bundle.getParcelable(HomeActivity.EXTRA_MESSAGE2);
        calendarButton = (Button) findViewById(R.id.calendarButton);
        timeButton = (Button) findViewById(R.id.timePickerButton);
        confirmButton = (Button) findViewById(R.id.saveButton);
        selectedDate = (TextView) findViewById(R.id.chosenDate);
        selectedTime = (TextView) findViewById(R.id.chosenTime);
        idView = (TextView) findViewById(R.id.idTv);
        timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTextView(selectedTime);
        til_guest = (TextInputLayout) findViewById(R.id.til_guest);

        idView.setText(idView.getText().toString() + (this.userAppointments.getHighestId()+1));
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChoicesAndReturnToHome();
            }
        });
        setCalendarUp();
        setTimePickerUp();
    }

    private void setCalendarUp()
    {   //Code taken from lab 3

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Seleccione una fecha");
        final MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();
        //Display calendar on user's click
        calendarButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(),
                                "MATERIAL_DATE_PICKER");
                    }
                });

        //Save user's chosen date, generated by milliseconds returned by calendar.
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        selectedDate.setText(materialDatePicker.getHeaderText());
                        appointmentDate = new Date(selection);
                    }
                });
    }

    private void setTimePickerUp() {
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });
    }

    private void saveChoicesAndReturnToHome() {
        //ID is set by UsersAppointment Object when pushing in new appointment
        try{ appointmentTime = Time.valueOf(selectedTime.getText().toString()); }catch(IllegalArgumentException e){}
        if(appointmentDate != null && appointmentTime != null){

            Appointment newAppointment = new Appointment(0,
                    til_guest.getEditText().getText().toString(),
                    appointmentDate,
                    appointmentTime);

            this.userAppointments.pushAppointment(newAppointment);
            returnHome();
        } else
        {
            displayMessage("fecha u hora invalidas");
        }
    }

    private void returnHome() {
        //Send newly updated appointment list back to home.
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(EXTRA_MESSAGE, this.userAppointments);
        setResult(RESULT_OK, intent);
        finish();
    }


}