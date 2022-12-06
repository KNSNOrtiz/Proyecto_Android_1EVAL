package com.example.proyecto_1eval_ortizmario.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.proyecto_1eval_ortizmario.R;
import com.prathameshmore.toastylibrary.Toasty;

public class IndexActivity extends AppCompatActivity {

    private CardView cvClases;
    private CardView cvMazmorras;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            //  Menú principal de la aplicación. Según el CardView seleciconado, se lanzará
            // un Activity u otro.
            switch (view.getId()){
                case R.id.cviewPersonaje:
                    intent = new Intent(IndexActivity.this, PersonajeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.cviewClases:
                    intent = new Intent(IndexActivity.this, ClasesActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        getSupportActionBar().hide();   //  Este método oculta el ActionBar por defecto.

        cvMazmorras = (CardView) findViewById(R.id.cviewPersonaje);
        cvClases = (CardView) findViewById(R.id.cviewClases);

        cvMazmorras.setOnClickListener(listener);
        cvClases.setOnClickListener(listener);

    }


}