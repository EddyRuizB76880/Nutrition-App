package com.example.nutritionapp;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.nutritionapp.model.User;
import com.example.nutritionapp.model.Users;
import com.google.gson.Gson;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.regex.Pattern;

public class MainActivity extends ToastActivity{
    public final static String EXTRA_MESSAGE = "user";
    String UCR_LOGO = "https://www.ucr.ac.cr/plantillas/ucr_4/imagenes/firma-ucr-ico.png";
    String usersUrlFirstHalf = "https://dummyjson.com/users/filter?key=username&value=";
    String usersUrlSecondHalf = "&select=firstName,lastName,password";
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Picasso.get().load(UCR_LOGO).into((ImageView)findViewById(R.id.logoUCR));
        tilUsername = (TextInputLayout) findViewById(R.id.til_username);
        tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        loginButton = (Button) findViewById(R.id.boton_login);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCredentials();
            }
        });

    }


    public void launchHome(User user){
        System.out.println("inicio sesion");
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(EXTRA_MESSAGE, user);
        startActivity(intent);
    }

    public void validateCredentials()
    {
        String nombre = tilUsername.getEditText().getText().toString();
        if(isValidUserName(nombre)){
            String searchURL = usersUrlFirstHalf + nombre + usersUrlSecondHalf;
            StringRequest myRequest = new StringRequest(Request.Method.GET,
                    searchURL,
                    response -> {
                        try{
                            displayMessage("send");
                            Gson gson = new Gson();
                            Users users = gson.fromJson(response, Users.class);

                            if(users.total > 0)
                            {
                                String inputPassword = tilPassword.getEditText().getText().toString();
                                if(users.getUsers()[0].getPassword().equals(inputPassword)){
                                    launchHome(users.getUsers()[0]);
                                }else {
                                    displayMessage("contraseña incorrecta");
                                }

                            }else
                            {
                                displayMessage("credenciales incorrectas");
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                   null
            );
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(myRequest);

        }
    }



    public boolean isValidUserName(String username)
    {
        //Code taken from lab 03
        Pattern patron = Pattern.compile("^[a-zA-Z0-9 ]+$");
        if (!patron.matcher(username).matches() || username.length() > 30) {
            tilUsername.setError("Nombre inválido");
            return false;
        } else {
            tilUsername.setError(null);
        }
        return true;
    }

}
