package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;

    Button Ingresar, Registrar;

    EditText Usuario, Contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDatabase = openOrCreateDatabase("Mis_Usuarios", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Mis_Usuarios(Usuario VARCHAR , Contraseña VARCHAR);");

        Usuario = (EditText) findViewById(R.id.IDUsuario);
        Contraseña = (EditText) findViewById(R.id.IDContraseña);

        Ingresar = (Button) findViewById(R.id.IDIngresar);
        Ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario, contraseña;

                usuario = Usuario.getText().toString();
                contraseña = Contraseña.getText().toString();
                IngresarUsuario(usuario, contraseña);
            }
        });

        Registrar = (Button) findViewById(R.id.IDRegistrar);
        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario();
            }
        });
    }

    private void IngresarUsuario(String usuario, String contraseña) {

        Toast.makeText(getApplicationContext(),"Usuario -> "+usuario+ " Contraseña -> " +contraseña,Toast.LENGTH_LONG).show();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Mis_Usuarios WHERE Usuario= '" + usuario + "' AND  Contraseña ='" + contraseña + "'", null);

        if (cursor.getCount()>0) {

            Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();
            cursor.close();

        } else {

            Toast.makeText(this, "dentro", Toast.LENGTH_SHORT).show();
            cursor.close();
            Intent intent = new Intent(this, ListasPeliculas.class);
            intent.putExtra("Nombre", usuario);
            startActivity(intent);
        }
    }

    private void RegistrarUsuario() {
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }
}
