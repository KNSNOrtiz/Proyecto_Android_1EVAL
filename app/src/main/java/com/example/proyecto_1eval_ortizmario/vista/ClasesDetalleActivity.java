package com.example.proyecto_1eval_ortizmario.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.controlador.HttpGetXIV;
import com.example.proyecto_1eval_ortizmario.modelo.ClaseJuego;
import com.prathameshmore.toastylibrary.Toasty;

import org.json.JSONException;
import org.json.JSONObject;

public class ClasesDetalleActivity extends AppCompatActivity {

    private ImageView imgIcono, imgCiudadInicial;
    private TextView tvNombre, tvAbreviacion, tvTipo, tvCiudadInicial, tvNivelMax,tvNivelInicial;
    private Toasty toasty = new Toasty(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_clases_detalle);

        imgIcono = (ImageView) findViewById(R.id.imgDetalleClase);
        tvNombre = (TextView) findViewById(R.id.tvDetalleNombre);
        tvAbreviacion = (TextView) findViewById(R.id.tvDetalleAbrev);
        tvTipo = (TextView) findViewById(R.id.tvDetalleTipo);
        tvCiudadInicial = (TextView) findViewById(R.id.tvDetalleCiudad);
        tvNivelInicial = (TextView)  findViewById(R.id.tvDetalleNivelInicial);
        tvNivelMax = (TextView) findViewById(R.id.tvDetalleNivelMax);
        imgCiudadInicial = (ImageView) findViewById(R.id.imgDetalleCiudad);
        //  Gracias al ID obtenido del anterior Activity podemos consultar detalles del elemento
        //  seleccionado.
        String id = getIntent().getExtras().getString("id");

        new getClases().execute("GET", id);

    }

    private class getClases extends AsyncTask<String, Void, String> {

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

                    //  EXTRAEMOS TODA LA INFORMACIÓN DE LA CLASE.
                    String id = String.valueOf(claseJSON.getInt("ClassJobParentTargetID"));
                    String nombre = claseJSON.getString("NameEnglish");
                    String abreviacion = claseJSON.getString("Abbreviation");
                    String urlIcono = claseJSON.getString("Icon");
                    //  Algunas propiedades están dentro de otros JSONObject anidados, así que es necesario declararlos.
                    JSONObject padreTipo = claseJSON.getJSONObject("ClassJobCategory");
                    JSONObject padreCiudad;
                    //  Algunos campos no necesariamente tendrán valores asignados, así que para evitar errores los tratamos desde aquí.
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

                    if (clase != null){
                        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(ClasesDetalleActivity.this);
                        progressDrawable.setStrokeWidth(20f);
                        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
                        progressDrawable.setCenterRadius(5f);
                        progressDrawable.setBackgroundColor(Color.CYAN);
                        progressDrawable.start();

                        Glide.with(ClasesDetalleActivity.this)
                                .load("https://xivapi.com/" + clase.getUrlImagen())
                                .placeholder(progressDrawable)
                                .error(R.mipmap.img_placeholder_round)
                                .into(imgIcono);
                        tvNombre.setText(clase.getNombre());
                        tvAbreviacion.setText(clase.getAbreviacion());
                        tvTipo.setText(clase.getTipo());
                        tvCiudadInicial.setText(clase.getCiudadInicio());
                        tvNivelInicial.setText(String.valueOf(clase.getNivelInicial()));
                        tvNivelMax.setText(String.valueOf(clase.getNivelMax()));
                        Glide.with(ClasesDetalleActivity.this)
                                .load("https://xivapi.com/" + clase.getUrlImagenCiudad())
                                .placeholder(progressDrawable)
                                .error(R.mipmap.img_placeholder_round)
                                .into(imgCiudadInicial);
                    }
                }

            } catch(JSONException ex){
                toasty.dangerToasty(ClasesDetalleActivity.this, "No se ha podido crear el objeto Clase.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            }
        }
    }
}