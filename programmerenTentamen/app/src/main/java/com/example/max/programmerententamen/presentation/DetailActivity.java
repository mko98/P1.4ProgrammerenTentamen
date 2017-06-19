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

import com.example.max.programmerententamen.R;
import com.example.max.programmerententamen.domain.Film;
import com.example.max.programmerententamen.service.FilmRequest;

public class DetailActivity extends AppCompatActivity {

//    public final String TAG = this.getClass().getSimpleName();
//    private EditText et_name, et_countryCode, et_district, et_population, et_oName, et_oCountryCode, et_oDistrict, et_oPopulation;
//    private Button editBtn, deleteBtn, addBtn, backBtn;
//    private Film film;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detailed);
//        if(tokenAvailable()){
//
//            et_oName = (EditText) findViewById(R.id.activity_rental_det_et_oName);
//            et_oCountryCode = (EditText) findViewById(R.id.activity_rental_det_et_oCountryCode);
//            et_oDistrict = (EditText) findViewById(R.id.activity_rental_det_et_oDistrict);
//            et_oPopulation = (EditText) findViewById(R.id.activity_rental_det_et_oPopulation);
//
//            et_name = (EditText) findViewById(R.id.activity_rental_det_editText_name);
//            et_countryCode = (EditText) findViewById(R.id.activity_rental_det_editText_countryCode);
//            et_district = (EditText) findViewById(R.id.activity_rental_det_editText_district);
//            et_population = (EditText) findViewById(R.id.activity_rental_det_editText_population);
//
//            editBtn = (Button) findViewById(R.id.activity_rental_det_btn_editrental);
//            editBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        if (!et_oName.getText().toString().equals("") && !et_oCountryCode.getText().toString().equals("") && !et_oDistrict.getText().toString().equals("") && Integer.parseInt(et_oPopulation.getText().toString()) > 0) {
//                            film.setName(et_oName.getText().toString());
//
//                            editrental(film);
////                            Intent in = new Intent(getApplicationContext(), MainActivity.class);
////                            startActivity(in);
//                        }
//                    } catch (Exception e){
//                        Log.e(TAG, e.toString());
//                    }
//                }
//            });
//
//            deleteBtn = (Button) findViewById(R.id.activity_rental_det_btn_deleterental);
//            deleteBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    deleterental(film);
//                }
//            });
//
//            addBtn = (Button) findViewById(R.id.activity_rental_det_btn_newrental);
//            addBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        if (!et_name.getText().toString().equals("") && !et_countryCode.getText().toString().equals("") && !et_district.getText().toString().equals("") && Integer.parseInt(et_population.getText().toString()) > 0) {
//                            Film newFilm = new Film(et_name.getText().toString());
//                            postrental(newFilm);
//                        }
//                    } catch (Exception e){
//                        Log.e(TAG, e.toString());
//                    }
//                }
//            });
//
//            backBtn = (Button) findViewById(R.id.activity_rental_det_btn_back);
//            backBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(in);
//                }
//            });
//
//            Bundle extras = getIntent().getExtras();
//            film = (Film) extras.getSerializable("Film");
//            et_oName.setText(film.getName());
//
//
//        } else {
//            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(login);
//            finish();
//        }
//    }
//
//    private boolean tokenAvailable() {
//        boolean result = false;
//
//        Context context = getApplicationContext();
//        SharedPreferences sharedPref = context.getSharedPreferences(
//                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        String token = sharedPref.getString("token", "");
//        if (token != null && !token.equals("")) {
//            result = true;
//        }
//        return result;
//    }
//
//    private void editrental(Film film){
//        FilmRequest request = new FilmRequest(getApplicationContext());
//        request.handleEditrental(film);
//    }
//
//    private void deleterental(Film film){
//        FilmRequest request = new FilmRequest(getApplicationContext());
//        request.handleDeleterental(film);
//    }
//
//    private void postrental(Film film){
//        FilmRequest request = new FilmRequest(getApplicationContext());
//        request.handlePostrental(film);
//    }
}