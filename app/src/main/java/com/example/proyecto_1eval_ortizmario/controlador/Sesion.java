package com.example.proyecto_1eval_ortizmario.controlador;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sesion {

    private SharedPreferences preferencias;

    public Sesion(Context context){
        preferencias = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setUsuario(String usuario){
        preferencias.edit().putString("Usuario",usuario).commit();
    }
    public String getUsuario(){
        String usuario = preferencias.getString("Usuario","");
        return usuario;
    }
}
