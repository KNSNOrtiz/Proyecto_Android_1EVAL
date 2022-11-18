package com.example.proyecto_1eval_ortizmario.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyecto_1eval_ortizmario.R;
import com.example.proyecto_1eval_ortizmario.controlador.DBA;
import com.example.proyecto_1eval_ortizmario.controlador.Sesion;
import com.example.proyecto_1eval_ortizmario.modelo.Usuario;
import com.prathameshmore.toastylibrary.Toasty;

import life.sabujak.roundedbutton.RoundedButton;

public class LoginActivity extends AppCompatActivity {

    private EditText editUsuario;
    private EditText editPass;
    private RoundedButton btnLogin;
    private TextView tvRegistrarse;

    private Usuario usuario = null;
    public static Sesion sesion;

    Toasty toasty = new Toasty(this);

    DBA bd = DBA.getInstance(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // El código a continuación oculta el ActionBar con el título
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);



        editUsuario = (EditText) findViewById(R.id.editUsuarioRegistro);
        editPass = (EditText) findViewById(R.id.editContrasenaRegistro);
        btnLogin = (RoundedButton) findViewById(R.id.btnIniciarSesion);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Login()){
                    Intent index = new Intent(LoginActivity.this, IndexActivity.class);
                    startActivity(index);
                };

            }
        });
        tvRegistrarse = (TextView) findViewById(R.id.tvRegistrate);
        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                    startActivity(intent);
                } catch (Exception ex){
                    toasty.warningToasty(LoginActivity.this, "Error al abrir la página de registro", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
                }
            }
        });

    }

    private boolean Login(){
        boolean res = false;
        if (editUsuario.getText().length() == 0 || editPass.getText().length() == 0){
            toasty.warningToasty(LoginActivity.this, "Nombre de usuario/Contraseña vacíos.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
            return res;
        }
        usuario = new Usuario(editUsuario.getText().toString(), editPass.getText().toString());
        res = bd.consultarUsuarioLogin(usuario);
        if (res = true){
            toasty.successToasty(LoginActivity.this, "Has iniciado sesión como " + usuario.getNombreUsuario() + " . Bienvenido/a.", Toasty.LENGTH_LONG, Toasty.BOTTOM);
        }
        return res;
    }

}