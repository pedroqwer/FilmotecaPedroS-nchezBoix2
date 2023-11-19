package com.example.proyecto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FilmListActivity extends ArrayAdapter<Film>  {
    private  int rr;
    private ArrayList<Film> films;
    public FilmListActivity(@NonNull Context context, int resource, @NonNull ArrayList<Film> objects) {
        super(context, resource, objects);
        rr=resource;
        films=objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View mi=inflater.inflate(rr,parent,false);

        TextView titulo=mi.findViewById(R.id.titulo);
        TextView director=mi.findViewById(R.id.director);
        ImageView foto=mi.findViewById(R.id.img);

        String tituliP=films.get(position).getTitle();
        titulo.setText(tituliP);

        String directorP=films.get(position).getDirector();
        director.setText(directorP);

        int fotoP=films.get(position).getImageResId();
        foto.setImageResource(fotoP);


        return  mi;
    }
}

