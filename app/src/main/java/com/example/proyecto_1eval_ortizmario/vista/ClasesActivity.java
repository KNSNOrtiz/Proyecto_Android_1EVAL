package com.example.proyecto_1eval_ortizmario.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.controlador.HttpGetXIV;
import com.example.proyecto_1eval_ortizmario.controlador.RecyclerAdapter_Clases;
import com.example.proyecto_1eval_ortizmario.modelo.ClaseJuego;
import com.prathameshmore.toastylibrary.Toasty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClasesActivity extends AppCompatActivity {


    private ArrayList<ClaseJuego> listaClases = new ArrayList<>();
    private RecyclerView rvClases;
    private RecyclerAdapter_Clases adapter;
    private int claseSeleccionada = 0;
    private Toasty toasty = new Toasty(this);
    androidx.appcompat.view.ActionMode.Callback actionMenuCallback;

    private int numResultados = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_clases);

        rvClases = (RecyclerView) findViewById(R.id.rvClases);
        adapter = new RecyclerAdapter_Clases(listaClases);
        LinearLayoutManager managerLayout = new LinearLayoutManager(this);
        rvClases.setLayoutManager(managerLayout);

        /*  La clase GestureDetector cumple la función de detectar qué tipo de movimiento estamos haciendo en la pantalla táctil.
            En este caso, solamente queremos detectar cuándo se ha dado un solo toque en el elemento del RecyclerView para entrar a los detalles,
            por lo que en el constructor le pasamos una instancia del listener propio de su clase (SimpleOnGestureListener) y sobreescribimos
            el método "OnSingleTapUp".

         */
        final GestureDetector mGestureDetector = new GestureDetector(ClasesActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View item = rvClases.findChildViewUnder(e.getX(), e.getY());
                claseSeleccionada = rvClases.getChildAdapterPosition(item);
                startSupportActionMode(actionMenuCallback);
            }
        });


        /*
            En el listener del RecyclerView comprobamos que el elemento de la lista que estamos tocando no es nulo, y que el MotionEvent (parámetro que indica
            el tipo de movimiento en la pantalla) es de un solo toque.
         */
        rvClases.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                try{
                    //  Escogemos el item mediante las coordenadas de la pantalla.
                    View item = rv.findChildViewUnder(e.getX(), e.getY());
                    if (item !=  null && mGestureDetector.onTouchEvent(e)){
                        Intent intent = new Intent(ClasesActivity.this, ClasesDetalleActivity.class);
                        //  Le pasamos al Intent de los detalles el ID del objeto, para posteriormente hacer una petición nueva a la API.
                        intent.putExtra("id", listaClases.get(rvClases.getChildAdapterPosition(item)).getId()); //  Pasamos el ID de la clase que hemos pulsado para crear la vista "Detalle".
                        startActivity(intent);
                        return true;
                    }
                    //toasty.infoToasty(ClasesActivity.this, "Evento activado desde " + listaClases.get(rvClases.getChildAdapterPosition(item)).getId(), Toasty.LENGTH_SHORT, Toasty.BOTTOM);

                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;

            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        rvClases.setAdapter(adapter);

        actionMenuCallback = new androidx.appcompat.view.ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.clases_menu, menu);
                mode.setTitle(listaClases.get(claseSeleccionada).getNombre());
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int itemId = item.getItemId();
                final FlatDialog flatDialog = new FlatDialog(ClasesActivity.this);
                switch(itemId){
                    case R.id.itemEliminar:
                        flatDialog.setTitle("Eliminación de registro")
                            .setSubtitle("Se va a eliminar " + listaClases.get(claseSeleccionada).getNombre() + " ¿Seguro/a?")
                            .setFirstButtonText("Aceptar")
                            .setSecondButtonText("Cancelar")
                                .withFirstButtonListner(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        borrarClase(claseSeleccionada);
                                        mode.finish();
                                        flatDialog.dismiss();
                                    }
                                })
                                .withSecondButtonListner(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        flatDialog.dismiss();
                                    }
                                })
                                .show();
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        };


        new getAllClases().execute("GET","");


    }

    public void borrarClase(int clas){
        int posicion = clas;
        listaClases.remove(posicion);
        adapter.notifyItemRemoved(posicion);
        adapter.notifyItemRangeChanged(posicion, listaClases.size());
    }



    private class getClases extends AsyncTask<String, Void, String>{

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

                    //  EXTRAEMOS TODA LA INFORMACIÓN DE LA CLASE (DEL JUEGO) DESDE EL INICIO.
                    String id = String.valueOf(claseJSON.getInt("ID"));
                    String nombre = claseJSON.getString("NameEnglish");
                    String abreviacion = claseJSON.getString("Abbreviation");
                    String urlIcono = claseJSON.getString("Icon");
                    //  Algunas propiedades están dentro de otros JSONObject anidados, así que es necesario declararlos.
                    JSONObject padreTipo = claseJSON.getJSONObject("ClassJobCategory");
                    JSONObject padreCiudad;
                    try{
                        padreCiudad = claseJSON.getJSONObject("StartingTown");
                    } catch(JSONException ex){
                        padreCiudad = null;
                    }

                    String tipo = padreTipo.getString("Name");
                    String  ciudadInicio = "";
                    String urlCiudad= "";
                    if (padreCiudad != null){
                        ciudadInicio = padreCiudad.getString("Name");
                        urlCiudad = padreCiudad.getString("Icon");
                    }

                    int nivelInicial = claseJSON.getInt("StartingLevel");




                    clase = new ClaseJuego(id, nombre, abreviacion, urlIcono, tipo, ciudadInicio, urlCiudad, nivelInicial);
                    listaClases.add(clase);
                    adapter.notifyDataSetChanged();

                }

            } catch(JSONException ex){
                toasty.dangerToasty(ClasesActivity.this, "No se ha podido crear el objeto Clase.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            }
        }
    }
    private class getAllClases extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            String raizClases = "";
            switch (strings[0]){
                case "GET":
                    raizClases = HttpGetXIV.getClases(strings[1]);
                    break;
            }
            return raizClases;
        }

        @Override
        protected void onPostExecute(String raizClases) {
            try{
                if (raizClases != null){
                    JSONObject raiz = new JSONObject(raizClases);
                    JSONObject paginacion = raiz.getJSONObject("Pagination");
                    numResultados = paginacion.getInt("Results");
                    for (int i = 1; i < numResultados; i++) {
                        new getClases().execute("GET", String.valueOf(i));
                    }
                }
            } catch (JSONException ex){

            }

        }
    }
}