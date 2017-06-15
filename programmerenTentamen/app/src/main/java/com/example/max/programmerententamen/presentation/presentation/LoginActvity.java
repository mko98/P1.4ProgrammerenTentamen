package com.example.max.programmerententamen.presentation.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.presentation.service.Constants;

import org.json.JSONObject;

public class LoginActvity extends AppCompatActivity {

    private EditText usernameET;
    private EditText passwordET;

    private TextView loginErrorMessage;
    private TextView registerLinkTV;

    private Button loginBtn;

    private String mUsername;
    private String mPassword;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText) findViewById(R.id.usernameLoginEditText);
        passwordET = (EditText) findViewById(R.id.passwordLoginEditText);

        loginErrorMessage = (TextView) findViewById(R.id.loginErrorTextView);

        loginBtn = (Button) findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsername = usernameET.getText().toString();
                mPassword = passwordET.getText().toString();
                loginErrorMessage.setText("");

                handleLogin(mUsername, mPassword);
            }
        });

        registerLinkTV = (TextView) findViewById(R.id.registerLinkTextView);
        registerLinkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    private void handleLogin(String username, String password){
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        Log.i(TAG, "handleLogin - body = " + body);

        try {
            JSONObject jsonObject = new JSONObject(body);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Constants.URL_LOGIN, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            displayMessage("Succesvol ingelogd!");
                        }

                        }
                    }
    }
}
