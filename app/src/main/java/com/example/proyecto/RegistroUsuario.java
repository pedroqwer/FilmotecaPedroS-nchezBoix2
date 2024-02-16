
package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroUsuario extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    Button botonalta;

    EditText UsuarioR,ContraseñaR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        sqLiteDatabase=openOrCreateDatabase("Mis_Usuarios", Context.MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Mis_Usuarios(Usuario VARCHAR , Contraseña VARCHAR);");

        UsuarioR=(EditText) findViewById(R.id.UsuarioRe);
        ContraseñaR=(EditText) findViewById(R.id.ContraseñaRE);

        botonalta=(Button) findViewById(R.id.IDAlta);
        botonalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistrarUsuario();
                finish();
            }


        });

    }
    private void RegistrarUsuario() {

        if(!EsisteUsuario(sqLiteDatabase,UsuarioR)){

            sqLiteDatabase.execSQL("INSERT INTO Mis_Usuarios VALUES('"+UsuarioR.getText().toString()+"','"+ContraseñaR.getText().toString()+"');");

            Toast.makeText(getApplicationContext(), "Insertado", Toast.LENGTH_LONG).show();

        }else {

            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_LONG).show();

        }

    }

    private boolean EsisteUsuario(SQLiteDatabase sqLiteDatabase, EditText usuarioR) {

        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM Mis_Usuarios WHERE Usuario = ?",new String[]{usuarioR.getText().toString()});
        boolean existe=cursor.getCount()>0;
        cursor.close();
        return  existe;
    }
}
