package com.example.proyecto_1eval_ortizmario.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
    //  RoundedButton es un elemento de tipo Button importado desde AndroidArsenal para hacer botones redondeados.
    private RoundedButton btnLogin;
    private TextView tvRegistrarse;
    private CheckBox cbxRecordar;

    private Usuario usuario = null;
    private Sesion sesion;

    Toasty toasty = new Toasty(this);
    //  Cargamos la instancia de la BD.
    DBA bd = DBA.getInstance(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // La línea a continuación oculta el ActionBar con el título del proyecto.
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);
        sesion = new Sesion(this);

        editUsuario = (EditText) findViewById(R.id.editUsuarioRegistro);
        editPass = (EditText) findViewById(R.id.editContrasenaRegistro);
        cbxRecordar = (CheckBox) findViewById(R.id.cbxRecordar);
        btnLogin = (RoundedButton) findViewById(R.id.btnIniciarSesion);

        if (sesion.getRecordar()){
            if (sesion.getUsuario() != null && sesion.getUsuario() != ""){
                editUsuario.setText(sesion.getUsuario());
                cbxRecordar.setChecked(sesion.getRecordar());
            }
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Gestionamos las preferencias del Login según tenga éxito o no, y según si se quieren guardar o viceversa.
                if (Login()){
                    if (cbxRecordar.isChecked()){
                        sesion.setUsuario(editUsuario.getText().toString());
                        sesion.setRecordar(true);
                    } else{
                        sesion.setUsuario("");
                        sesion.setRecordar(false);
                    }
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
        usuario = new Usuario(editUsuario.getText().toString().trim(), editPass.getText().toString().trim());
        //  Lanza una consulta a la BD para ver si existe.
        res = bd.consultarUsuarioLogin(usuario);
        if (res == true){
            toasty.successToasty(LoginActivity.this, "Has iniciado sesión como " + usuario.getNombreUsuario() + " . Bienvenido/a.", Toasty.LENGTH_LONG, Toasty.BOTTOM);
        } else{
            toasty.warningToasty(LoginActivity.this, "El usuario no existe o las credenciales son incorrectas.", Toasty.LENGTH_SHORT, Toasty.BOTTOM);
        }
        return res;
    }

}