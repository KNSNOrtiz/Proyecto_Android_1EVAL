package com.example.proyecto_1eval_ortizmario.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyecto_1eval_ortizmario.R;

public class PreferenciasActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        actionBar = getSupportActionBar();  //  Guardamos la referencia al ActionBar en esta variable.

        //  Reemplazamos mediante FragmentManager el elemento contenedor por el Fragment que contiene el XML con las preferecias.
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraintPreferencias, new PreferenciasFragment())
                .commit();


        //  Para mayor comodidad a la hora de volver al Activity anterior, habilitamos un botón para ello en el ActionBar.
        if (actionBar != null){
            actionBar.setTitle("Ajustes");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();    //  Método que vuelve al anterior Activity de la pila.
        }
        return true;
    }
}