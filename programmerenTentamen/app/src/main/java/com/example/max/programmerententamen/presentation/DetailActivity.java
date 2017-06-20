package com.example.max.programmerententamen.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.domain.Film;
import com.example.max.programmerententamen.service.FilmRequest;

public class DetailActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    private TextView et_name;
    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        if(tokenAvailable()){


            et_name = (TextView) findViewById(R.id.filmTitleTextView);


            Bundle extras = getIntent().getExtras();
            film = (Film) extras.getSerializable("Film");
            et_name.setText(film.getName());

        } else {
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
            finish();
        }
    }

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

    private void editrental(Film film){
        FilmRequest request = new FilmRequest(getApplicationContext());
        request.handleEditrental(film);
    }

    private void deleterental(Film film){
        FilmRequest request = new FilmRequest(getApplicationContext());
        request.handleDeleterental(film);
    }

    private void postrental(Film film){
        FilmRequest request = new FilmRequest(getApplicationContext());
        request.handlePostrental(film);
    }
}