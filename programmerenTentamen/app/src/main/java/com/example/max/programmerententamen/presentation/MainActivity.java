package com.example.max.programmerententamen.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.domain.Film;
import com.example.max.programmerententamen.domain.FilmAdapter;
import com.example.max.programmerententamen.service.FilmRequest;

public class MainActivity extends AppCompatActivity implements FilmRequest.rentalListener {

    // Logging tag
    public final String TAG = this.getClass().getSimpleName();

    // The name for communicating Intents extras
    public final static String rental_DATA = "title";

    // A request code for returning data from Intent - is supposed to be unique.
    public static final int MY_REQUEST_CODE = 1234;

    // UI Elements
    private ListView listViewFilms;
    private ArrayAdapter filmAdapter;
    private ArrayList<Film> films = new ArrayList<>();
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We kijken hier eerst of de gebruiker nog een geldig token heeft.
        // Het token is opgeslagen in SharedPreferences.
        // Mocht er geen token zijn, of het token is expired, dan moeten we
        // eerst opnieuw inloggen.
        if(tokenAvailable()){
            setContentView(R.layout.activity_main);

            backBtn = (Button) findViewById(R.id.logoutButton);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(in);
                    finish();
                }
            });

            listViewFilms = (ListView) findViewById(R.id.filmsListView);
            filmAdapter = new FilmAdapter(getApplicationContext(), films);
            listViewFilms.setAdapter(filmAdapter);
            listViewFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(view.getContext(), DetailActivity.class);
                    Film film = films.get(position);
                    i.putExtra("Film", (Serializable) film);
                    startActivity(i);
                }
            });
            //
            // We hebben een token. Je zou eerst nog kunnen valideren dat het token nog
            // geldig is; dat doen we nu niet.
            // Vul de lijst met ToDos
            //
            Log.d(TAG, "Token gevonden - rentals ophalen!");
            getrentals();
        } else {
            //
            // Blijkbaar was er geen token - eerst inloggen dus
            //
            Log.d(TAG, "Geen token gevonden - inloggen dus");
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
            // Sluit de huidige activity. Dat voorkomt dat de gebuiker via de
            // back-button zonder inloggen terugkeert naar het homescreen.
            finish();
        }
    }

    /**
     * Aangeroepen door terugkerende Intents. Maakt het mogelijk om data
     * terug te ontvangen van een Intent.
     *
     * @param requestCode
     * @param resultCode
     * @param pData
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent pData)
    {
        if ( requestCode == MY_REQUEST_CODE )
        {
            Log.v( TAG, "onActivityResult OK" );
            if (resultCode == Activity.RESULT_OK )
            {
                final Film newFilm = (Film) pData.getSerializableExtra(rental_DATA);
                Log.v( TAG, "Retrieved Value newFilm is " + newFilm);

                // We need to save our new Film
                postrental(newFilm);
            }
        }

    }

    /**
     * Check of een token in localstorage is opgeslagen. We checken niet de geldigheid -
     * alleen of het token bestaat.
     *
     * @return
     */
    private boolean tokenAvailable() {
        boolean result = false;

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "");
        if (token != null && !token.equals("")) {
            result = true;
        }
        return result;
    }

    @Override
    public void onrentalsAvailable(ArrayList<Film> filmArrayList) {

        Log.i(TAG, "We hebben " + filmArrayList.size() + " items in de lijst");

        films.clear();
        for(int i = 0; i < filmArrayList.size(); i++) {
            films.add(filmArrayList.get(i));
        }
        filmAdapter.notifyDataSetChanged();
    }


    @Override
    public void onrentalAvailable(Film film) {
        films.add(film);
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onrentalsError(String message) {
        Log.e(TAG, message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Start the activity to GET all ToDos from the server.
     */
    private void getrentals(){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handleGetAllrentals();
    }

    /**
     * Start the activity to POST a new Film to the server.
     */
    private void postrental(Film film){
        FilmRequest request = new FilmRequest(getApplicationContext(), this);
        request.handlePostrental(film);
    }

}



