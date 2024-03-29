
package com.example.proyecto;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ListasPeliculas  extends AppCompatActivity implements AdapterView.OnItemClickListener {
        SQLiteDatabase db;
        Film nuevaPelicula = new Film();
        ListView lista;
        private final static String Canal="33";
        private static int DATA_FILM =1;
        FilmListActivity filmListActivity;
        Film film;
        FilmListActivity filmAdapter;

       ArrayList<Film> fil;

        ArrayList<Film> films;


        int iden;
        int contador=56;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_listas_peliculas);

                fil=new ArrayList<>();

                db = openOrCreateDatabase("MisPeliculas", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS peliculas(id Integer Primary Key AutoIncrement,Imagen Integer,Titulo Varchar,Director Varchar, Año Integer, Formato Integer, Genero Integer, URL Varchar, Comentarios Varchar);");


                leerBase();


                FilmDataSource.Initialize();
                lista=(ListView) findViewById(R.id.listaa);

                //--filmListActivity=new FilmListActivity(this,R.layout.mostrar,FilmDataSource.films);

                filmListActivity=new FilmListActivity(this,R.layout.mostrar,fil);

                lista.setAdapter(filmListActivity);
                lista.setOnItemClickListener(this);


                registerForContextMenu(lista);


        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ListasPeliculas.this,FilmDataActivity.class);
                intent.putExtra("Pelicula",i);
                startActivity(intent);

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.menu,menu);
                return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                getMenuInflater().inflate(R.menu.menucon,menu);
                super.onCreateContextMenu(menu, v, menuInfo);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.abo){
                        MostrarmensajePersonalizado("Has pulsado Acerca de");
                        Intent in =new Intent(this, AboutActivityy.class);
                        startActivity(in);
                }else if (id==R.id.opcion2){
                    iden=++contador;

                    film=new Film(iden,R.drawable.ic_launcher_background,"Titulo nuevo","Director nuevo",2002,1,2,"Nuevo enlace","Nuevo comentario");

                    /*
                        FilmDataSource.films.add(film=new Film(R.drawable.ic_launcher_background,"Titulo nuevo","Director nuevo",2002,1,2,"Nuevo enlace","Nuevo comentario"));
                        db.execSQL("INSERT INTO peliculas (Imagen, Titulo, Director, Año, Formato, Genero, URL, Comentarios) VALUES " + "(1, 'Nombre de la Película', 'Nombre del Director', 2022, 1, 1, 'http://ejemplo.com', 'Comentario sobre la película');");
                    */

                        Insert(film);
                        fil.add(film);
                        filmListActivity.notifyDataSetChanged();
                        NotificacionExpaldible(false,true);


                       /* MostrarmensajePersonalizado("Has pulsado añadir peliculas");
                        FilmDataSource.films.add(film=new Film(R.drawable.ic_launcher_background,"Titulo nuevo","Director nuevo",2002,1,2,"Nuevo enlace","Nuevo comentario"));
                        filmListActivity.notifyDataSetChanged();
                        NotificacionExpaldible(false,true);*/

                } else if (id==R.id.Idsalir) {
                    finish();
                } else if (id==R.id.info) {
                    MostrarmensajePersonalizado("Más información");
                    Intent intent=new Intent(this, MoeActivity.class);
                    startActivity(intent);
                } else if (id==R.id.actu) {

                    Intent intent=new Intent(this, ListasPeliculas.class);
                    startActivity(intent);
                    finish();

                }
                return super.onOptionsItemSelected(item);
        }

        private void Insert(Film film) {

                db.execSQL("INSERT INTO peliculas (Imagen, Titulo, Director, Año, Formato, Genero, URL, Comentarios) VALUES " +
                       "("+R.drawable.ic_launcher_background+", 'Nombre de la Película', 'Nombre del Director', 2022, 1, 1, 'http://ejemplo.com', 'Comentario sobre la película');");

                FilmDataSource.films.add(new Film(iden,R.drawable.ic_launcher_background,"Titulo nuevo","Director nuevo",2002,1,2,"Nuevo enlace","Nuevo comentario"));

        }


        @Override
        public boolean onContextItemSelected(@NonNull MenuItem item) {
                AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                Film f=(Film) lista.getItemAtPosition(info.position);

                int id=item.getItemId();

                if(id==R.id.eliminar){

                        AlertDialog.Builder alert=new AlertDialog.Builder(ListasPeliculas.this);
                        alert.setMessage("Desea borrar la película seleccionada?")
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                                BorradoDB(f);
                                                fil.remove(f);
                                                //Borra pelicula del datasource
                                                leerBase();
                                               // borrarPeliculasDeArchivo();
                                                MostrarmensajePersonalizado("Eliminar " + f.getTitle());
                                                FilmDataSource.films.remove(f);
                                                filmListActivity.notifyDataSetChanged();

                                        }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                Toast.makeText(getApplicationContext(),"Película no eliminada",Toast.LENGTH_LONG).show();
                                        }
                                });
                        AlertDialog aa=alert.create();
                        aa.setTitle("Borrar");
                        aa.show();

                }
                return super.onContextItemSelected(item);
        }

        private void BorradoDB(Film f) {

                Cursor c= db.rawQuery("SELECT * FROM peliculas WHERE Titulo = '"+f.getTitle()+"';",null);
                if(c.getCount()==0){
                        MostrarmensajePersonalizado("Pelicula no existe");
                }else {
                  db.execSQL("DELETE FROM peliculas WHERE Titulo ='"+f.getTitle()+"';");
                }
                c.close();

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == DATA_FILM){
                        if (resultCode == RESULT_OK){

                            fil.clear();
                            leerBase();
                            filmAdapter.notifyDataSetChanged();
                            //fil=new ArrayList<>();

                        }
                }
        }

        private void Update() {

                String tituloToUpdate = "El corredor del laberinto";
                String nuevoDirector = "Nuevo Director";
                int nuevoAnio = 2025;
                String nuevaURL = "https://www.imdb.com/title/tt1790864/?ref_=hm_tpks_tt_i_16_pd_tp1_pbr_ic";
                String nuevosComentarios = "Nuevos comentarios para la película";

                String updateQuery = "UPDATE peliculas SET Director = '" + nuevoDirector +
                        "', Año = " + nuevoAnio +
                        ", URL = '" + nuevaURL +
                        "', Comentarios = '" + nuevosComentarios +
                        "' WHERE Titulo = '" + tituloToUpdate + "';";

                db.execSQL(updateQuery);
        }

        private void NotificacionExpaldible (boolean b, boolean b1) {

                NotificationCompat.Builder builder=new  NotificationCompat.Builder(ListasPeliculas.this, Canal);
                builder.setSmallIcon(android.R.drawable.ic_dialog_info);

                if(b){

                        builder.setContentTitle("Notificacion");
                        builder.setContentText("Simple");
                }
                else {

                        NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("Notificacion de la pelicula");

                        String [] Lineas=new String[8];

                        Lineas[0]="Imagen de la película";
                        Lineas[1]="Titulo de la película";
                        Lineas[2]="Director de la película";
                        Lineas[3]="Año de estreno de la película";
                        Lineas[4]="Gnero de la película";
                        Lineas[5]="Formato de la película";
                        Lineas[6]="ImbUrl de la película";
                        Lineas[7]="Comentario de la película";



                        for (int a=0;a<Lineas.length;a++){
                                inboxStyle.addLine(Lineas[a]);
                        }

                        builder.setStyle(inboxStyle);

                }

                builder.setPriority(NotificationCompat.PRIORITY_MAX);

                if(b1){

                        int posicion = FilmDataSource.films.size()-1;
                        Intent intent =new Intent(ListasPeliculas.this, FilmEditActivity.class);
                        intent.putExtra("Pelicula", posicion);

                        PendingIntent pendingIntent= PendingIntent.getActivity(this,posicion,intent,PendingIntent.FLAG_IMMUTABLE);

                        builder.setContentIntent(pendingIntent);

                }


                NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel canal = new NotificationChannel(Canal, "Titulo del canal", NotificationManager.IMPORTANCE_DEFAULT);
                        notificationManager.createNotificationChannel(canal);
                }

                Notification notificacion = builder.build();
                notificationManager.notify(Integer.parseInt(Canal), notificacion);


        }
        private void leerBase(){
              Cursor c= db.rawQuery("SELECT * FROM peliculas",null);

              if(c.getCount()==0){
                      MostrarmensajePersonalizado("No tienes peliculas");
                      crearPelis();
                      leerBase();
              }
              else {
                   while (c.moveToNext()){
                     Film film= new Film();

                     film.setImageResId(c.getInt(1));
                     film.setTitle(c.getString(2));
                     film.setDirector(c.getString(3));
                     film.setYear(c.getInt(4));
                     film.setGenre(c.getInt(5));
                     film.setFormat(c.getInt(6));
                     film.setImdbUrl(c.getString(7));
                     film.setComments(c.getString(8));

                     fil.add(film);

                   }
              }
              c.close();
        }

        private void crearPelis() {
                Cursor cursor = db.rawQuery("SELECT * FROM peliculas", null);
                if(cursor.getCount() == 0) {
                        db.execSQL("INSERT INTO peliculas (Imagen, Titulo, Director, Año, Formato, Genero, URL, Comentarios) " +
                                "VALUES('" + R.drawable.endgamee + "','Endgame', 'Anthony y Joe Russo', 2019,0,2, 'https://www.imdb.com/title/tt4154796/?ref_=hm_tpks_tt_i_6_pd_tp1_pbr_ic', 'Los vengadores viajan en el tiempo para recuperar las gemas del infinito y poder derrotar a Thanos');");
                        db.execSQL("INSERT INTO peliculas (Imagen, Titulo, Director, Año, Formato, Genero, URL, Comentarios) " +
                                "VALUES('" + R.drawable.inter + "','Interstellar', 'Christopher Nolan', 2014, 3, 2, 'http://www.imdb.com/title/tt0816692', 'Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad.');");

                        db.execSQL("INSERT INTO peliculas (Imagen, Titulo, Director, Año, Formato, Genero, URL, Comentarios) " +
                                "VALUES('" + R.drawable.regresoalfuturo + "','Back to the future', 'Robert Zemeckis', 1985, 3, 2, 'http://www.imdb.com/title/tt0088763', 'Marty McFly, es enviado 30 años al pasado en un viaje en el tiempo en el DeLorean');");

                        db.execSQL("INSERT INTO peliculas (Imagen, Titulo, Director, Año, Formato, Genero, URL, Comentarios) " +
                                "VALUES('" + R.drawable.oppenheimer + "','Oppenheimer', 'Christopher Nolan', 2023, 2, 2, 'https://www.imdb.com/title/tt15398776/?ref_=hm_tpks_tt_i_3_pd_tp1_pbr_ic', 'Oppenheimer es una pelicula biográfica de suspenso británicoestadounidense de 2023, escrita y dirigida por Christopher Nolan. Basada en American Prometheus, una biografía de 2005 escrita por Kai Bird y Martin J.');");

                }
                cursor.close();
                //leerBase();
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
