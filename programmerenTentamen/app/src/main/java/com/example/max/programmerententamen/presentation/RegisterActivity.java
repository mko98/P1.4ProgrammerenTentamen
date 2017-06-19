package com.example.max.programmerententamen.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.service.FilmRequest;

public class RegisterActivity  extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    private EditText et_username, et_password;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = (EditText) findViewById(R.id.usernameRegisterEditText);
        et_password = (EditText) findViewById(R.id.passwordRegisterEditText);

        registerBtn = (Button) findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (!et_username.getText().toString().equals("") && !et_password.getText().toString().equals("")) {
                            registerUser(et_username.getText().toString(), et_password.getText().toString());
                        }
                    } catch (Exception e){
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
