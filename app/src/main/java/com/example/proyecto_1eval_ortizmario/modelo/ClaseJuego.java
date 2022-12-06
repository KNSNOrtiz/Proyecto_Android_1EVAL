package com.example.proyecto_1eval_ortizmario.modelo;

//  Modelo de datos para las clases jugables dentro de Final Fantasy XIV, según la API.
public class ClaseJuego {

    private String id;
    private String nombre;
    private String abreviacion;
    private String urlImagen;
    private String tipo;
    private String ciudadInicio;
    private String urlImagenCiudad;
    private int nivelInicial = 1;   //  No todas las clases tienen el mismo nivel inicial, pero sí el máximo.
    private final int nivelMax = 90;



    public ClaseJuego(String id, String nombre, String abreviacion, String urlImagen, String tipo, String ciudadInicio, String urlImagenCiudad, int nivelInicial) {
        this.id = id;
        this.nombre = nombre;
        this.abreviacion = abreviacion;
        this.urlImagen = urlImagen;
        this.tipo = tipo;
        //  No todas las clases tendrán una ciudad de inicio, así que rellenamos ese hueco.
        if (ciudadInicio == null | ciudadInicio.isEmpty()){
            ciudadInicio = "<Sin ciudad inicial>";
        }
        this.ciudadInicio = ciudadInicio;
        this.urlImagenCiudad = urlImagenCiudad;
        this.nivelInicial = nivelInicial;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCiudadInicio() {
        return ciudadInicio;
    }

    public void setCiudadInicio(String ciudadInicio) {
        this.ciudadInicio = ciudadInicio;
    }

    public String getUrlImagenCiudad() {
        return urlImagenCiudad;
    }

    public void setUrlImagenCiudad(String urlImagenCiudad) {
        this.urlImagenCiudad = urlImagenCiudad;
    }

    public int getNivelInicial() {
        return nivelInicial;
    }

    public void setNivelInicial(int nivelInicial) {
        this.nivelInicial = nivelInicial;
    }

    public int getNivelMax() {
        return nivelMax;
    }
}
