package com.example.proyecto_1eval_ortizmario.controlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetXIV {

    //  Para sacar los contenidos de esta categoría, indico la URL de la que se van a empezar a sacar.
    private static final String URL_RAIZ_CLASES = "https://xivapi.com/classjob/";

    //  Con este método vamos a obtener las clases del juego, en base a su constante correspondiente y el filtro o endpoint.
    public static String getClases(String endpoint){

        //  Esta clase permite abrir conexiones HTTP a partir de una URL.
        HttpURLConnection http = null;

        String contenido = null;

        try {
            URL url = new URL( URL_RAIZ_CLASES + endpoint );
            //  Se obtiene una instancia de la clase abstracta a partir de la URl.
            http = (HttpURLConnection)url.openConnection();
            //  Establecimiento de las cabeceras para el tipo de contenido que vamos a solicitar. En este caso, JSON.
            http.setRequestProperty("Content-Type", "application/json");

            //  El servidor responde positivamente, se sacan los contenidos que buscamos en forma de cadena de texto.
            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                contenido = sb.toString();
                reader.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            //  Se cierra la conexión en cuanto terminamos de obtener los datos.
            if( http != null ){
                http.disconnect();
            }
        }
        return contenido;
    }

}
