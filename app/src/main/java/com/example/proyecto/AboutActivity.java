package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {
    Button button,buttonS,fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abou_tactivityy);
        button=(Button) findViewById(R.id.boton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Enlace de github", Toast.LENGTH_SHORT).show();
                String url="https://github.com/pedroqwer/FilmotecaPedroS-nchezBoix2";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                startActivity(intent);
            }
        });
        buttonS=(Button) findViewById(R.id.boton2);
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"Soporte Filmoteca");
                intent.putExtra(Intent.EXTRA_TEXT,"Texto del correo de soporte");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"filmoteca@pmdm.es"});

                startActivity(intent);
            }
        });

        fin=(Button) findViewById(R.id.boton3);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AbouTActivityy.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Has vuelto al principio", Toast.LENGTH_LONG).show();
            }
        });

    }
}
