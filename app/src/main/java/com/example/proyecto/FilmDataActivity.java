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

    Button volver;

    Button editar;



    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_data);

       Intent intent=getIntent();
       int posicion=intent.getIntExtra("FILM_POSITION",0);

        film=FilmDataSource.films.get(posicion);

        TextView tit=findViewById(R.id.tituloo);
        tit.setText(film.getTitle());

        TextView dir=findViewById(R.id.directorrr);
        dir.setText(film.getDirector());

        TextView commm=findViewById(R.id.comen);
        commm.setText(film.getComments());

        Button bo=findViewById(R.id.botonenlace);
        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=film.getImdbUrl();

                Uri uri=Uri.parse(url);

                Intent intent1=new Intent(Intent.ACTION_VIEW,uri);
                if(intent1.resolveActivity(getPackageManager())!=null){
                    startActivity(intent1);
                }
            }
        });

        ImageView imm=findViewById(R.id.imaa);
        imm.setImageResource(film.getImageResId());

        TextView any =findViewById(R.id.anyoo);
        any.setText(String.valueOf(film.getYear()));

        TextView form=findViewById(R.id.F);
        form.setText(Formato(film.getFormat()));

        TextView gen=findViewById(R.id.G);
        gen.setText(Genero(film.getGenre()));





        //Botones

        volver=(Button)findViewById(R.id.BotonvolverList);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Has vuelto al inicio", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(FilmDataActivity.this,MainActivity.class);
                startActivity(i);
            }



        });


        editar=(Button) findViewById(R.id.edit);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Edición de película", Toast.LENGTH_SHORT).show();


                Intent intent=new Intent(FilmDataActivity.this, FilmEditActivity.class);
                intent.putExtra("FILM_POSITION", posicion);
                startActivity(intent);


            }

        });

    }

    private String Genero(int genre) {
        switch (genre){
            case Film.GENRE_ACTION:
                return "Accion";
            case Film.GENRE_COMEDY:
                return "Comedia";
            case Film.GENRE_DRAMA:
                return "Drama";
            case Film.GENRE_HORROR:
                return "Horror";
            case Film.GENRE_SCIFI:
                return "Scifi";
            default:
                return "desconocido";
        }
    }

    private String Formato(int format) {
      switch (format){
          case  Film.FORMAT_DVD:
              return "DVD";
          case  Film.FORMAT_BLURAY:
              return  "Bluray";
          case  Film.FORMAT_DIGITAL:
              return "Digital";
          default:
              return "desconocido";
      }

    }



}