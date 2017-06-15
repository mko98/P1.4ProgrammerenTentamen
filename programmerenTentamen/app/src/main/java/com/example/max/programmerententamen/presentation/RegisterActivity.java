package com.example.max.programmerententamen.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.service.FilmRequest;

public class RegisterActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    private EditText usernameET;
    private EditText passwordET;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = (EditText) findViewById(R.id.usernameRegisterEditText);
        passwordET = (EditText) findViewById(R.id.passwordRegisterEditText);

        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!usernameET.getText().toString().equals("") && !passwordET.getText().toString().equals("")){
                        registerUser(usernameET.getText().toString(), passwordET.getText().toString());
                    }
                }catch (Exception e){
                    Log.i(TAG, e.toString());
                }
            }
        });
    }

    private void registerUser(String username, String password){
        FilmRequest request = new FilmRequest(getApplicationContext());
        request.handlePostUser(username, password);
    }
}
