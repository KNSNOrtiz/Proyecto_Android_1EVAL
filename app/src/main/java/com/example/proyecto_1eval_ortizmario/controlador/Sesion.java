package com.example.proyecto_1eval_ortizmario.controlador;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sesion {

    //  Esta clase facilita el uso de las preferencias en toda la aplicación. En lugar de llamar
    //  a la clase SharedPreferences, se asignarán y recogerán los valores de los parámetros
    //  mediante Getters y Setters.
    private static SharedPreferences preferencias;

    //  Constructor de las preferencias.
    public Sesion(Context context){
        preferencias = PreferenceManager.getDefaultSharedPreferences(context);
    }
    //  Getters y Setters de la LoginActivity
    public void setUsuario(String usuario){
        preferencias.edit().putString("Usuario",usuario).commit();
    }
    public String getUsuario(){
        String usuario = preferencias.getString("Usuario","");
        return usuario;
    }
    public void setRecordar(Boolean recordar){
        preferencias.edit().putBoolean("RecordarUsuario", recordar).commit();
    }
    public boolean getRecordar(){
        Boolean recordar = preferencias.getBoolean("RecordarUsuario", false);
        return recordar;
    }

    //  Getters y setters de la ventana de preferencias, accesible desde
    //  PersonajeActivity -> PreferenciasActivity
    public String getNombrePersonaje(){
        String nombrePersonaje = preferencias.getString("nombrePersonaje", "");
        return nombrePersonaje;
    }
    public String getServidorPersonaje(){
        String servidorPersonaje = preferencias.getString("servidorPersonaje", "");
        return servidorPersonaje;
    }
}
