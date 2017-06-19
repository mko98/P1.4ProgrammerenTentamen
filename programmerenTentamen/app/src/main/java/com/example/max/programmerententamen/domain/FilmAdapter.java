package com.example.max.programmerententamen.domain;

/**
 * Created by Maikel on 1-6-2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.max.programmerententamen.R;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {
    public FilmAdapter(Context context, ArrayList<Film> meldingen){
        super(context, 0, meldingen);
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        Film film = getItem(position);

        if (convertview == null){
            convertview = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_row, parent, false);
        }

        TextView name = (TextView) convertview.findViewById(R.id.listViewRow);
        name.setText(film.getName());

        return convertview;


    }
}