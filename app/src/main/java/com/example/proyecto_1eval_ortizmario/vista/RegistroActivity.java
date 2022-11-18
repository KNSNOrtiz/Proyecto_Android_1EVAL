package com.example.proyecto_1eval_ortizmario.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.controlador.DBA;
import com.example.proyecto_1eval_ortizmario.modelo.Usuario;
import com.prathameshmore.toastylibrary.Toasty;

import life.sabujak.roundedbutton.RoundedButton;

public class RegistroActivity extends AppCompatActivity {

    private EditText editUsuario;
    private EditText editPass;
    private RoundedButton btnRegistrar;

    private final Toasty toasty = new Toasty(this);     //  Librería "Toasty" para facilitar el uso de los Toast.
    private DBA bd = DBA.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro);

        editUsuario = (EditText) findViewById(R.id.editUsuarioRegistro);
        editPass = (EditText) findViewById(R.id.editContrasenaRegistro);
        btnRegistrar = (RoundedButton)findViewById(R.id.btnRegistrarse);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarUsuario(editUsuario.getText().toString(), editPass.getText().toString());
            }
        });


    }

    private int ValidarUsuario(String usuario, String pass){
        /*  Debe empezar y terminar con un caracter alfanumérico, pudiendo contener ciertos caracteres especiales (puntos, guiones y barras bajas) pero no más de uno seguido.
            La longitud del nombre de usuario estará entre 5 y 20 caracteres (Caracter inicial, cuerpo[3-18 long.], y  caracter final).
         */
        String patronUsuario = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

        //Debe ser una combinación de mínimo 4 caracteres alfanuméricos y no tener espacios en blanco.
        String patronPass = "^(?=.*[a-zA-Z0-9])(?=\\S+$).{4,}$";


        if (usuario.isEmpty() || pass.isEmpty()){
            toasty.dangerToasty(this,"Debes introducir un usuario y contraseña.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            return -1;
        }
        if (!usuario.matches(patronUsuario)){
            toasty.dangerToasty(this,"Nombre de usuario inválido. (Longitud entre 5-20 caracteres, sin caracteres especiales salvo [._-] ni espacios en blanco.)", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            return -1;
        }
        if (!pass.matches(patronPass)){
            toasty.dangerToasty(this,"Contraseña inválida. (Mínimo 6 caracteres alfanuméricos o caracteres especiales, sin espacios en blanco.)", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            return -1;
        }
        if (bd.consultarUsuarioRegistro(usuario)){
            toasty.dangerToasty(this,"El nombre de usuario no está disponible porque ya existe.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            return -1;
        }
        return 0;
    }

    private void RegistrarUsuario(String nombreUsuario, String pass){
        Usuario usuario = null;
        long res = -1;
        if (ValidarUsuario(nombreUsuario, pass) == 0){
            usuario = new Usuario(nombreUsuario,pass);
            res = bd.insertUsuario(usuario);
            if (res == -1){
                toasty.dangerToasty(this,"Error de registro", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
                return;
            }
            else{
                toasty.successToasty(this,"Registro completado con éxito.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            }
        }



    }
}