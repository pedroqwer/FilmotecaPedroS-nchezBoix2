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
    private  static final  int PermidoCamara=33;
    Button guardar;

    ImageView Sipermitido,Nopermitido,imageView;
    Button select,cap;
    Button cancelar,permiso;

    EditText tit,dir,commm,imb,any;
    ImageView imm;
    Spinner spinner,spinnerww;
    private Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_edit);



        Intent intent=getIntent();
        int posicion=intent.getIntExtra("Pelicula",0);


        film=FilmDataSource.films.get(posicion);

        tit=(EditText) findViewById(R.id.ediciontitulo);
        tit.setText(FilmDataSource.films.get(posicion).getTitle().toString());

        dir=(EditText) findViewById(R.id.ediciondirector);
        dir.setText(FilmDataSource.films.get(posicion).getDirector().toString());

        any =(EditText) findViewById(R.id.edicionAño);
        String anyo=String.valueOf(FilmDataSource.films.get(posicion).getYear());
        any.setText(anyo);

        imb=(EditText) findViewById(R.id.editarnimb);
        imb.setText(FilmDataSource.films.get(posicion).getImdbUrl().toString());

        commm=(EditText) findViewById(R.id.edicioncomentario);
        commm.setText(FilmDataSource.films.get(posicion).getComments().toString());

        spinner=findViewById(R.id.spinner);
        spinner.setSelection(FilmDataSource.films.get(posicion).getFormat());

        spinnerww=findViewById(R.id.spinner2);
        spinnerww.setSelection(FilmDataSource.films.get(posicion).getGenre());


        imm=findViewById(R.id.imageView);
        imm.setImageResource(film.getImageResId());


        guardar=(Button) findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilmDataSource.films.get(posicion).setTitle(tit.getText().toString());
                FilmDataSource.films.get(posicion).setDirector(dir.getText().toString());
                int year = Integer.parseInt(any.getText().toString());
                FilmDataSource.films.get(posicion).setYear(year);
                FilmDataSource.films.get(posicion).setImdbUrl(imb.getText().toString());
                FilmDataSource.films.get(posicion).setComments(commm.getText().toString());
                FilmDataSource.films.get(posicion).setGenre(spinnerww.getSelectedItemPosition());
                FilmDataSource.films.get(posicion).setFormat(spinner.getSelectedItemPosition());
                Toast.makeText(getApplicationContext(),"Cambios aplicados correctamente", Toast.LENGTH_LONG).show();

                setResult(RESULT_OK, null);
                finish();
            }


        });
        cancelar=(Button) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Los cambios han sido cancelados", Toast.LENGTH_LONG ).show();
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
                Toast.makeText(getApplicationContext(),"Funcionalidad no implementada", Toast.LENGTH_LONG ).show();
            }
        });
    }

    private void AbrirCamara() {
        int estado=ContextCompat.checkSelfPermission(FilmEditActivity.this,Manifest.permission.CAMERA);

        if(estado==PackageManager.PERMISSION_GRANTED){
            Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),"No tienes permiso para abrir la camara ",Toast.LENGTH_LONG).show();
        }
    }

    private void VerificarPermisoCamara() {
        int estado= ContextCompat.checkSelfPermission(FilmEditActivity.this, Manifest.permission.CAMERA);

        if(estado== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"Se ha verificado el permiso de camara concedido",Toast.LENGTH_LONG).show();

        }
        else {
            ActivityCompat.requestPermissions(FilmEditActivity.this,new String[]{Manifest.permission.CAMERA},PermidoCamara);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PermidoCamara:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Permiso concedido",Toast.LENGTH_LONG).show();
                   // permiso.setVisibility(Button.VISIBLE);

                }else {
                    Toast.makeText(getApplicationContext(),"Permiso no concedido",Toast.LENGTH_LONG).show();
               //     permiso.setVisibility(Button.GONE);

                }
            break;
        }
    }
}
