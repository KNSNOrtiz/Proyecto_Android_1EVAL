package com.example.proyecto_1eval_ortizmario.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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
    //  Este índice es importante para abrir el ActionBar correspondiente.
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
            En este caso, solamente queremos detectar cuándo se ha mantenido pulsado en el elemento del RecyclerView para entrar a los detalles,
            por lo que en el constructor le pasamos una instancia del listener propio de su clase (SimpleOnGestureListener) y sobreescribimos
            el método "OnSingleTapUp" para detectar toques simples (es obligatorio) y "onLongPress" para los sostenidos.

         */
        final GestureDetector mGestureDetector = new GestureDetector(ClasesActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                /*
                Abrimos el menú en base al item de la lista que hemos seleccionado, detectado mediante
                coordendas en la pantalla.
                 */
                View item = rvClases.findChildViewUnder(e.getX(), e.getY());
                //  Devuelve la posición del item seleccionado.
                claseSeleccionada = rvClases.getChildAdapterPosition(item);
                //  Llamada al actionBar
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

        //  Construcción del ActionBar
        actionMenuCallback = new androidx.appcompat.view.ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                //  Cargamos el menú creado por nosotros desde XML.
                mode.getMenuInflater().inflate(R.menu.clases_menu, menu);
                try{
                    //  Para saber qué clase estamos manipulando uso la variable descrita más arriba.
                    mode.setTitle(listaClases.get(claseSeleccionada).getNombre());
                } catch (Exception ex){
                    //  Puede ser que lance una excepción si no termina de cargar, así que queda controlado
                    //  mediante un simple mensaje de error.
                    toasty.darkToasty(ClasesActivity.this, "La lista no ha terminado de cargar. Espera antes de entrar al detalle.", Toasty.LENGTH_LONG, Toasty.BOTTOM);
                    mode.finish();
                }

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int itemId = item.getItemId();
                /*
                FlatDialog es una librería de AndroidArsenal que permite emplear de forma muy sencilla
                los AlertDialog, permitiéndonos en este caso pedir confirmación por parte del usuario antes
                de eliminar algún elemento.
                 */
                final FlatDialog flatDialog = new FlatDialog(ClasesActivity.this);
                switch(itemId){
                    case R.id.itemEliminar:
                        //  CONFIGURACIÓN DEL FLATDIALOG
                        flatDialog.setTitle("Eliminación de registro")
                            .setSubtitle("Se va a eliminar " + listaClases.get(claseSeleccionada).getNombre() + " ¿Seguro/a?")
                            .setBackgroundColor(Color.parseColor("#2196F3"))
                            .setFirstButtonColor(Color.parseColor("#3F51B5"))
                            .setFirstButtonTextColor(Color.parseColor("#FBD30B"))
                            .setFirstButtonText("Aceptar")
                            .setSecondButtonColor(Color.parseColor("#000000"))
                            .setSecondButtonTextColor(Color.parseColor("#FFFFFF"))
                            .setSecondButtonText("Cancelar")
                                .withFirstButtonListner(new View.OnClickListener() {    //  Listener del primer botón
                                    @Override
                                    public void onClick(View view) {
                                        borrarClase(claseSeleccionada);
                                        mode.finish();
                                        flatDialog.dismiss();
                                    }
                                })
                                .withSecondButtonListner(new View.OnClickListener() {   //  Listener del segundo botón
                                    @Override
                                    public void onClick(View view) {
                                        flatDialog.dismiss();
                                    }
                                })
                                .show();    //  Finalmente, se muestra.
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        };

        new getAllClases().execute("GET","");   //  Lanzamiento del hilo que carga toda la lista.


    }

    //  Método que elimina una clase de la lista.
    public void borrarClase(int clas){
        int posicion = clas;
        listaClases.remove(posicion);
        //  Notificamos al adaptador del RecyclerView que se han alterado los elementos de la lista.
        //  Conseguimos así que se redimensione y actualice.
        adapter.notifyItemRemoved(posicion);
        adapter.notifyItemRangeChanged(posicion, listaClases.size());
    }

    private class getAllClases extends AsyncTask<String, Void, String>{

        //  Código que se ejecuta en el hilo secundario, y en este caso recoge la información de la API.
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

        //  Tratamos los resultados de la API convirtiendo el String en JSON Objects/Arrays.
        //  Si son correctos, se añaden a la lista de clases del juego.
        @Override
        protected void onPostExecute(String raizClases) {
            try{
                if (raizClases != null){
                    ClaseJuego clase = null;
                    //  JSONArray con todas las clases del juego.
                    JSONArray resultados = new JSONObject(raizClases).getJSONArray("Results");

                    //  EXTRAEMOS LA INFORMACIÓN BÁSICA DE LA CLASE DEL JUEGO JUNTO A SU ID PARA PODER HACER OTRA PETICIÓN A LA API DESDE LA VISTA DETALLE.
                    for (int i = 0; i < resultados.length(); i++) {
                        JSONObject claseJSON = resultados.getJSONObject(i);
                        String id = String.valueOf(claseJSON.getInt("ID"));
                        String nombre = claseJSON.getString("Name");
                        nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
                        String urlIcono = claseJSON.getString("Icon");
                        clase = new ClaseJuego(id, nombre, " ", urlIcono, "", "", "", 1);
                        if (clase != null){
                            listaClases.add(clase);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            } catch (JSONException ex){

            }

        }
    }
}