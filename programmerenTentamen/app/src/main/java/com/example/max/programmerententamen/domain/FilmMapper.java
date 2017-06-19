package com.example.max.programmerententamen.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilmMapper {

    public static final String rental_ID = "ID";
    public static final String rental_NAME = "title";
    public static final String rental_COUNTRYCODE = "CountryCode";
    public static final String rental_DISTRICT = "District";
    public static final String rental_POPULATION = "Population";

    public static ArrayList<Film> rentalList(JSONArray response){

        ArrayList<Film> result = new ArrayList<>();

        try{
//            JSONArray jsonArray = response.getJSONArray(rental_RESULT);

            for(int i = 0; i < response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);

                Film film = new Film(
//                        jsonObject.getInt(rental_ID),
                        jsonObject.getString(rental_NAME)

                );
                result.add(film);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
