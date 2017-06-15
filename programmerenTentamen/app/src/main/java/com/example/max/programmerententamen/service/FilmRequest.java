package com.example.max.programmerententamen.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.max.programmerententamen.domain.Film;
import com.example.max.programmerententamen.presentation.LoginActvity;
import com.example.max.programmerententamen.presentation.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 15-6-2017.
 */

public class FilmRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private FilmRequest.FilmListener listener;

    public FilmRequest(Context context, FilmRequest.FilmListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public FilmRequest(Context context) {
        this.context = context;
    }


    public void handlePostUser(String username, String password) {

        Log.i(TAG, "handlePostUser");
        //
        // Maak een JSON object met username en password. Dit object sturen we mee
        // als request body (zoals je ook met Postman hebt gedaan)
        //
        String body = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        try {
            JSONObject jsonBody = new JSONObject(body);
            Log.i(TAG, "handlePostUser - body = " + jsonBody);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    Constants.URL_REGISTER,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, response.toString());
                            Toast.makeText(context, "U kunt nu inloggin met uw account", Toast.LENGTH_SHORT).show();

                            Intent in = new Intent(context, LoginActvity.class);
                            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(in);

                            Log.i(TAG, "toevoegen user gelukt");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Intent i = new Intent(context, RegisterActivity.class);
                            Log.i(TAG, "iets fout gegaan met registeren");
                            Toast.makeText(context, "Probeer het opnieuw", Toast.LENGTH_SHORT).show();
                            context.startActivity(i);
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
//                listener.onCitiesError(e.getMessage());
        }
    }

    //
    // Callback interface - implemented by the calling class (MainActivity in our case).
    //


    public interface FilmListener {
        // Callback function to return a fresh list of Cities
        void onCitiesAvailable(ArrayList<Film> cities);

        // Callback function to handle a single added City.
        void onCityAvailable(Film city);

        // Callback to handle serverside API errors
        void onCitiesError(String message);
    }
}
