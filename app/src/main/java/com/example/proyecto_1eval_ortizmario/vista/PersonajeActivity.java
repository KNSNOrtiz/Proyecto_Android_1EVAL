package com.example.proyecto_1eval_ortizmario.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.controlador.HttpGetXIV;
import com.example.proyecto_1eval_ortizmario.controlador.Sesion;
import com.example.proyecto_1eval_ortizmario.modelo.Personaje;
import com.prathameshmore.toastylibrary.Toasty;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonajeActivity extends AppCompatActivity {


    private TextView tvNombre;
    private TextView tvFreeCompany;
    private TextView tvDCServer;
    private TextView tvClaseActual;
    private TextView tvNivel;

    private ImageView imgPortrait;
    private CircularProgressDrawable progressDrawable;

    private Toasty toasty = new Toasty(this);
    private Sesion sesion;  //  Mediante Sesion (SharedPreferences) voy a gestionar los parámetros de consulta.
    private String nombrePersonaje;
    private String servidorPersonaje;

    private Personaje personaje = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje);
        getSupportActionBar().setTitle("Visor de personajes");

        tvNombre = (TextView) findViewById(R.id.tvPersonajeNombre);
        tvFreeCompany = (TextView) findViewById(R.id.tvPersonajeFC);
        tvDCServer = (TextView) findViewById(R.id.tvPersonajeDCServer);
        tvClaseActual = (TextView) findViewById(R.id.tvPersonajeClase);
        tvNivel = (TextView) findViewById(R.id.tvPersonajeNivel);

        imgPortrait = (ImageView) findViewById(R.id.imgPersonajePortrait);
        imgPortrait.setClipToOutline(true); //  Permite que el borde se sobreponga al contenedor ImageView.

        //  Barra de progreso que cargará mientras Glide carga la imagen del personaje, de encontrarlo.
        progressDrawable = new CircularProgressDrawable(this);
        progressDrawable.setStrokeWidth(100f);  //  Tamaño de la barra como tal.
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(100f); //  Tamaño de la circunferencia.
        progressDrawable.setBackgroundColor(Color.CYAN);
        progressDrawable.start();


        sesion = new Sesion(this);
        // Reemplazo los espacios por un +. La API documenta poder tratar los espacios en blanco,
        //  pero por si acaso, los trato yo mismo.
        nombrePersonaje = sesion.getNombrePersonaje().replace(" ", "+");
        servidorPersonaje = sesion.getServidorPersonaje();

        String endpoint = "search?name="+nombrePersonaje+"&server="+servidorPersonaje;
        new getPersonajeId().execute("GET", endpoint);
    }

    //  En OnResume cargamos los nuevos parámetros de los ajustes para poder hacer una nueva búsqueda y
    //  lanzamos el hilo.
    @Override
    protected void onResume() {
        super.onResume();
        nombrePersonaje = sesion.getNombrePersonaje().replace(" ", "+");
        servidorPersonaje = sesion.getServidorPersonaje();
        String endpoint = "search?name="+nombrePersonaje+"&server="+servidorPersonaje;
        new getPersonajeId().execute("GET", endpoint);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.index_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAjustes:
                Intent intent = new Intent(PersonajeActivity.this, PreferenciasActivity.class);
                startActivity(intent);
        }
        return true;
    }

    private class getPersonajeId extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String personajeString = "";
            switch(strings[0]){
                case "GET":
                    personajeString = HttpGetXIV.getPersonaje(strings[1]);
                    break;
            }
            return personajeString;
        }

        @Override
        protected void onPostExecute(String personajeString) {
            try{
                if (personajeString != null){
                    JSONObject personajeBasico = new JSONObject(personajeString);
                    String idPersonaje = null;
                    idPersonaje = String.valueOf(personajeBasico.getJSONArray("Results").getJSONObject(0).getInt("ID"));
                    if (idPersonaje != null){
                        new getPersonaje().execute("GET", idPersonaje);
                    }
                }
                else{
                    throw new JSONException("Parámetros no encontrados");

                }
            } catch (JSONException ex){
                //  Reuso los elementos de la interfaz para mostrar que la búsqueda
                //  no se ha podido realizar con éxito.
                tvClaseActual.setText("Uy");
                tvNivel.setText("Vaya");
                tvNombre.setText("No encontrado");
                tvDCServer.setText("Comprueba los ajustes");
                tvFreeCompany.setText("Y que el personaje exista.");
                imgPortrait.setImageResource(R.mipmap.img_placeholder);
            }

        }
    }

    private class getPersonaje extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String personajeString = "";
            switch(strings[0]){
                case "GET":
                    personajeString = HttpGetXIV.getPersonaje(strings[1]);
                    break;
            }
            return personajeString;
        }

        @Override
        protected void onPostExecute(String personajeString) {
            try{
                if (personajeString != null){
                    JSONObject personajeJSON = new JSONObject(personajeString).getJSONObject("Character");

                    //  Declaración de los campos que voy a extraer del JSON.
                    String id;
                    String nombre;
                    String urlPortrait;
                    String datacenter;
                    String servidor;
                    String claseActual;
                    String nivelActual;
                    String freeCompany;

                    //  Obtención del contenido.
                    id = personajeJSON.getString("ID");
                    nombre = personajeJSON.getString("Name");
                    urlPortrait = personajeJSON.getString("Portrait");
                    datacenter = personajeJSON.getString("DC");
                    servidor = personajeJSON.getString("Server");
                    claseActual = personajeJSON.getJSONObject("ActiveClassJob").getJSONObject("UnlockedState").getString("Name");
                    nivelActual = String.valueOf(personajeJSON.getJSONObject("ActiveClassJob").getInt("Level"));
                    freeCompany = personajeJSON.getString("FreeCompanyName");

                    personaje = new Personaje(id,nombre,urlPortrait,datacenter,servidor,claseActual,nivelActual,freeCompany);

                    tvNombre.setText(personaje.getNombre());
                    tvFreeCompany.setText("FC: "+personaje.getFreeCompany());
                    tvDCServer.setText("Servidor: "+personaje.getDataCenter() + "@" + personaje.getServidor());
                    tvClaseActual.setText("Clase: "+personaje.getClaseActual());
                    tvNivel.setText("Lvl: "+personaje.getNivelActual());



                    Glide.with(PersonajeActivity.this)
                            .load(urlPortrait)
                            .placeholder(progressDrawable)
                            .error(R.mipmap.img_placeholder)
                            .into(imgPortrait);

                }
            } catch (JSONException ex){

            }

        }
    }
}