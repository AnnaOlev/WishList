package com.example.wishlist.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wishlist.R;
import com.example.wishlist.RequestHandler;

import org.json.JSONObject;

public class AuthActivity extends AppCompatActivity {

    Button login;
    Button registration;
    EditText user_login;
    EditText user_password;
    String userName;
    String password;

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_auth);

        login = findViewById(R.id.login);
        registration = findViewById(R.id.registration);
        user_login = findViewById(R.id.userLogin);
        user_password = findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = user_login.getText().toString();
                password = user_password.getText().toString();
                if (!userName.equals("") & !password.equals("")) {
                    new AuthRequestAsync().execute();

                }
                else {
                    String text = "Введите все необходимые данные!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = user_login.getText().toString();
                password = user_password.getText().toString();
                if (!userName.equals("") & !password.equals("")) {
                    new RegRequestAsync().execute();

                }
                else {
                    String text = "Введите все необходимые данные!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }
            }
        });

    }

    public class AuthRequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", userName);
                postDataParams.put("password", password);

                return RequestHandler.sendPost("http://192.168.0.19:8500", postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if (s.equals("Авторизация успешна")) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    public class RegRequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("newusername", userName);
                postDataParams.put("newpassword", password);

                return RequestHandler.sendPost("http://192.168.0.19:8500", postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                if (s.equals("Регистрация успешна")) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
