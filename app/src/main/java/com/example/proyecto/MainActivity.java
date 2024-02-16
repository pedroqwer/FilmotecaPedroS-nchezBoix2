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
import android.widget.TextView;
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

        Usuario.setText("");
        Contraseña.setText("");

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

        MostrarmensajePersonalizado("Usuario -> "+usuario+ " Contraseña -> " +contraseña);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Mis_Usuarios WHERE Usuario= '" + usuario + "' AND  Contraseña ='" + contraseña + "'", null);

        if (cursor.getCount()==0) {

            MostrarmensajePersonalizado("No existe");

            cursor.close();

        } else {

            MostrarmensajePersonalizado("Estas dentro de la base dde datos");
            cursor.close();
            Intent intent = new Intent(this, ListasPeliculas.class);
            startActivity(intent);
        }
    }

    private void RegistrarUsuario() {
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
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
