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
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FilmEditActivity extends AppCompatActivity {
    private  static final  int PermidoCamara=33;
    private  static final  int PermidoAlmacenamiento=323;
    Bitmap bitmap;
    Button guardar;

    String rutaIMG;
    ImageView Sipermitido,Nopermitido,imageView;
    Button select,cap;
    Button cancelar,permiso;

    Bitmap imagenBitmap;
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
              //  verificarPermisoAlmacenamiento();

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
   /* private void verificarPermisoAlmacenamiento() {
        int estado= ContextCompat.checkSelfPermission(FilmEditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(estado== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"Se ha verificado el permiso de acceso al almacenamiento",Toast.LENGTH_LONG).show();

        }
        else {
            ActivityCompat.requestPermissions(FilmEditActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PermidoAlmacenamiento);

        }
    }*/

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
                    Toast.makeText(getApplicationContext(),"Permiso concedido camara",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Permiso no concedido camara",Toast.LENGTH_LONG).show();

                }
                break;

          /*  case PermidoAlmacenamiento:

                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Permiso concedido almacenamiento",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Permiso no concedido almacenamiento",Toast.LENGTH_LONG).show();

                }
                break;*/

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

   /* private void SaveImagen()  {

        OutputStream fos=null;

        File file = null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            ContentResolver resolver=getContentResolver();
            ContentValues values=new ContentValues();

            String filename=System.currentTimeMillis() +"img";

            values.put(MediaStore.Images.Media.DISPLAY_NAME,filename);
            values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "pictures/MiAplicación");
            values.put(MediaStore.Images.Media.IS_PENDING,1);

            Uri uri= MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri imgUri= resolver.insert(uri,values);

            try {
                fos=resolver.openOutputStream(imgUri);
            } catch (FileNotFoundException e) {
                e.getMessage();
            }

            values.clear();
            values.put(MediaStore.Images.Media.IS_PENDING,0);
            resolver.update(imgUri,values,null,null);

        }else {
            String imgDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

            String fileNAme=System.currentTimeMillis()+".jpg";

            file=new File(imgDir,fileNAme);
            try {
                fos=new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.getMessage();
            }
        }

        boolean save = bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

        if(save){
            Toast.makeText(getApplicationContext(),"foto guardada",Toast.LENGTH_LONG).show();

        }
        if(fos!=null){
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        if(file!=null){
            MediaScannerConnection.scanFile(this,new String[]{file.toString()},null,null);

        }

    }*/
}
