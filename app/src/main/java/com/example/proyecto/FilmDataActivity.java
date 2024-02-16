package com.example.proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectorecuperacionpedro.Film;
import com.example.proyectorecuperacionpedro.FilmDataSource;
import com.example.proyectorecuperacionpedro.FilmEditActivity;
import com.example.proyectorecuperacionpedro.R;

import java.util.ArrayList;

public class FilmDataActivity extends AppCompatActivity  {

    int posicion;
    Film film;
    TextView tit, dir, commm, any;
    Button bo, volver, editar;
    private static final int Edi = 1;
    String anyo, formatoGenero, formato;
    int gen, form;
    SQLiteDatabase db;
    ImageView imgView;
    TextView txtComentario, txtFormato, txtGenero, txtNumAnyo, txtNomDirector, txtNomPelicula;
    Button btnWebIMDB, btnVolverMenu, btnEditar;
    ArrayList<Film> fil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_data);

        //Codigo antes de la modificación
        /*
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

*/
        db = openOrCreateDatabase("MisPeliculas", MODE_PRIVATE, null);

        Intent intentS = getIntent();
        posicion = intentS.getIntExtra("Pelicula", 0) ;

        film=leerBase();


        obtenerPeliculaDesdeBaseDeDatos();
        crearDatos(obtenerPeliculaDesdeBaseDeDatos());

        //Codigo antes de la modificación
        /*
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
                MostrarmensajePersonalizado("Redirigiendote a la página de IMDB de la película...");

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

                MostrarmensajePersonalizado("Has vuelto al inicio");

                Intent i = new Intent();
                setResult(RESULT_OK, intentS);
                finish();
            }


        });


        editar = (Button) findViewById(R.id.edit);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MostrarmensajePersonalizado("Edición de película");

                Intent intentFilmEditActivity = new Intent(FilmDataActivity.this, FilmEditActivity.class);
                intentFilmEditActivity.putExtra("Pelicula", posicion);
                startActivityForResult(intentFilmEditActivity, Edi);

            }

        });
*/
    }

    private Film leerBase() {
        ArrayList<Film> listado = new ArrayList<Film>();

        Cursor c = db.rawQuery("SELECT * FROM peliculas", null);

        while(c.moveToNext()){
            int id = c.getInt(0);
            int imagen = c.getInt(1);
            String titulo = c.getString(2);
            String director = c.getString(3);
            int año = c.getInt(4);
            int formato = c.getInt(5);
            int genero = c.getInt(6);
            String url = c.getString(7);
            String comentarios = c.getString(8);

            listado.add(new Film(id,imagen, titulo, director, año, formato, genero, url, comentarios));
        }
        c.close();
        return listado.get(posicion);
    }

    private void crearDatos(Object f) {

            if (f != null) {
                imgView = (ImageView) findViewById(R.id.imaa);
                txtNomPelicula = (TextView) findViewById(R.id.tituloo);
                txtNomDirector = (TextView) findViewById(R.id.directorrr);
                txtNumAnyo = (TextView) findViewById(R.id.anyoo);
                txtFormato = (TextView) findViewById(R.id.F);
                txtGenero = (TextView) findViewById(R.id.G);
                txtComentario = (TextView) findViewById(R.id.comen);
                txtComentario.setMovementMethod(new ScrollingMovementMethod());
                btnWebIMDB = (Button) findViewById(R.id.botonenlace);
                btnVolverMenu = (Button) findViewById(R.id.BotonvolverList);
                btnEditar = (Button) findViewById(R.id.edit);

                imgView.setImageResource(film.getImageResId());
                txtNomPelicula.setText(film.getTitle());
                txtNomDirector.setText(film.getDirector());

                anyo = String.valueOf(film.getYear());
                txtNumAnyo.setText(anyo);

                formatoGenero = "";

                form = film.getFormat();

                if (form == 0) {
                    formato = "DVD";
                } else if (form == 1) {
                    formato = " Bluray";
                } else if (form == 2) {
                    formato = "Digital";
                }


                txtGenero.setText(formato);

                gen = film.getGenre();
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

                txtComentario.setText(film.getComments());

                btnWebIMDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MostrarmensajePersonalizado("Redirigiendote a la página de IMDB de la película...");

                        Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                        intentWeb.setData(Uri.parse(film.getImdbUrl()));
                        startActivity(intentWeb);
                    }
                });


                //Botones

                volver = (Button) findViewById(R.id.BotonvolverList);

                volver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MostrarmensajePersonalizado("Has vuelto al inicio");

                        Intent i = new Intent();
                        setResult(RESULT_OK, i);
                        finish();
                    }


                });


                editar = (Button) findViewById(R.id.edit);

                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        MostrarmensajePersonalizado("Edición de película");

                        Intent intentFilmEditActivity = new Intent(FilmDataActivity.this, FilmEditActivity.class);
                        intentFilmEditActivity.putExtra("Pelicula", posicion);
                        startActivityForResult(intentFilmEditActivity, Edi);

                    }

                });


            }
    }

    private Film obtenerPeliculaDesdeBaseDeDatos() {
        ArrayList<Film> listado = new ArrayList<Film>();

        Cursor c = db.rawQuery("SELECT * FROM peliculas", null);

        while(c.moveToNext()){
            int id = c.getInt(0);
            int imagen = c.getInt(1);
            String titulo = c.getString(2);
            String director = c.getString(3);
            int año = c.getInt(4);
            int formato = c.getInt(5);
            int genero = c.getInt(6);
            String url = c.getString(7);
            String comentarios = c.getString(8);

            listado.add(new Film(id,imagen, titulo, director, año, formato, genero, url, comentarios));
        }
        c.close();
        return listado.get(posicion);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Edi) {
            if (resultCode == RESULT_OK) {
                MostrarmensajePersonalizado("Cambios aplicados correctamente");

                
                txtNomPelicula.setText(film.getTitle());
                txtNomDirector.setText(film.getDirector());
                anyo = String.valueOf(film.getYear());
                txtNumAnyo.setText(anyo);
                btnWebIMDB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MostrarmensajePersonalizado("Redirigiendote a la página de IMDB de la película...");

                        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(film.getImdbUrl()));
                        startActivity(intentWeb);
                    }
                });
                txtComentario.setText(film.getComments());

                formato = "";
                form = film.getFormat();
                if (form == 0) {
                    formato = "DVD ";
                } else if (form == 1) {
                    formato = "Bluray ";
                } else if (form == 2) {
                    formato = "Digital ";
                }


                gen = film.getGenre();
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

                leerBase();
            } else {
                MostrarmensajePersonalizado("Los cambios han sido cancelados");
            }
        }
    }
    private void MostrarmensajePersonalizado(String cadena) {
        Toast toast = new Toast(this);
        View toastL = getLayoutInflater().inflate(R.layout.mensaje, null);
        toast.setView(toastL);
        TextView textView = (TextView) toastL.findViewById(R.id.toastMessage);
        textView.setText(cadena);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
