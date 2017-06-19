package com.example.max.programmerententamen.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maikel on 1-6-2017.
 */

public class FilmMapper {

    public static final String CITY_ID = "ID";
    public static final String CITY_NAME = "title";
    public static final String CITY_COUNTRYCODE = "CountryCode";
    public static final String CITY_DISTRICT = "District";
    public static final String CITY_POPULATION = "Population";

    public static ArrayList<Film> cityList(JSONArray response){

        ArrayList<Film> result = new ArrayList<>();

        try{
//            JSONArray jsonArray = response.getJSONArray(CITY_RESULT);

            for(int i = 0; i < response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);

                Film film = new Film(
//                        jsonObject.getInt(CITY_ID),
                        jsonObject.getString(CITY_NAME)

                );
                result.add(film);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}