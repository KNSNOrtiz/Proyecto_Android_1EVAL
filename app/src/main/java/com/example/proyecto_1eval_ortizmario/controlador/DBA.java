package com.example.proyecto_1eval_ortizmario.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.proyecto_1eval_ortizmario.modelo.Usuario;

public class DBA extends SQLiteOpenHelper {


    //  ESTRUCTURA INICIAL DE LA BASE DE DATOS
    private static final String NOMBRE_BD = "DB_APP";
    private static final String TABLA_USUARIOS = "USUARIOS";
    private static final int VERSION_BD = 1;
    private static DBA instanciaDB;     //  Se creará un Singleton de la BD para que sea accesible desde toda la aplicación.

    //  ESTRUCTURA DE LA TABLA USUARIOS
    private static final String COLUMNA_USUARIO = "nombreUsuario";
    private static final String COLUMNA_PASSWORD = "password";

    //  AUXILIARES
    private Context contextoActual;
    private String sql = "";    //  Variable que se usará para almacenar las sentencias para la BD.

    public static synchronized DBA getInstance(Context  context){   //  Método para crear el Singleton pasándole el contexto de la aplicación.
        if (instanciaDB == null) {
            instanciaDB = new DBA(context);
        }
        return instanciaDB;
    }


    public DBA(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
        contextoActual = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sql = "CREATE TABLE " + TABLA_USUARIOS + "(" + COLUMNA_USUARIO + " TEXT PRIMARY KEY," + COLUMNA_PASSWORD + " TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertUsuario(Usuario usuario){
        SQLiteDatabase bd = this.getWritableDatabase(); //  Conexión con posibilidad de escritura de la BD.
        ContentValues valores = new ContentValues();    // HashMap con los valores ("NombreColumna" : Valor)
        long res = -1;  //  Número de filas afectadas.

        valores.put(COLUMNA_USUARIO,usuario.getNombreUsuario());
        valores.put(COLUMNA_PASSWORD,usuario.getPassword());
        res = bd.insert(TABLA_USUARIOS, null, valores);
        bd.close();

        return res;
    }

    public boolean consultarUsuarioRegistro(String nombreUsuario){
        boolean res = false;

        SQLiteDatabase bd = this.getReadableDatabase(); // Conexión con posibilidad de lectura.
        String[] cols = {COLUMNA_USUARIO};  //  Columnas que queremos extraer
        String where = COLUMNA_USUARIO + "= ?";
        String[] valores = {nombreUsuario};


        Cursor cursor = bd.query(TABLA_USUARIOS,cols,where,valores, null, null, null);
        if (cursor.getCount() > 0){
            res = true;
        }
        cursor.close();
        return res;
    }

    public boolean consultarUsuarioLogin(Usuario usuario){
        boolean res = false;

        SQLiteDatabase bd = this.getReadableDatabase(); // Conexión con posibilidad de lectura.
        String[] cols = {COLUMNA_USUARIO};  //  Columnas que queremos extraer
        String where = COLUMNA_USUARIO + "= ? and " + COLUMNA_PASSWORD + " = ?";
        String[] valores = {usuario.getNombreUsuario(), usuario.getPassword()};


        Cursor cursor = bd.query(TABLA_USUARIOS,cols,where,valores, null, null, null);
        if (cursor.getCount() > 0){
            res = true;
        }
        cursor.close();
        return res;
    }

}
