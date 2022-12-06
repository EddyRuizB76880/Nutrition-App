package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nutritionapp.model.Appointment;
import com.example.nutritionapp.model.User;
import com.example.nutritionapp.model.UserAppointments;

public class HomeActivity extends ToastActivity {
    private User loggedInUser;
    private TextView welcomeMessage;
    private Button registerButton;
    private Button deleteButton;
    private Button queryButton;
    private Button redirectButton;

    private UserAppointments userAppointments;
    public final static String EXTRA_MESSAGE = "Appointments";
    public final static String EXTRA_MESSAGE2 = "User";
    private final static int SECOND_ACTIVITY_RESULT_CODE = 0;
    private final static int THIRD_ACTIVITY_RESULT_CODE = 1;

    private final String NUTRITION_URL = "https://www.nutricion.ucr.ac.cr/index.php/es/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userAppointments = new UserAppointments();

        //Recover user data acquired from login screen
        Bundle bundle = getIntent().getExtras();
        loggedInUser = bundle.getParcelable(MainActivity.EXTRA_MESSAGE);

        // Retrieve relevant UI Pieces from XML file.
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        registerButton = (Button) findViewById(R.id.create_button);
        deleteButton = (Button) findViewById(R.id.delete_button);
        queryButton = (Button) findViewById(R.id.consult_button);
        redirectButton = (Button) findViewById(R.id.nutrition_button);


        //Set data and register events on each button
        welcomeMessage.setText(welcomeMessage.getText().toString() + loggedInUser.getFirstName());
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDeleteActivity();
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchConsultActivity();
            }
        });

        redirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToNutrition();
            }
        });
    }

    private void launchRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(EXTRA_MESSAGE, userAppointments);
        intent.putExtra(EXTRA_MESSAGE2, loggedInUser);
        startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
    }

    private void launchDeleteActivity()
    {
        Intent intent = new Intent(this, DeletionActivity.class);
        intent.putExtra(EXTRA_MESSAGE, userAppointments);
        startActivityForResult(intent, THIRD_ACTIVITY_RESULT_CODE);

    }

    private void launchConsultActivity()
    {
        Intent intent = new Intent(this, ConsultActivity.class);
        intent.putExtra(EXTRA_MESSAGE, userAppointments);
        intent.putExtra(EXTRA_MESSAGE2, loggedInUser);
        startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
    }

    private void redirectToNutrition()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(NUTRITION_URL));
        startActivity(intent);
    }

    //El método se llama cuando la segunda actividad termina
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE){
            if (resultCode == RESULT_OK){
                this.userAppointments = data.getExtras().getParcelable(RegisterActivity.EXTRA_MESSAGE);
                displayMessage("cita agendada");
            }
        }

        if (requestCode == THIRD_ACTIVITY_RESULT_CODE){
            if (resultCode == RESULT_OK){
                this.userAppointments = data.getExtras().getParcelable(DeletionActivity.EXTRA_MESSAGE);
                displayMessage("Cambios guardados exitosamente");
            } else
            {
                displayMessage("operacion cancelada");
            }
        }
    }
}