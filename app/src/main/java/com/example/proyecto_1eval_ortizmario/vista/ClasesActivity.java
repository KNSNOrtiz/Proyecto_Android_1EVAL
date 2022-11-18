package com.example.proyecto_1eval_ortizmario.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.controlador.HttpGetXIV;
import com.example.proyecto_1eval_ortizmario.controlador.RecyclerAdapter_Clases;
import com.example.proyecto_1eval_ortizmario.modelo.ClaseJuego;
import com.prathameshmore.toastylibrary.Toasty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClasesActivity extends AppCompatActivity {


    private ArrayList<ClaseJuego> listaClases = new ArrayList<>();
    private RecyclerView rvClases;
    private RecyclerAdapter_Clases adapter;
    private Toasty toasty = new Toasty(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_clases);


        //ToDo AÑADIR CAMPOS [ICON,NAMEENGLISH,ABBREVIATION]
        //ToDo CREAR MODELO DE LAS CLASES DEL JUEGO, LUEGO CREAR LA LISTA EN BASE A LO QUE PILLE EL JSON.
        rvClases = (RecyclerView) findViewById(R.id.rvClases);
        adapter = new RecyclerAdapter_Clases(listaClases);
        rvClases.setAdapter(adapter);
        LinearLayoutManager managerLayout = new LinearLayoutManager(this);
        rvClases.setLayoutManager(managerLayout);

        new consultarAPI().execute("GET","1");




    }

    private class consultarAPI extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {

            String claseString = null;
            switch (strings[0]){
                case "GET":
                    claseString = HttpGetXIV.getClases(strings[1]);
                    break;
            }
            return claseString;

        }

        @Override
        protected void onPostExecute(String claseString) {
            ClaseJuego clase = null;
            try{
                if (claseString != null) {
                    JSONObject claseJSON = new JSONObject(claseString);

                    String nombre = claseJSON.getString("NameEnglish");
                    String abreviacion = claseJSON.getString("Abbreviation");
                    String urlIcono = claseJSON.getString("Icon");

                    clase = new ClaseJuego(nombre, abreviacion, urlIcono);
                    listaClases.add(clase);
                    adapter.notifyDataSetChanged();

                }

            } catch(JSONException ex){
                toasty.dangerToasty(ClasesActivity.this, "No se ha podido crear el objeto Clase.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            }

        }
    }
}