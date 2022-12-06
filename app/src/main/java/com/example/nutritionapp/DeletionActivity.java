package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nutritionapp.fragments.RecyclerViewFragment;
import com.example.nutritionapp.model.AppointmentViewAdapter;
import com.example.nutritionapp.model.UserAppointments;

public class DeletionActivity extends AppCompatActivity implements AppointmentViewAdapter.ItemClickListener {
    public final static String EXTRA_MESSAGE = "Appointments";
    private UserAppointments userAppointments;
    private AppointmentViewAdapter ava;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletion);
        Bundle bundle = getIntent().getExtras();
        userAppointments = bundle.getParcelable(HomeActivity.EXTRA_MESSAGE);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerViewFragment appointmentsView = new RecyclerViewFragment();
        ava = new AppointmentViewAdapter(this,this.userAppointments.getAppointments());
        ava.setClickListener(this);
        appointmentsView.setAppointmentViewAdapter(ava);
        appointmentsView.setContext(this);
        appointmentsView.setAppointmentList(userAppointments.getAppointments());
        fragmentTransaction.replace(R.id.fragmentContainer, appointmentsView).commit();
    }

    @Override
    public void onItemClick(View view, int position) {
        this.userAppointments.popAppointment(ava.getAppointmentID(position));
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(EXTRA_MESSAGE, this.userAppointments);
        setResult(RESULT_OK, intent);
        finish();
    }
}