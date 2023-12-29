package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilmDataActivity extends AppCompatActivity  {

  int posicion;
    Film film;
    TextView tit, dir, commm, any;
    Button bo, volver, editar;
    private static final int Edi = 1;
    String anyo, formatoGenero, formato;
    int gen, form;
    ImageView imgView;
    TextView txtComentario, txtFormato, txtGenero, txtNumAnyo, txtNomDirector, txtNomPelicula;
    Button btnWebIMDB, btnVolverMenu, btnEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_data);

        imgView = (ImageView) findViewById(R.id.imaa);
        txtNomPelicula = (TextView) findViewById(R.id.tituloo);
        txtNomDirector = (TextView) findViewById(R.id.directorrr);
        txtNumAnyo = (TextView) findViewById(R.id.anyoo);
        txtFormato = (TextView) findViewById(R.id.F);
        txtGenero=(TextView) findViewById(R.id.G);
        txtComentario = (TextView) findViewById(R.id.comen);
        txtComentario.setMovementMethod(new ScrollingMovementMethod());
        btnWebIMDB = (Button) findViewById(R.id.botonenlace);
        btnVolverMenu = (Button) findViewById(R.id.BotonvolverList);
        btnEditar = (Button) findViewById(R.id.edit);

        Intent intent = getIntent();
        posicion = intent.getIntExtra("Pelicula", 0);


        imgView.setImageResource(FilmDataSource.films.get(posicion).getImageResId());
        txtNomPelicula.setText(FilmDataSource.films.get(posicion).getTitle().toString());
        txtNomDirector.setText(FilmDataSource.films.get(posicion).getDirector().toString());

        anyo = String.valueOf(FilmDataSource.films.get(posicion).getYear());
        txtNumAnyo.setText(anyo);

        formatoGenero="";

        form =FilmDataSource.films.get(posicion).getFormat();

        if(form ==0){
            formato="DVD";
        } else if (form==1) {
            formato =" Bluray";
        } else if (form==2) {
            formato="Digital";
        }


        txtGenero.setText(formato);

        gen = FilmDataSource.films.get(posicion).getGenre();
        if (gen == 0) {
            formatoGenero = "Action";
        } else if (gen == 1) {
            formatoGenero = "Comedy";
        } else if (gen == 2) {
            formatoGenero = "Drama";
        } else if (gen == 3) {
            formatoGenero = "Scifi";
        } else if (gen == 4) {
            formatoGenero = "Horror";
        }


        txtFormato.setText(formatoGenero);

        txtComentario.setText(FilmDataSource.films.get(posicion).getComments());

        btnWebIMDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Redirigiendote a la página de IMDB de la película...", Toast.LENGTH_SHORT).show();
                Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse(FilmDataSource.films.get(posicion).getImdbUrl()));
                startActivity(intentWeb);
            }
        });


        //Botones

        volver = (Button) findViewById(R.id.BotonvolverList);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Has vuelto al inicio", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }


        });


        editar = (Button) findViewById(R.id.edit);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Edición de película", Toast.LENGTH_SHORT).show();

                Intent intentFilmEditActivity = new Intent(FilmDataActivity.this, FilmEditActivity.class);
                intentFilmEditActivity.putExtra("Pelicula", posicion);
                startActivityForResult(intentFilmEditActivity, Edi);

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Edi) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Cambios aplicados correctamente", Toast.LENGTH_SHORT).show();

                txtNomPelicula.setText(FilmDataSource.films.get(posicion).getTitle().toString());
                txtNomDirector.setText(FilmDataSource.films.get(posicion).getDirector().toString());
                anyo = String.valueOf(FilmDataSource.films.get(posicion).getYear());
                txtNumAnyo.setText(anyo);
                btnWebIMDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Redirigiendote a la página de IMDB de la película...", Toast.LENGTH_SHORT).show();
                        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(FilmDataSource.films.get(posicion).getImdbUrl()));
                        startActivity(intentWeb);
                    }
                });
                txtComentario.setText(FilmDataSource.films.get(posicion).getComments().toString());

                formato = "";
                form = FilmDataSource.films.get(posicion).getFormat();
                if (form == 0) {
                    formato = "DVD ";
                } else if (form == 1) {
                    formato = "Bluray ";
                } else if (form == 2) {
                    formato = "Digital ";
                }


                gen = FilmDataSource.films.get(posicion).getGenre();
                if (gen == 0) {
                    formatoGenero = "Action";
                } else if (gen == 1) {
                    formatoGenero = "Comedy";
                } else if (gen == 2) {
                    formatoGenero = "Drama";
                } else if (gen == 3) {
                    formatoGenero = "Scifi";
                } else if (gen == 4) {
                    formatoGenero = "Horror";
                }
                txtFormato.setText(formatoGenero);

                txtGenero.setText(formato);


            } else {
                Toast.makeText(getApplicationContext(), "Los cambios han sido cancelados", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
