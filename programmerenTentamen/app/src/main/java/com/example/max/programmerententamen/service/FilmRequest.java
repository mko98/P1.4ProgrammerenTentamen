package com.example.max.programmerententamen.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.domain.Film;
import com.example.max.programmerententamen.domain.FilmMapper;
import com.example.max.programmerententamen.presentation.LoginActivity;
import com.example.max.programmerententamen.presentation.MainActivity;
import com.example.max.programmerententamen.presentation.RegisterActivity;


/**
 * Deze class handelt requests naar de API server af. De JSON objecten die we terug krijgen
 * worden door de ToDoMapper vertaald naar (lijsten van) ToDo items.
 */
public class FilmRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    // De aanroepende class implementeert deze interface.
    private FilmRequest.CityListener listener;

    /**
     * Constructor
     *
     * @param context
     * @param listener
     */
    public FilmRequest(Context context, FilmRequest.CityListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public FilmRequest(Context context) {
        this.context = context;

    }

    /**
     * Verstuur een GET request om alle cities op te halen.
     */
    public void handleGetAllCities() {

        Log.i(TAG, "handleGetAllCities");

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString("token", "");
        if(token != null && !token.equals("")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
//            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
//                    Request.Method.GET,
//                    Constants.URL_CITIES,
//                    null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            // Succesvol response
//                            Log.i(TAG, response.toString() + "testetet");
//                            ArrayList<Film> result = FilmMapper.cityList(response);
//                            listener.onCitiesAvailable(result);
//                        }
//                    },

//                    kijken hoe je array ophaalt..
                    JsonArrayRequest jsArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.URL_FILMS,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // Succesvol response
                            Log.i(TAG, response.toString() + "testetet");
                            ArrayList<Film> result = FilmMapper.cityList(response);
                            listener.onCitiesAvailable(result);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove("token");
                                Intent i = new Intent(context, LoginActivity.class);
                                Log.i("token expired", "Log opnieuw in");
                            Toast.makeText(context,"Log opnieuw in", Toast.LENGTH_SHORT).show();

                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);

                            Log.e(TAG, error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Token", token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsArrayRequest);
        }
    }

    /**
     * Verstuur een POST met nieuwe Film.
     */
    public void handlePostCity(final Film newFilm) {

        Log.i(TAG, "handlePostCity");

        // Haal het token uit de prefs
        // Film Verplaats het ophalen van het token naar een centraal beschikbare 'utility funtion'
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString("token", "");
        if(token != null && !token.equals("")) {

            //
            // Maak een JSON object met username en password. Dit object sturen we mee
            // als request body (zoals je ook met Postman hebt gedaan)
            //
            String body = "{\"name\":\"" + newFilm.getName() + "\"}";

            try {
                JSONObject jsonBody = new JSONObject(body);
                Log.i(TAG, "handlePostCity - body = " + jsonBody);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        Constants.URL_FILMS,
                        jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, response.toString());
                                // Het toevoegen is gelukt
                                // Hier kun je kiezen: of een refresh door de hele lijst op te halen
                                // en de ListView bij te werken ... Of alleen de ene update toevoegen
                                // aan de ArrayList. Wij doen dat laatste.
                                listener.onCityAvailable(newFilm);
                                Intent in = new Intent(context, MainActivity.class);
                                context.startActivity(in);
                                Log.i(TAG, "toevoegen gelukt");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

//                                listener.onCitiesError(error.toString());

//                                if (error.toString().equals("com.android.volley.AuthFailureError")) {
                                if (error.networkResponse.statusCode == 401) {
                                    Log.i(TAG, error.toString());
                                    SharedPreferences sharedPref = context.getSharedPreferences(
                                            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.remove("token");
                                    Intent i = new Intent(context, LoginActivity.class);
                                    Log.i(TAG, "Log opnieuw in");

                                    String body = "Error 401";
                                    if(error.networkResponse.data!=null) {
                                        try {
                                            body = new String(error.networkResponse.data,"UTF-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Toast.makeText(context, body, Toast.LENGTH_SHORT).show();

                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(i);
                                } else if (error.toString().equals("com.android.volley.ServerError")) {
                                    String body = "";
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    if(error.networkResponse.data!=null) {
                                        try {
                                            body = new String(error.networkResponse.data,"UTF-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Toast.makeText(context, statusCode + " " + body, Toast.LENGTH_SHORT).show();
                                } else {
                                    String body = "";
                                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                                    if(error.networkResponse.data!=null) {
                                        try {
                                            body = new String(error.networkResponse.data,"UTF-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Toast.makeText(context, statusCode + " " + body, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Token", token);
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
    }

    public void handleEditCity(final Film editFilm) {

        Log.i(TAG, "handleEditCity");

        // Haal het token uit de prefs
        // Film Verplaats het ophalen van het token naar een centraal beschikbare 'utility funtion'
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString("token", "");
        if(token != null && !token.equals("")) {

            String body = "{\"name\":\"" + editFilm.getName()   + "\"}";

            try {
                JSONObject jsonBody = new JSONObject(body);
                Log.i(TAG, "handlePostCity - body = " + jsonBody);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.PUT,
                        Constants.URL_FILMS + "/" + editFilm.getName(),
                        jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, response.toString());
                                Log.i(TAG, "aanpassen gelukt");

                                Intent in = new Intent(context, MainActivity.class);
                                context.startActivity(in);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error - send back to caller
//                                listener.onCitiesError(error.toString());
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove("token");
                                Intent i = new Intent(context, LoginActivity.class);
                                Log.i(TAG, "Log opnieuw in");
                                Toast.makeText(context, "Log opnieuw in", Toast.LENGTH_SHORT).show();

                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);
                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Token", token);
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
    }

    public void handleDeleteCity(final Film editFilm) {

        Log.i(TAG, "handleDeleteCity");

        // Haal het token uit de prefs
        // Film Verplaats het ophalen van het token naar een centraal beschikbare 'utility funtion'
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString("token", "");
        if(token != null && !token.equals("")) {


            try {
//                JSONObject jsonBody = null;
//                Log.i(TAG, "handlePostCity - body = " + jsonBody);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                        Request.Method.DELETE,
                        Constants.URL_FILMS + "/" + editFilm.getName(),
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i(TAG, response.toString());
                                Log.i(TAG, "verwijderen gelukt");

                                Intent in = new Intent(context, MainActivity.class);
                                context.startActivity(in);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error - send back to caller
//                                listener.onCitiesError(error.toString());
                                SharedPreferences sharedPref = context.getSharedPreferences(
                                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove("token");
                                Intent i = new Intent(context, LoginActivity.class);
                                Log.i(TAG, "Log opnieuw in");
                                Toast.makeText(context, "Log opnieuw in", Toast.LENGTH_SHORT).show();

                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);
                            }
                        }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Token", token);
                        return headers;
                    }
                };

                // Access the RequestQueue through your singleton class.
                VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
//                listener.onCitiesError(e.getMessage());
            }
        }
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

                            Intent in = new Intent(context, LoginActivity.class);
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
    public interface CityListener {
        // Callback function to return a fresh list of Cities
        void onCitiesAvailable(ArrayList<Film> cities);

        // Callback function to handle a single added Film.
        void onCityAvailable(Film film);

        // Callback to handle serverside API errors
        void onCitiesError(String message);
    }
}



