package com.example.proyecto_1eval_ortizmario.modelo;

public class ClaseJuego {

    private String nombre;
    private String abreviacion;
    private String urlImagen;

    public ClaseJuego(String nombre, String abreviacion, String urlImagen) {
        this.nombre = nombre;
        this.abreviacion = abreviacion;
        this.urlImagen = urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
