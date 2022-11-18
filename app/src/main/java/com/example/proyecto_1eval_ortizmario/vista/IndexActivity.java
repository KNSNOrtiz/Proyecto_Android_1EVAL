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
    Toasty toasty = new Toasty(this);

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cviewClases:
                    toasty.infoToasty(IndexActivity.this, "Has elegido la categoría \"Clases\"", Toasty.LENGTH_SHORT, Toasty.TOP);
                    Intent intent = new Intent(IndexActivity.this, ClasesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.cviewMazmorras:
                    toasty.infoToasty(IndexActivity.this, "Has elegido la categoría \"Mazmorras\"", Toasty.LENGTH_SHORT, Toasty.TOP);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_index);

        cvMazmorras = (CardView) findViewById(R.id.cviewMazmorras);
        cvClases = (CardView) findViewById(R.id.cviewClases);

        cvMazmorras.setOnClickListener(listener);
        cvClases.setOnClickListener(listener);

    }
}