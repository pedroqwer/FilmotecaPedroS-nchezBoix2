package com.example.proyecto;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivityy extends AppCompatActivity {

    Button button,buttonS,fin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abou_tactivityy);
        button=(Button) findViewById(R.id.boton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MostrarmensajePersonalizado("Enlace de github");
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
                MostrarmensajePersonalizado("Correo");
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

                finish();
                MostrarmensajePersonalizado("Has vuelto al principio");

            }
        });



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
