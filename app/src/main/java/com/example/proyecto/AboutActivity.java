package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    Button fin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //Boton 1
        Button button1 = findViewById(R.id.boton1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/pedroqwer/FilmotecaPedroSanchezBoix";

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                startActivity(intent);


            }
        });

        //Boton 2
        Button button2 = findViewById(R.id.boton2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setType("Text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Filmoteca");
                intent.putExtra(Intent.EXTRA_TEXT, "Texto del correo de soporte");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"filmoteca@pmdm.es"});

                startActivity(intent);

            }
        });
        //boton 3

        fin=(Button) findViewById(R.id.boton3);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AboutActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Has vuelto al principio",Toast.LENGTH_LONG).show();
            }
        });

    }
}