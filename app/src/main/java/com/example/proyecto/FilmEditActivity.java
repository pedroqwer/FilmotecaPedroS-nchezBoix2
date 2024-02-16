package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class FilmEditActivity extends AppCompatActivity {
    private  static final  int PermidoCamara=33;
    private  static final  int PermidoAlmacenamiento=323;
    Bitmap bitmap;
    Button guardar;

    String rutaIMG;
    ImageView Sipermitido,Nopermitido,imageView;
    Button select,cap;
    Button cancelar,permiso;

    TextView anio;
    Bitmap imagenBitmap;
    EditText tit,dir,commm,imb,any;
    ImageView imm;
    Spinner spinner,spinnerww;
    private Film film;
    SQLiteDatabase db;

    int posicion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_edit);


        db = openOrCreateDatabase("MisPeliculas", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS peliculas(Imagen INTEGER, Titulo VARCHAR, Director VARCHAR, Año INTEGER, Genero INTEGER, Formato INTEGER, URL VARCHAR, Comentarios VARCHAR);");


        Intent intent=getIntent();
        posicion=intent.getIntExtra("Pelicula",0);


        film=LeerBatos();
        //FilmDataSource.films.get(posicion);

        guardar=(Button) findViewById(R.id.guardar);
        cancelar=(Button) findViewById(R.id.cancelar);

        crearDatos(obtenerPeliculaDesdeBaseDeDatos());


        anio=(TextView) findViewById(R.id.edicionAño);

        guardar.setOnClickListener(new View.OnClickListener() {
            String añoStr = anio.getText().toString();
            int año = Integer.parseInt(añoStr);

            @Override
            public void onClick(View v) {

                String updateQuery = "UPDATE peliculas SET " +
                        "Imagen = " + film.getImageResId() + ", " +
                        "Titulo = '" + ((TextView) findViewById(R.id.ediciontitulo)).getText().toString() + "', " +
                        "Director = '" + ((TextView) findViewById(R.id.ediciondirector)).getText().toString() + "', " +
                        "Año = " + Integer.parseInt(añoStr) + ", " +
                        //"Genero = " + spinnerww.getSelectedItemPosition() + ", " +
                        //"Formato = " + spinner.getSelectedItemPosition() + ", " +
                        "URL = '" + ((TextView) findViewById(R.id.editarnimb)).getText().toString() + "', " +
                        "Comentarios = '" + ((TextView) findViewById(R.id.edicioncomentario)).getText().toString() + "' " +
                        "WHERE id = '" + film.getId() + "'";

                try (SQLiteDatabase db = openOrCreateDatabase("MisPeliculas", MODE_PRIVATE, null)) {
                    db.execSQL(updateQuery);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                MostrarmensajePersonalizado("Película actualizada");

                setResult(RESULT_OK, null);
                finish();


               /* FilmDataSource.films.get(posicion).setTitle(tit.getText().toString());
                FilmDataSource.films.get(posicion).setDirector(dir.getText().toString());
                int year = Integer.parseInt(any.getText().toString());
                FilmDataSource.films.get(posicion).setYear(year);
                FilmDataSource.films.get(posicion).setImdbUrl(imb.getText().toString());
                FilmDataSource.films.get(posicion).setComments(commm.getText().toString());
                FilmDataSource.films.get(posicion).setGenre(spinnerww.getSelectedItemPosition());
                FilmDataSource.films.get(posicion).setFormat(spinner.getSelectedItemPosition());
                MostrarmensajePersonalizado("Cambios aplicados correctamente");


                setResult(RESULT_OK, null);
                finish();*/
            }


        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarmensajePersonalizado("Los cambios han sido cancelados");
                Intent intentCancel = new Intent();
                setResult(RESULT_CANCELED, intentCancel);
                finish();
            }
        });

        permiso=(Button) findViewById(R.id.verificar);
        permiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerificarPermisoCamara();

            }
        });

        select=(Button) findViewById(R.id.Captura_imagen);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbrirCamara();

            }
        });

        cap=(Button) findViewById(R.id.selectt);

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarmensajePersonalizado("Funcionalidad no implementada");

            }
        });
    }

    private Film LeerBatos() {

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

        if(f!= null){
            tit=(EditText) findViewById(R.id.ediciontitulo);
            tit.setText(film.getTitle());

            dir=(EditText) findViewById(R.id.ediciondirector);
            dir.setText(film.getDirector());

            any =(EditText) findViewById(R.id.edicionAño);
            String anyo=String.valueOf(film.getYear());
            any.setText(anyo);

            imb=(EditText) findViewById(R.id.editarnimb);
            imb.setText(film.getImdbUrl());

            commm=(EditText) findViewById(R.id.edicioncomentario);
            commm.setText(film.getComments());

           /*
            spinner=findViewById(R.id.spinner);
            spinner.setSelection(film.getFormat());

            spinnerww=findViewById(R.id.spinner2);
            spinnerww.setSelection(film.getGenre());
            */

            imm=findViewById(R.id.imageView);
            imm.setImageResource(film.getImageResId());

        }
    }

    private Object obtenerPeliculaDesdeBaseDeDatos() {
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


    private void AbrirCamara() {
        int estado=ContextCompat.checkSelfPermission(FilmEditActivity.this,Manifest.permission.CAMERA);

        if(estado==PackageManager.PERMISSION_GRANTED){
            Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);

        }else {
            MostrarmensajePersonalizado("No tienes permiso para abrir la camara");

        }
    }


    private void VerificarPermisoCamara() {

        int estado=ContextCompat.checkSelfPermission(FilmEditActivity.this,Manifest.permission.CAMERA);

        if(estado==PackageManager.PERMISSION_GRANTED){

            MostrarmensajePersonalizado("Se ha verificado el permiso de camara");

        }else {
            ActivityCompat.requestPermissions(FilmEditActivity.this,new String[]{Manifest.permission.CAMERA},PermidoCamara);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PermidoCamara:
                if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    MostrarmensajePersonalizado("Permiso concedido camara");

                }else {
                    MostrarmensajePersonalizado("Permiso concedido no camara");

                }
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==PermidoCamara){
            if(resultCode == Activity.RESULT_OK && data!=null){
                bitmap =(Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
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
