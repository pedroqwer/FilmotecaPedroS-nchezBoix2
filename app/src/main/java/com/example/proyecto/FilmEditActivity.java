package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilmEditActivity extends AppCompatActivity {

    Button guardar;

    Button select,cap;
    Button cancelar;
    private Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_edit);


        Intent intent=getIntent();
        int posicion=intent.getIntExtra("FILM_POSITION",0);


        film=FilmDataSource.films.get(posicion);

        EditText tit=findViewById(R.id.ediciontitulo);
        tit.setText(film.getTitle());

        EditText dir=findViewById(R.id.ediciondirector);
        dir.setText(film.getDirector());

        EditText commm=findViewById(R.id.edicioncomentario);
        commm.setText(film.getComments());

        EditText imb=findViewById(R.id.editarnimb);
        imb.setText(film.getImdbUrl());


        ImageView imm=findViewById(R.id.imageView);
        imm.setImageResource(film.getImageResId());

        EditText any =findViewById(R.id.edicionAño);
        any.setText(String.valueOf(film.getYear()));

      /*  TextView form=findViewById(R.id.F);
        form.setText(Formato(film.getFormat()));

        TextView gen=findViewById(R.id.G);
        gen.setText(Genero(film.getGenre()));*/

        guardar=(Button) findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Guardarcambios(
                        tit.getText().toString(),
                        dir.getText().toString(),
                        commm.getText().toString(),
                        any.getText().toString(),
                        imb.getText().toString()
                        );
                /*Intent intent1=new Intent(FilmEditActivity.this,FilmListActivity.class);
                startActivity(intent1);*/

                finish();

                Toast.makeText(getApplicationContext(),"Cambios aplicados correctamente", Toast.LENGTH_LONG).show();
            }

            private void Guardarcambios(String titulo, String director, String comentario, String anyo, String imb) {
                film.setTitle(titulo);
                film.setDirector(director);
                film.setComments(comentario);
                film.setYear(Integer.parseInt(anyo));
                film.setImdbUrl(imb);


            }
        });
        cancelar=(Button) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(FilmEditActivity.this,FilmListActivity.class);
                startActivity(intent1);
                Toast.makeText(getApplicationContext(),"Los cambios han sido cancelados", Toast.LENGTH_LONG ).show();
            }
        });

        select=(Button) findViewById(R.id.Captura_imagen);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Funcionalidad no implementada", Toast.LENGTH_LONG ).show();
            }
        });

        cap=(Button) findViewById(R.id.selectt);

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Funcionalidad no implementada", Toast.LENGTH_LONG ).show();
            }
        });
    }
}