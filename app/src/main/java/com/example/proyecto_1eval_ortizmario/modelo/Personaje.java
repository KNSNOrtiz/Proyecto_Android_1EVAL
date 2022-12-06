package com.example.proyecto_1eval_ortizmario.modelo;

//  Modelo de datos para el personaje que se va a mostrar en "Visor de personajes".
public class Personaje {

   private String id;
   private String nombre;
   private String urlPortrait;
   private String dataCenter;
   private String servidor;
   private String claseActual;
   private String nivelActual;
   private String freeCompany;

    public Personaje(String id, String nombre, String urlPortrait, String dataCenter, String servidor, String claseActual, String nivelActual, String freeCompany) {
        this.id = id;
        this.nombre = nombre;
        this.urlPortrait = urlPortrait;
        this.dataCenter = dataCenter;
        this.servidor = servidor;
        this.claseActual = claseActual;
        this.nivelActual = nivelActual;
        this.freeCompany = freeCompany;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlPortrait() {
        return urlPortrait;
    }

    public void setUrlPortrait(String urlPortrait) {
        this.urlPortrait = urlPortrait;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getClaseActual() {
        return claseActual;
    }

    public void setClaseActual(String claseActual) {
        this.claseActual = claseActual;
    }

    public String getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(String nivelActual) {
        this.nivelActual = nivelActual;
    }

    public String getFreeCompany() {
        return freeCompany;
    }

    public void setFreeCompany(String freeCompany) {
        this.freeCompany = freeCompany;
    }
}
