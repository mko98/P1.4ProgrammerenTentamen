package com.example.max.programmerententamen.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilmMapper {


    public static final String FILM_TITLE = "title";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String INVENTORY_ID = "inventory_id";
    public static final String FILM_ID = "film_id";


    public static ArrayList<Film> rentalList(JSONArray response){

        ArrayList<Film> result = new ArrayList<>();

        try{
//            JSONArray jsonArray = response.getJSONArray(rental_RESULT);

            for(int i = 0; i < response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);

                Film film = new Film(

                        jsonObject.getString(FILM_TITLE)

                );




                result.add(film);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
