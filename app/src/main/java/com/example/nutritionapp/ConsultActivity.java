package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nutritionapp.fragments.RecyclerViewFragment;
import com.example.nutritionapp.model.AppointmentViewAdapter;
import com.example.nutritionapp.model.User;
import com.example.nutritionapp.model.UserAppointments;

public class ConsultActivity extends ToastActivity {

    private UserAppointments userAppointments;
    private User loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        Bundle bundle = getIntent().getExtras();
        userAppointments = bundle.getParcelable(HomeActivity.EXTRA_MESSAGE);
        loggedInUser = bundle.getParcelable(HomeActivity.EXTRA_MESSAGE2);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecyclerViewFragment appointmentsView = new RecyclerViewFragment();
        appointmentsView.setAppointmentViewAdapter(new AppointmentViewAdapter(this
                                                , this.userAppointments.getAppointments()));
        appointmentsView.setContext(this);
        appointmentsView.setAppointmentList(this.userAppointments.getAppointments());
        fragmentTransaction.replace(R.id.fragmentContainer, appointmentsView).commit();
    }
}