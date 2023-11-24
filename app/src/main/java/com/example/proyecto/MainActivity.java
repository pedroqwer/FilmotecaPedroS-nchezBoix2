package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView lista;
    private     FilmListActivity filmListActivity;
    Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilmDataSource.Initialize();
        lista=(ListView) findViewById(R.id.listaa);
        filmListActivity=new FilmListActivity(this,R.layout.mostrar, FilmDataSource.films);
        lista.setAdapter(filmListActivity);
        lista.setOnItemClickListener(this);

        registerForContextMenu(lista);


    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Acción al clickar sobre un elemento determinado
        Intent intent = new Intent(MainActivity.this, FilmDataActivity.class);
        intent.putExtra("FILM_POSITION", position);
        Toast.makeText(getApplicationContext(),"Película selecionada : "+FilmDataSource.films.get(position).getTitle(), Toast.LENGTH_LONG).show();
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menucon,menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){

        int id=item.getItemId();

        if(id==R.id.opcion1){
            Toast.makeText(getApplicationContext(), "Has pulsado Acerca de ", Toast.LENGTH_LONG).show();
            Intent si=new Intent(this,AboutActivity.class);
            startActivity(si);

        }else if (id==R.id.opcion2){
            Toast.makeText(getApplicationContext(),"Has pulsado añadir peliculas",Toast.LENGTH_LONG).show();
            FilmDataSource.films.add(film=new Film(R.drawable.ic_launcher_background,"Titulo nuevo","Director nuevo",2002,1,2,"Nuevo enlace","Nuevo comentario"));
            filmListActivity.notifyDataSetChanged();
            //Intent si=new Intent();
            //startActivity(si);
        }

        return  super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Film f=(Film) lista.getItemAtPosition(info.position);

        int id=item.getItemId();

        if(id==R.id.eliminar){
            Toast.makeText(getApplicationContext(), "Eliminar " + f.getTitle(), Toast.LENGTH_LONG).show();
            FilmDataSource.films.remove(f);

            filmListActivity.notifyDataSetChanged();

        }

        return super.onContextItemSelected(item);
    }
}
