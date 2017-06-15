package com.example.max.programmerententamen.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.service.Constants;
import com.example.max.programmerententamen.service.VolleyRequestQueue;
import com.android.volley.NetworkResponse;

import org.json.JSONException;
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

    private void handleLogin(String username, String password) {
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        Log.i(TAG, "handleLogin - body = " + body);

        try {
            JSONObject jsonObject = new JSONObject(body);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, Constants.URL_LOGIN, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            displayMessage("Succesvol ingelogd!");

                            try {
                                Log.i("test", response.getString("token"));
                                String token = response.getString("token");
                                Context context = getApplicationContext();
                                SharedPreferences sharedPreferences = context.getSharedPreferences(getString(
                                        R.string.preference_file_key), context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", token);

                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(main);

                                finish();
                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1500, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            loginErrorMessage.setText(e.getMessage());
        }
        return;
    }

    public void handleErrorResponse(VolleyError error){
        Log.e(TAG, "handleErrorResponse");

        if (error instanceof com.android.volley.AuthFailureError){
            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data !=null){
                json = new String(response.data);
                json = trimMessage(json, "error");
                if (json != null){
                    json = "Error " + response.statusCode + ": " + json;
                    displayMessage(json);
                }
            }else {
                Log.e(TAG, "handleErrorResponse: kon geen networkResponse vinden.");
            }
        } else if(error instanceof com.android.volley.NoConnectionError) {
            Log.e(TAG, "handleErrorResponse: server was niet bereikbaar");
            loginErrorMessage.setText(getString(R.string.error_server_offline));
        } else {
            Log.e(TAG, "handleErrorResponse: error = " + error);
        }
    }

    public String trimMessage(String json, String key){
        Log.i(TAG, "trimMessage: json = " + json);
        String trimmedString = null;

        try {
            JSONObject object = new JSONObject(json);
            trimmedString = object.getString(key);
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    public void displayMessage(String toastString){
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }

}
