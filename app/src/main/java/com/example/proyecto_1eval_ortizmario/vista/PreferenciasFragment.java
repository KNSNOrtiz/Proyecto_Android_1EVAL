package com.example.proyecto_1eval_ortizmario.vista;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.example.proyecto_1eval_ortizmario.R;

public class PreferenciasFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferencias,rootKey); //  Cargamos las preferencias desde el XML.
    }
}
